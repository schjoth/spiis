package spiis.server.api;

import lombok.Data;
import spiis.server.model.Comment;

@Data
public class CommentRequest {
    private String content;
    private Comment.Visibility visibility;
}
