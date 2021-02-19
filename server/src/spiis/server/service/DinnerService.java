package spiis.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spiis.server.api.AllDinnerResponse;
import spiis.server.api.DinnerResponse;
import spiis.server.error.NotFoundException;
import spiis.server.model.Dinner;
import spiis.server.repository.DinnerRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DinnerService {

    private final DinnerRepository dinnerRepository;

    @Autowired
    public DinnerService(DinnerRepository dinnerRepository) {
        this.dinnerRepository = dinnerRepository;
    }

    @Transactional(readOnly = true)
    public DinnerResponse getDinnerById(long id) {
        Dinner dinner = dinnerRepository.findById(id).orElseThrow(NotFoundException::new);
        return new DinnerResponse(dinner);
    }

    @Transactional(readOnly = true)
    public AllDinnerResponse getDinners() {
        Iterable<Dinner> dinnerIterable = dinnerRepository.findAll();
        Set<Dinner> dinnerSet = new HashSet<>();
        dinnerIterable.forEach(dinnerSet::add);
        return new AllDinnerResponse(dinnerSet.stream().map(DinnerResponse::new).collect(Collectors.toSet()));
    }
}
