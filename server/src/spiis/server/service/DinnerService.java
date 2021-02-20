package spiis.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import spiis.server.api.DinnerResponse;
import spiis.server.error.NotFoundException;
import spiis.server.model.Dinner;
import spiis.server.model.User;
import spiis.server.repository.DinnerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DinnerService {

    private final DinnerRepository dinnerRepository;

    @Autowired
    public DinnerService(DinnerRepository dinnerRepository) {
        this.dinnerRepository = dinnerRepository;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public DinnerResponse makeDinnerResponse(Dinner dinner) {
        Objects.requireNonNull(dinner.getId());
        Objects.requireNonNull(dinner.getHost());
        Objects.requireNonNull(dinner.getHost().getId());

        return new DinnerResponse(dinner.getId(), dinner.getTitle(),
                dinner.getTime(), dinner.getPlace(), dinner.getMaxPeople(), dinner.getHost().getId(),
                dinner.getGuests().stream().map(User::getId).collect(Collectors.toList()));
    }

    @Transactional(readOnly = true)
    public DinnerResponse getDinnerById(long id) {
        Dinner dinner = dinnerRepository.findById(id).orElseThrow(NotFoundException::new);
        return makeDinnerResponse(dinner);
    }

    @Transactional(readOnly = true)
    public List<DinnerResponse> getDinners() {
        Iterable<Dinner> dinnerIterable = dinnerRepository.findAll();
        List<DinnerResponse> responses = new ArrayList<>();
        for (Dinner dinner : dinnerIterable)
            responses.add(makeDinnerResponse(dinner));
        return responses;
    }
}
