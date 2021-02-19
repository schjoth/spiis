package spiis.server.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import spiis.server.model.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.token.token = ?1")
    Optional<User> findUserByToken(String token);

    Optional<User> findUserByEmail(String email);
}
