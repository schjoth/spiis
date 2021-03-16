package spiis.server.model;

import lombok.ToString;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Allergy {
    @Id
    @GeneratedValue
    @Nullable
    private Long id;

    @ToString.Exclude
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @Nullable
    private User user;

    @Column(nullable = false)
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
        if (o == null) return false;
        if (!(o instanceof Allergy)) return false;
        return Objects.equals(id, ((Allergy) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, allergy);
    }
}
