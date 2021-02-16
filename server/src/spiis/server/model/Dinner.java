package spiis.server.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Dinner {

    @Id
    @GeneratedValue
    @Nullable
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private OffsetDateTime time;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String place;

    @Column(nullable = false)
    private int maxPeople;

    /**
     * The host can invite people outside of the app.
     * These people take up seats without being part of the guest list.
     */
    @Column(nullable = false)
    private int extraGuests;

    @ManyToOne(fetch = FetchType.LAZY)
    @Nullable
    private User host;

    @ManyToMany
    private final Set<User> guests = new HashSet<>();

    @CreatedDate
    @Nullable
    private OffsetDateTime createdTime;

    protected Dinner() {}

    public Dinner(String title, OffsetDateTime time, String description, String place, int maxPeople, int extraGuests) {
        this.title = title;
        this.time = time;
        this.description = description;
        this.place = place;
        this.maxPeople = maxPeople;
        this.extraGuests = extraGuests;
    }

    @Nullable
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public int getExtraGuests() {
        return extraGuests;
    }

    public void setExtraGuests(int extraGuests) {
        this.extraGuests = extraGuests;
    }

    public void setHost(@Nullable User user) {
        if (this.host != null)
            this.host.getHosting().remove(this);
        this.host = user;
        if (this.host != null)
            this.host.getHosting().add(this);
    }

    @Nullable
    public User getHost() {
        return host;
    }

    public void addGuest(User guest) {
        guests.add(guest);
        guest.getGuesting().add(this);
    }

    public void removeGuest(User guest) {
        guests.remove(guest);
        guest.getGuesting().remove(this);
    }

    public Set<User> getGuests() {
        return guests;
    }

    @Nullable
    public OffsetDateTime getCreatedTime() {
        return createdTime;
    }
}
