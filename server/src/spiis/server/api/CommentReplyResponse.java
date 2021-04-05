package spiis.server.api;

import lombok.Builder;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.time.OffsetDateTime;

@Data
@Builder
public class CommentReplyResponse {
    private Long replyId;
    @Nullable
    private String content;
    @Nullable
    private Long userId;
    @Nullable
    private String userFirstName;
    @Nullable
    private String userLastName;
    private OffsetDateTime postedAt;
    private OffsetDateTime editedAt;
}
