package spiis.server.api;

import spiis.server.model.Dinner;
import spiis.server.model.User;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class DinnerResponse {

    private String title;
    private Long id;
    private OffsetDateTime time;
    private String place;
    private int maxPeople;
    private Set<Long> guestIds;

    public DinnerResponse(Dinner dinner) {
        this.title = dinner.getTitle();
        this.id = dinner.getId();
        this.time = dinner.getTime();
        this.place = dinner.getPlace();
        this.maxPeople = dinner.getMaxPeople();
        this.guestIds = dinner.getGuests().stream().map(User::getId).collect(Collectors.toSet());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OffsetDateTime getTime() {
        return time;
    }

    public void setTime(OffsetDateTime time) {
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(int maxPeople) {
        this.maxPeople = maxPeople;
    }

    public Set<Long> getGuestIds() {
        return guestIds;
    }

    public void setGuestIds(Set<Long> guestIds) {
        this.guestIds = guestIds;
    }

}
