package spiis.server.repository;

import org.springframework.data.repository.CrudRepository;
import spiis.server.model.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long> {
}
