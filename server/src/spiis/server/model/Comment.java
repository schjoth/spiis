package spiis.server.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.ToString;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
public class Comment {

    private static final int MAX_COMMENT_LENGTH = 4000;

    public enum Visibility {
        //Important! Do not change the order!
        HOST_ONLY("host_only"),
        GUESTS_ONLY("guests_only"),
        PUBLIC("public");

        private final String name;
        Visibility(String name) {
            this.name = name;
        }

        @JsonCreator
        public static Visibility decode(String name) {
            return Arrays.stream(values()).filter(it -> it.name.equals(name)).findFirst().orElseThrow();
        }

        @JsonValue
        @Override
        public String toString() {
            return name;
        }
    }

    @Id
    @GeneratedValue
    @Nullable
    private Long id;

    @Column(nullable = false, length = MAX_COMMENT_LENGTH)
    private String content;

    @ToString.Exclude
    @ManyToOne
    @Nullable
    private User commenter;

    @ToString.Exclude
    @ManyToOne(optional = false)
    @Nullable
    private Dinner dinner;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Visibility visibility;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL)
    private Set<CommentReply> replies = new HashSet<>();

    @Column(nullable = false)
    private boolean deleted;

    @Column(nullable = false)
    private OffsetDateTime createdTime;

    @Column(nullable = false)
    private OffsetDateTime lastModifiedTime;

    @PrePersist
    protected void prePersist() {
        createdTime = OffsetDateTime.now().withNano(0);
        lastModifiedTime = createdTime;
    }

    @PreUpdate
    protected void preUpdate() {
        lastModifiedTime = OffsetDateTime.now().withNano(0);
    }

    public void setCommenter(@Nullable User commenter) {
        if (this.commenter != null)
            this.commenter.getComments().remove(this);
        this.commenter = commenter;
        if (commenter != null)
            commenter.getComments().add(this);
    }

    public void setDinner(@Nullable Dinner dinner) {
        if (this.dinner != null)
            this.dinner.getComments().remove(this);
        this.dinner = dinner;
        if (dinner != null)
            dinner.getComments().add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Comment)) return false;
        return Objects.equals(id, ((Comment) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
