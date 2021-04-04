package spiis.server.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Data
public class CommentReply {
    private static final int MAX_COMMENT_REPLY_LENGTH = 4000;

    @Id
    @GeneratedValue
    @Nullable
    private Long id;

    @Column(nullable = false, length = MAX_COMMENT_REPLY_LENGTH)
    private String content;

    @ToString.Exclude
    @ManyToOne
    @Nullable
    private User commenter;

    @ToString.Exclude
    @ManyToOne(optional = false)
    @Nullable
    private Comment parentComment;

    @Nullable
    @Column(nullable = false)
    private OffsetDateTime createdTime;

    @Nullable
    @Column(nullable = false)
    private OffsetDateTime lastModifiedTime;

    @PrePersist
    protected void prePersist() {
        createdTime = OffsetDateTime.now();
        lastModifiedTime = createdTime;
    }

    @PreUpdate
    protected void preUpdate() {
        lastModifiedTime = OffsetDateTime.now();
    }

    public void setCommenter(@Nullable User commenter) {
        if (this.commenter != null)
            this.commenter.getCommentReplies().remove(this);
        this.commenter = commenter;
        if (commenter != null)
            commenter.getCommentReplies().add(this);
    }

    public void setParentComment(@Nullable Comment parentComment) {
        if (this.parentComment != null)
            this.parentComment.getReplies().remove(this);
        this.parentComment = parentComment;
        if (parentComment != null)
            parentComment.getReplies().add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof CommentReply)) return false;
        return Objects.equals(id, ((CommentReply) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
