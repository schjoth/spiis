package spiis.server.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.lang.Nullable;
import spiis.server.error.ModelError;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
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
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

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

    @Column(nullable = false)
    private LocalDate registrationDeadlineDate;

    @Column(nullable = false)
    private LocalTime registrationDeadlineTime;

    @Column(nullable = false)
    private boolean lockedByAdmin;


    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @Nullable
    private User host;

    @ToString.Exclude
    @ManyToMany
    private final Set<User> guests = new HashSet<>();

    @ManyToMany
    private final Set<User> blockedGuests = new HashSet<>();

    @OneToMany(mappedBy = "dinner", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<Comment> comments = new HashSet<>();

    @Nullable
    private OffsetDateTime createdTime;

    @PreUpdate
    public void verifyModel() {
        ModelUtil.requireNonNull(title);
        ModelUtil.requireNonNull(description);
        ModelUtil.requireNonNull(expenses);
        ModelUtil.requireNonNull(date);
        ModelUtil.requireNonNull(startTime);
        ModelUtil.requireNonNull(endTime);
        ModelUtil.requireNonNull(addressLine);
        ModelUtil.requireNonNull(postCode);
        ModelUtil.requireNonNull(city);
        ModelUtil.requireNonNull(host);
        ModelUtil.requireNonNull(guests);
        ModelUtil.requireNonNull(registrationDeadlineDate);
        ModelUtil.requireNonNull(registrationDeadlineTime);

        ModelUtil.ensureTextTrimAndLength(title, 4, 200, "title");
        ModelUtil.ensureTextMaxLength(description, MAX_DESCRIPTION_LENGTH, "description");
        ModelUtil.ensureTextMaxLength(expenses, MAX_DESCRIPTION_LENGTH, "expenses");
        ModelUtil.ensureTextTrimAndLength(addressLine, 1, 200, "address line");
        ModelUtil.ensureTextTrimAndLength(postCode, 1, 8, "post code");
        ModelUtil.ensureTextTrimAndLength(city, 1, 100, "city");

        if (maxGuests < 1)
            throw new ModelError("A dinner must have at least one guest space");

        if (getGuests().size() > getMaxGuests())
            throw new ModelError("There are more guests than allowed");

        if ((startTime.isAfter(endTime) || startTime.equals(endTime)))
            throw new ModelError("Start time can not be same as nor later than end time");

        if (registrationDeadlineDate.isAfter(date)
                || (registrationDeadlineDate.equals(date) && registrationDeadlineTime.isAfter(startTime)))
            throw new ModelError("registration deadline can not be later than start time");
    }

    public void verifyIsInFuture() {
        if (date.isBefore(LocalDate.now()) || (date.equals(LocalDate.now()) && (startTime.isBefore(LocalTime.now()))))
            throw new ModelError("The date or time selected is not valid");
    }

    @PrePersist
    protected void prePersist() {
        createdTime = OffsetDateTime.now();
        verifyModel();
    }

    public void setHost(@Nullable User user) {
        if (this.host != null)
            this.host.getHosting().remove(this);
        this.host = user;
        if (this.host != null)
            this.host.getHosting().add(this);
    }

    public void addGuest(User guest) {
        if (blockedGuests.contains(guest))
            throw new IllegalArgumentException("This user is blocked!");
        guests.add(guest);
        guest.getGuesting().add(this);
    }

    public void removeGuest(User guest) {
        guests.remove(guest);
        guest.getGuesting().remove(this);
    }

    public void removeAndBlockGuest(User guest) {
        removeGuest(guest);
        blockedGuests.add(guest);
        guest.getBlockedFrom().add(this);
    }

    public void unblockGuest(User guest) {
        blockedGuests.remove(guest);
        guest.getBlockedFrom().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Dinner)) return false;
        return Objects.equals(id, ((Dinner) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
