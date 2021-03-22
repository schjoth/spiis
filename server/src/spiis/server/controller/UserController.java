package spiis.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import spiis.server.api.DinnerResponse;
import spiis.server.api.UserResponse;
import spiis.server.api.ValueWrapper;
import spiis.server.error.ForbiddenException;
import spiis.server.error.NotFoundException;
import spiis.server.model.User;
import spiis.server.repository.UserRepository;
import spiis.server.service.AuthService;
import spiis.server.service.DinnerService;
import spiis.server.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final AuthService authService;
    private final UserService userService;
    private final DinnerService dinnerService;
    private final UserRepository userRepository;

    @Autowired
    public UserController(AuthService authService, UserService userService,
                          DinnerService dinnerService, UserRepository userRepository) {
        this.authService = authService;
        this.userService = userService;
        this.dinnerService = dinnerService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<UserResponse> getAllUsers(@RequestHeader(value = "Authorization") String token) {
        if (!authService.isTokenForAdminUser(token))
            throw new ForbiddenException();
        return userService.makeUserResponses();
    }

    @GetMapping("/{id}")
    @Transactional
    public UserResponse getUser(@PathVariable("id") Long id,
                                @RequestHeader(value = "Authorization", required = false) @Nullable String token) {
        return userService.makeUserResponse(id, authService.doesTokenGiveAccess(token, id));
    }

    @GetMapping("/{id}/guesting")
    @Transactional
    public List<DinnerResponse> getGuestingForUser(@PathVariable("id") Long id,
                                                   @RequestHeader("Authorization") String token) {
        User user = userRepository.findById(id).orElseThrow(NotFoundException::new);

        authService.throwIfForbidden(token, user);

        return user.getGuesting().stream()
                .map(it -> dinnerService.makeDinnerResponse(it, true)).collect(Collectors.toList());
    }

    @GetMapping("/{id}/hosting")
    @Transactional
    public List<DinnerResponse> getHostingForUser(@PathVariable("id") Long id,
                                                  @RequestHeader("Authorization") String token) {
        User user = userRepository.findById(id).orElseThrow(NotFoundException::new);

        authService.throwIfForbidden(token, user);

        return user.getHosting().stream()
                .map(it -> dinnerService.makeDinnerResponse(it, true)).collect(Collectors.toList());
    }

    @DeleteMapping("{id}/deleted")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void deleteUser(@PathVariable("id") Long id,
                           @RequestBody ValueWrapper<Boolean> deleted,
                           @RequestHeader("Authorization") String token) {
        if (!authService.isTokenForAdminUser(token))
            throw new ForbiddenException();
        User user = userRepository.findById(id).orElseThrow(NotFoundException::new);
        user.setDeleted(deleted.getValue());
    }
    //TODO: Edit user info
}
