package spiis.server.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
public class HostRating {

    @Id
    @GeneratedValue
    @Nullable
    private Long id;

    @Column(nullable = false)
    @ManyToOne
    private User rater;

    @Column(nullable = false)
    @ManyToOne
    private Dinner dinner;

    @Column(nullable = false)
    private int rating;

    public HostRating(User rater, Dinner dinner, int rating) {
        this.rater = rater;
        this.dinner = dinner;
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof HostRating)) return false;
        return Objects.equals(id, ((HostRating) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}