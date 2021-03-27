package spiis.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import spiis.server.api.DinnerRequest;
import spiis.server.api.DinnerResponse;
import spiis.server.api.ValueWrapper;
import spiis.server.error.ConflictException;
import spiis.server.error.ForbiddenException;
import spiis.server.error.NotFoundException;
import spiis.server.model.Dinner;
import spiis.server.model.User;
import spiis.server.repository.DinnerRepository;
import spiis.server.repository.UserRepository;
import spiis.server.service.AuthService;
import spiis.server.service.DinnerService;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/dinners")
public class DinnerController {

    private final AuthService authService;
    private final DinnerService dinnerService;
    private final UserRepository userRepository;
    private final DinnerRepository dinnerRepository;

    @Autowired
    public DinnerController(AuthService authService, DinnerService dinnerService,
                            UserRepository userRepository, DinnerRepository dinnerRepository) {
        this.authService = authService;
        this.dinnerService = dinnerService;
        this.userRepository = userRepository;
        this.dinnerRepository = dinnerRepository;
    }

    @GetMapping
    public List<DinnerResponse> getAllDinners() {
        return dinnerService.makeDinnerResponses(false);
    }

    @GetMapping("/{id}")
    public DinnerResponse getDinner(@PathVariable("id") Long id) {
        return dinnerService.makeDinnerResponse(id);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DinnerResponse> createDinner(@RequestBody DinnerRequest request,
                                                       @RequestHeader("Authorization") String token) {
        User user = authService.getUserForToken(token);
        DinnerResponse response = dinnerService.createDinner(request, user);
        return RESTControllerUtil.makePOSTResponse(response, "/dinners", response.getId());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void editDinner(@PathVariable("id") Long id,
                           @RequestBody DinnerRequest request,
                           @RequestHeader("Authorization") String token) {
        Dinner dinner = dinnerRepository.findById(id).orElseThrow(NotFoundException::new);

        authService.throwIfForbidden(token, Objects.requireNonNull(dinner.getHost()));

        dinnerService.editDinner(dinner, request);
    }

    @PutMapping("/{id}/cancelled")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void setDinnerCancelled(@PathVariable("id") Long id,
                             @RequestBody ValueWrapper<Boolean> value,
                             @RequestHeader("Authorization") String token) {
        Dinner dinner = dinnerRepository.findById(id).orElseThrow(NotFoundException::new);

        authService.throwIfForbidden(token, Objects.requireNonNull(dinner.getHost()));

        dinner.setCancelled(value.getValue());
    }

    /**
     * Adds a user as a guest, unless already added, is host, or dinner is full.
     *
     * @param dinnerId the id of the dinner
     * @param userId the id of the user
     * @param token authorization. Only the userId user can call this
     */
    @PutMapping("/{id}/guests/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void addGuestToDinner(@PathVariable("id") Long dinnerId, @PathVariable("userId") Long userId,
                                 @RequestHeader("Authorization") String token) {
        User user = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        Dinner dinner = dinnerRepository.findById(dinnerId).orElseThrow(NotFoundException::new);

        authService.throwIfForbidden(token, user);

        if (user.equals(dinner.getHost()))
            throw new ConflictException("The host can't be a guest");
        if (dinner.getGuests().contains(user))
            throw new ConflictException("The user is already a guest");
        if (dinner.getGuests().size() >= dinner.getMaxGuests())
            throw new ConflictException("There is not room for more guests!");
        if (dinner.getRemovedGuests().contains(user))
            throw new ConflictException("The user has been removed");
        dinner.addGuest(user);
    }

    /**
     * Removes a user as guest from the given dinner
     *
     * @param dinnerId the id of the dinner
     * @param userId the id of the user
     * @param token authorization. Only the userId user or dinner host user can call this
     */
    @DeleteMapping("/{id}/guests/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void removeGuestFromDinner(@PathVariable("id") Long dinnerId, @PathVariable("userId") Long userId,
                                 @RequestHeader("Authorization") String token) {
        User user = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        Dinner dinner = dinnerRepository.findById(dinnerId).orElseThrow(NotFoundException::new);
        User host = Objects.requireNonNull(dinner.getHost());

        if (!authService.doesTokenGiveAccess(token, user) && !authService.doesTokenGiveAccess(token, host))
            throw new ForbiddenException();

        dinner.removeGuest(user);
    }
}
