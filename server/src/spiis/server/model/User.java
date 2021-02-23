package spiis.server.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.lang.Nullable;
import spiis.server.error.ModelError;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue
    @Nullable
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private String city;

    @OneToMany(mappedBy = "host")
    private final Set<Dinner> hosting = new HashSet<>();

    @ManyToMany(mappedBy = "guests")
    private final Set<Dinner> guesting = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<Allergy> allergies = new HashSet<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Nullable
    private AuthToken token;

    @CreatedDate
    @Nullable
    private OffsetDateTime createdTime;

    @LastModifiedDate
    @Nullable
    private OffsetDateTime lastModifiedTime;

    public User() {}

    @PrePersist
    @PreUpdate
    public void verifyModel() {
        Objects.requireNonNull(email);
        Objects.requireNonNull(password);
        Objects.requireNonNull(firstName);
        Objects.requireNonNull(lastName);
        Objects.requireNonNull(city);
        Objects.requireNonNull(hosting);
        Objects.requireNonNull(guesting);
        Objects.requireNonNull(allergies);

        if (!email.contains("@"))
            throw new ModelError("Invalid email");

        ModelUtil.ensureTextTrimAndLength(email, 4, 100, "email");
        ModelUtil.ensureTextTrimAndLength(firstName, 2, 40, "first name");
        ModelUtil.ensureTextTrimAndLength(lastName, 2, 40, "last name");
        ModelUtil.ensureTextTrimAndLength(city, 1, 40, "city");

        if (age < 0 || age > 200)
            throw new ModelError("Age is illegal");
    }

    @Nullable
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String location) {
        this.city = city;
    }

    public Set<Dinner> getHosting() {
        return hosting;
    }

    public Set<Dinner> getGuesting() {
        return guesting;
    }

    public Set<Allergy> getAllergies() {
        return allergies;
    }

    public void addAllergy(Allergy allergy) {
        allergy.setUser(this);
    }

    public void removeAllergy(Allergy allergy) {
        allergy.setUser(null);
    }

    @Nullable
    public AuthToken getToken() {
        return token;
    }

    public void setToken(@Nullable AuthToken token) {
        if (token == this.token)
            return;

        AuthToken oldToken = this.token;

        this.token = null;
        if (oldToken != null)
            oldToken.setUser(null);

        this.token = token;
        if (token != null)
            token.setUser(this);
    }

    @Nullable
    public OffsetDateTime getCreatedTime() {
        return createdTime;
    }

    @Nullable
    public OffsetDateTime getLastModifiedTime() {
        return lastModifiedTime;
    }

    @PreRemove
    protected void onRemove() {
        for (Dinner hosting : this.hosting) {
            hosting.setHost(null);
        }
        for (Dinner guesting : this.guesting) {
            guesting.removeGuest(this);
        }
    }
}
