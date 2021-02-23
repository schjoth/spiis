package spiis.server.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.lang.Nullable;
import spiis.server.error.ModelError;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Dinner {

    private static final int MAX_DESCRIPTION_LENGTH = 40000;

    @Id
    @GeneratedValue
    @Nullable
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = MAX_DESCRIPTION_LENGTH)
    private String description;

    @Column(nullable = false)
    private OffsetDateTime startTime;

    @Column(nullable = false)
    private OffsetDateTime endTime;

    @Column(nullable = false)
    private String addressLine;

    @Column(nullable = false)
    private String postCode;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private int maxGuests;

    @Column(nullable = false)
    private boolean cancelled;

    @ManyToOne(fetch = FetchType.LAZY)
    @Nullable
    private User host;

    @ManyToMany
    private final Set<User> guests = new HashSet<>();

    @CreatedDate
    @Nullable
    private OffsetDateTime createdTime;

    public Dinner() {}

    @PrePersist
    @PreUpdate
    public void verifyModel() {
        Objects.requireNonNull(title);
        Objects.requireNonNull(description);
        Objects.requireNonNull(startTime);
        Objects.requireNonNull(endTime);
        Objects.requireNonNull(addressLine);
        Objects.requireNonNull(postCode);
        Objects.requireNonNull(city);

        ModelUtil.ensureTextTrimAndLength(title, 4, 200, "title");
        ModelUtil.ensureTextTrimAndLength(description, 0, MAX_DESCRIPTION_LENGTH, "title");
        ModelUtil.ensureTextTrimAndLength(addressLine, 1, 200, "address line");
        ModelUtil.ensureTextTrimAndLength(postCode, 1, 8, "post code");
        ModelUtil.ensureTextTrimAndLength(city, 1, 100, "city");

        if (maxGuests < 1)
            throw new ModelError("A dinner must have at least one guest space");
    }

    @Nullable
    public Long getId() {
        return id;
    }

    public void setId(@Nullable Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OffsetDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(OffsetDateTime startTime) {
        this.startTime = startTime;
    }

    public OffsetDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(OffsetDateTime endTime) {
        this.endTime = endTime;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getMaxGuests() {
        return maxGuests;
    }

    public void setMaxGuests(int maxGuests) {
        this.maxGuests = maxGuests;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
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
