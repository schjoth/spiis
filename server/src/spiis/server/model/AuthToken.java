package spiis.server.model;

import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
public class AuthToken {

    @Id
    @GeneratedValue
    @Nullable
    private Long id;

    @Column(nullable = false)
    @NaturalId
    private String token;

    @ToString.Exclude
    @OneToOne(optional = false)
    @Nullable
    private User user;

    @CreatedDate
    @Nullable
    private OffsetDateTime createdDate;

    protected AuthToken() {}

    public AuthToken(String token) {
        this.token = token;
    }

    @Nullable
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Nullable
    public User getUser() {
        return user;
    }

    public void setUser(@Nullable User user) {
        if (user == this.user)
            return;

        User oldUser = this.user;

        this.user = null;
        if (oldUser != null)
            oldUser.setToken(null);

        this.user = user;
        if (user != null)
            user.setToken(this);
    }

    @Nullable
    public OffsetDateTime getCreatedDate() {
        return createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof AuthToken)) return false;
        return Objects.equals(id, ((AuthToken) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
