package spiis.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import spiis.server.api.ValueWrapper;
import spiis.server.error.ForbiddenException;
import spiis.server.error.ModelError;
import spiis.server.error.NotFoundException;
import spiis.server.model.Dinner;
import spiis.server.model.HostRating;
import spiis.server.model.User;
import spiis.server.repository.DinnerRepository;
import spiis.server.repository.HostRatingRepository;
import spiis.server.repository.UserRepository;
import spiis.server.service.AuthService;

@RestController
public class RatingController {

    private final AuthService authService;
    private final DinnerRepository dinnerRepository;
    private final UserRepository userRepository;
    private final HostRatingRepository hostRatingRepository;

    @Autowired
    public RatingController(AuthService authService, DinnerRepository dinnerRepository,
                            UserRepository userRepository, HostRatingRepository hostRatingRepository) {
        this.authService = authService;
        this.dinnerRepository = dinnerRepository;
        this.userRepository = userRepository;
        this.hostRatingRepository = hostRatingRepository;
    }

    @PutMapping("/dinners/{dinnerId}/hostRating")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void setHostRating(@PathVariable("dinnerId") Long id,
                                   @RequestBody ValueWrapper<Integer> rating,
                                   @RequestHeader("Authorization") String token) {
        Dinner dinner = dinnerRepository.findById(id).orElseThrow(NotFoundException::new);

        User rater = authService.getUserForToken(token);

        if (!dinner.getGuests().contains(rater)) {
            throw new ForbiddenException();
        }

        if (rating.getValue() < 1 || rating.getValue() > 6) {
            throw new ModelError("Rating must be in range 1-6");
        }

        hostRatingRepository.findByRaterAndDinner(rater, dinner)
                .ifPresentOrElse(it -> it.setRating(rating.getValue()), () -> {
                    HostRating ratingObject = new HostRating(rater, dinner, rating.getValue());
                    hostRatingRepository.save(ratingObject);
                });
    }

    @GetMapping("/dinners/{dinnerId)/rater/{guestId}")
    @Transactional
    public boolean hasGuestRatedDinner(@PathVariable("dinnerId") Long dinnerId,
                                       @PathVariable("guestID") Long guestId) {
        Dinner dinner = dinnerRepository.findById(dinnerId).orElseThrow(NotFoundException::new);
        User guest = userRepository.findById(guestId).orElseThrow(NotFoundException::new);
        try {
            hostRatingRepository.findByRaterAndDinner(guest, dinner).orElseThrow(NotFoundException::new);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @GetMapping("/users/{userId}/hostRating")
    @Transactional
    public ValueWrapper<Float> getHostRating(@PathVariable("userId") Long id) {
        User host = userRepository.findById(id).orElseThrow(NotFoundException::new);
        return new ValueWrapper<>(hostRatingRepository.getAverageHostRating(host).orElse(null));
    }
}
