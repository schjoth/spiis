package spiis.server.api;

import lombok.Builder;
import lombok.Data;
import org.springframework.lang.Nullable;
import spiis.server.model.Comment;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
public class CommentResponse {
    private Long commentId;
    @Nullable
    private String content; //null if deleted
    private Long dinnerId;
    private Comment.Visibility visibility;
    @Nullable
    private Long userId;
    @Nullable
    private String userLastName;
    @Nullable
    private String userFirstName;
    private OffsetDateTime postedAt;
    private OffsetDateTime editedAt;

    private List<CommentReplyResponse> replies;
}
