package spiis.server.repository;

import org.springframework.data.repository.CrudRepository;
import spiis.server.model.CommentReply;

public interface CommentReplyRepository extends CrudRepository<CommentReply, Long> {
}
