package spiis.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import spiis.server.api.AdvertRequest;
import spiis.server.api.AdvertResponse;
import spiis.server.error.NotFoundException;
import spiis.server.model.Advert;
import spiis.server.repository.AdvertRepository;

import java.util.Objects;

@Service
public class AdvertService {

    private final AdvertRepository advertRepository;

    @Autowired
    public AdvertService(AdvertRepository advertRepository) {
        this.advertRepository = advertRepository;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public AdvertResponse makeAdvertResponse(Advert advert) {
        Objects.requireNonNull(advert.getId());

        AdvertResponse.AdvertResponseBuilder builder = AdvertResponse.builder();
        builder.id(advert.getId())
                .title(advert.getTitle())
                .companyName(advert.getCompanyName())
                .link(advert.getLink())
                .picture(advert.getPicture());

        return builder.build();
    }

    @Transactional(readOnly = true)
    public AdvertResponse makeAdvertResponse(Long id) {
        Advert advert = advertRepository.findById(id).orElseThrow(NotFoundException::new);
        return makeAdvertResponse(advert);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public AdvertResponse createAdvert(AdvertRequest request) {
        Advert advert = new Advert();
        editAdvert(advert, request);

        advertRepository.save(advert);
        return makeAdvertResponse(advert);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void editAdvert(Advert advert, AdvertRequest request) {
        advert.setTitle(request.getTitle().trim());
        advert.setCompanyName(request.getCompanyName().trim());
        advert.setLink(request.getLink().trim());
        advert.setPicture(request.getPicture());

        advert.verifyModel();
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void deleteAdvert(Advert advert) {
        advertRepository.delete(advert);
    }

    @Transactional
    public void deleteAdvert(Long id) {
        deleteAdvert(advertRepository.findById(id).orElseThrow(NotFoundException::new));
    }

}
