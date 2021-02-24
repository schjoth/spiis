package spiis.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import spiis.server.api.DinnerRequest;
import spiis.server.api.DinnerResponse;
import spiis.server.api.UserResponse;
import spiis.server.error.NotFoundException;
import spiis.server.model.Dinner;
import spiis.server.model.ModelUtil;
import spiis.server.model.User;
import spiis.server.repository.DinnerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class DinnerService {

    private final DinnerRepository dinnerRepository;
    private final UserService userService;

    @Autowired
    public DinnerService(DinnerRepository dinnerRepository, UserService userService) {
        this.dinnerRepository = dinnerRepository;
        this.userService = userService;
    }

    /**
     * Makes a serializable object with info about the Dinner
     * @param dinner the Dinner in question
     * @param member if the user asking has access to member info
     * @return the DinnerResponse
     */
    @Transactional(propagation = Propagation.MANDATORY)
    public DinnerResponse makeDinnerResponse(Dinner dinner, boolean member) {
        Objects.requireNonNull(dinner.getId());

        DinnerResponse.DinnerResponseBuilder builder = DinnerResponse.builder();
        builder.id(dinner.getId())
                .title(dinner.getTitle())
                .description(dinner.getDescription())
                .expenses(dinner.getExpenses())
                .date(dinner.getDate().toString())
                .startTime(dinner.getStartTime().toString())
                .endTime(dinner.getEndTime().toString())
                .postCode(dinner.getPostCode())
                .city(dinner.getCity())
                .maxGuests(dinner.getMaxGuests())
                .cancelled(dinner.isCancelled());

        User host = Objects.requireNonNull(dinner.getHost());
        builder.host(userService.makeUserResponse(host, false));

        if (member) {
            builder.addressLine(dinner.getAddressLine());
            List<UserResponse> guests = new ArrayList<>();
            for (User guest : dinner.getGuests())
                guests.add(userService.makeUserResponse(guest, false));
            builder.guests(guests);
        }

        return builder.build();
    }

    @Transactional(readOnly = true)
    public DinnerResponse makeDinnerResponse(Long id) {
        Dinner dinner = dinnerRepository.findById(id).orElseThrow(NotFoundException::new);
        return makeDinnerResponse(dinner, true); //TODO: Not all true
    }

    @Transactional(readOnly = true)
    public List<DinnerResponse> makeDinnerResponses(boolean includeCancelled) {

        Iterable<Dinner> dinnerIterable = includeCancelled
                ? dinnerRepository.findAll()
                : dinnerRepository.findAllByCancelledFalse();

        List<DinnerResponse> responses = new ArrayList<>();
        for (Dinner dinner : dinnerIterable)
            responses.add(makeDinnerResponse(dinner, true)); //TODO: Not all true
        return responses;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public DinnerResponse createDinner(DinnerRequest request, User user) {
        Dinner dinner = new Dinner();
        dinner.setHost(user);
        editDinner(dinner, request);

        dinnerRepository.save(dinner);
        return makeDinnerResponse(dinner, true);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void editDinner(Dinner dinner, DinnerRequest request) {
        dinner.setTitle(request.getTitle().trim());
        dinner.setDescription(request.getDescription());
        dinner.setExpenses(request.getExpenses());
        dinner.setDate(ModelUtil.parseLocalDate(request.getDate()));
        dinner.setStartTime(ModelUtil.parseLocalTime(request.getStartTime()));
        dinner.setEndTime(ModelUtil.parseLocalTime(request.getEndTime()));
        dinner.setAddressLine(request.getAddressLine().trim());
        dinner.setPostCode(request.getPostCode().trim());
        dinner.setCity(request.getCity().trim());
        dinner.setMaxGuests(request.getMaxGuests());

        dinner.verifyModel();
    }
}
