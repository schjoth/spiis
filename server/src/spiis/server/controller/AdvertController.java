package spiis.server.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import spiis.server.api.AdvertRequest;
import spiis.server.api.AdvertResponse;
import spiis.server.error.ForbiddenException;
import spiis.server.error.NotFoundException;
import spiis.server.model.Advert;
import spiis.server.repository.AdvertRepository;
import spiis.server.service.AdvertService;
import spiis.server.service.AuthService;

import java.util.List;

@RestController
@RequestMapping("/adverts")
public class AdvertController {

    private final AdvertService advertService;
    private final AdvertRepository advertRepository;
    private final AuthService authService;

    @Autowired
    public AdvertController(AdvertService advertService, AdvertRepository advertRepository, AuthService authService) {
        this.advertService = advertService;
        this.advertRepository = advertRepository;
        this.authService = authService;
    }

    @GetMapping
    public List<AdvertResponse> getAllAdverts() {
        return advertService.makeAdvertResponses();
    }

    @GetMapping("/{id}")
    public AdvertResponse getAdvert(@PathVariable("id") Long id) {
        return advertService.makeAdvertResponse(id);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<AdvertResponse> createAdvert(@RequestBody AdvertRequest request,
                                                       @RequestHeader("Authorization") String token) {
        if (!authService.isTokenForAdminUser(token))
            throw new ForbiddenException();

        AdvertResponse advertResponse = advertService.createAdvert(request);
        return RESTControllerUtil.makePOSTResponse(advertResponse, "/adverts", advertResponse.getId());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void editAdvert(@PathVariable("id") Long id, @RequestBody AdvertRequest request,
                           @RequestHeader("Authorization") String token) {
        if (!authService.isTokenForAdminUser(token))
            throw new ForbiddenException();
        Advert advert = advertRepository.findById(id).orElseThrow(NotFoundException::new);

        advertService.editAdvert(advert, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void deleteAdvert(@PathVariable("id") Long id, @RequestHeader("Authorization") String token) {
        if (!authService.isTokenForAdminUser(token)) {
            throw new ForbiddenException();
        }

        advertService.deleteAdvert(id);
    }
}
