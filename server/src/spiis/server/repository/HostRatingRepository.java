package spiis.server.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import spiis.server.model.Dinner;
import spiis.server.model.HostRating;
import spiis.server.model.User;

import java.util.Optional;

public interface HostRatingRepository extends CrudRepository<HostRating, Long> {

    Optional<HostRating> findByRaterAndDinner(User rater, Dinner dinner);

    @Query("SELECT AVG(hr.rating) FROM HostRating hr WHERE hr.dinner.host = ?1")
    Optional<Float> getAverageHostRating(User user);

}
