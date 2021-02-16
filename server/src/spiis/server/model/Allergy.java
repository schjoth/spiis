package spiis.server.model;

import org.hibernate.annotations.NaturalId;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Allergy {
    @Id
    @GeneratedValue
    @Nullable
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @Nullable
    @NaturalId
    private User user;

    @Column(nullable = false)
    @NaturalId
    private String allergy;

    protected Allergy() {}

    public Allergy(String allergy) {
        this.allergy = allergy;
    }

    @Nullable
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Nullable
    public User getUser() {
        return user;
    }

    public void setUser(@Nullable User user) {
        if (this.user != null)
            this.user.getAllergies().remove(this);
        this.user = user;
        if (this.user != null)
            this.user.getAllergies().add(this);
    }

    public String getAllergy() {
        return allergy;
    }

    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Allergy)) return false;
        Allergy allergy1 = (Allergy) o;
        return Objects.equals(user, allergy1.user) && allergy.equals(allergy1.allergy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, allergy);
    }
}
