package spiis.server.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.lang.Nullable;
import spiis.server.error.ModelError;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
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

    @Column(nullable = false, length = MAX_DESCRIPTION_LENGTH)
    private String expenses;

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
        Objects.requireNonNull(expenses);
        Objects.requireNonNull(startTime);
        Objects.requireNonNull(endTime);
        Objects.requireNonNull(addressLine);
        Objects.requireNonNull(postCode);
        Objects.requireNonNull(city);
        Objects.requireNonNull(host);
        Objects.requireNonNull(guests);

        ModelUtil.ensureTextTrimAndLength(title, 4, 200, "title");
        ModelUtil.ensureTextMaxLength(description, MAX_DESCRIPTION_LENGTH, "title");
        ModelUtil.ensureTextMaxLength(expenses, MAX_DESCRIPTION_LENGTH, "expenses");
        ModelUtil.ensureTextTrimAndLength(addressLine, 1, 200, "address line");
        ModelUtil.ensureTextTrimAndLength(postCode, 1, 8, "post code");
        ModelUtil.ensureTextTrimAndLength(city, 1, 100, "city");

        if (maxGuests < 1)
            throw new ModelError("A dinner must have at least one guest space");

        if (getGuests().size() > getMaxGuests())
            throw new ModelError("There are more guests than allowed");
    }

    public void setHost(@Nullable User user) {
        if (this.host != null)
            this.host.getHosting().remove(this);
        this.host = user;
        if (this.host != null)
            this.host.getHosting().add(this);
    }

    public void addGuest(User guest) {
        guests.add(guest);
        guest.getGuesting().add(this);
    }

    public void removeGuest(User guest) {
        guests.remove(guest);
        guest.getGuesting().remove(this);
    }
}
