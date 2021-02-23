package spiis.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import spiis.server.api.DinnerRequest;
import spiis.server.api.DinnerResponse;
import spiis.server.api.LogInRequest;
import spiis.server.api.ValueWrapper;
import spiis.server.error.NotFoundException;
import spiis.server.model.Dinner;
import spiis.server.model.User;
import spiis.server.repository.DinnerRepository;
import spiis.server.service.AuthService;
import spiis.server.service.DinnerService;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/dinners")
public class DinnerController {

    private final AuthService authService;
    private final DinnerService dinnerService;
    private final DinnerRepository dinnerRepository;

    @Autowired
    public DinnerController(AuthService authService, DinnerService dinnerService, DinnerRepository dinnerRepository) {
        this.authService = authService;
        this.dinnerService = dinnerService;
        this.dinnerRepository = dinnerRepository;
    }

    @GetMapping
    public List<DinnerResponse> getAllDinners() {
        return dinnerService.getDinners();
    }

    @GetMapping("/{id}")
    public DinnerResponse getDinner(@PathVariable("id") Long id) {
        return dinnerService.getDinnerById(id);
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
    public void cancelDinner(@PathVariable("id") Long id,
                             @RequestBody ValueWrapper<Boolean> value,
                             @RequestHeader("Authorization") String token) {
        Dinner dinner = dinnerRepository.findById(id).orElseThrow(NotFoundException::new);

        authService.throwIfForbidden(token, Objects.requireNonNull(dinner.getHost()));

        dinner.setCancelled(value.getValue());
    }
}
