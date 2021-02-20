package spiis.server.api;

import java.time.OffsetDateTime;
import java.util.List;

public class DinnerResponse {

    private Long id;
    private String title;
    private OffsetDateTime time;
    private String place;
    private int maxPeople;
    private Long hostId;
    private List<Long> guestIds;

    public DinnerResponse(Long id, String title, OffsetDateTime time,
                          String place, int maxPeople, Long hostId, List<Long> guestIds) {
        this.id = id;
        this.title = title;
        this.time = time;
        this.place = place;
        this.maxPeople = maxPeople;
        this.hostId = hostId;
        this.guestIds = guestIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Long getHostId() {
        return hostId;
    }

    public void setHostId(Long hostId) {
        this.hostId = hostId;
    }

    public List<Long> getGuestIds() {
        return guestIds;
    }

    public void setGuestIds(List<Long> guestIds) {
        this.guestIds = guestIds;
    }

}
