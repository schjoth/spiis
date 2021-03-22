package spiis.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import spiis.server.api.DinnerResponse;
import spiis.server.api.UserResponse;
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
    public List<UserResponse> getAllUsers() {
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


    //TODO: Edit user info
}
