package spiis.server.repository;

import org.springframework.data.repository.CrudRepository;
import spiis.server.model.Dinner;

public interface DinnerRepository extends CrudRepository<Dinner, Long> {
    Iterable<Dinner> findAllByCancelledFalse();
}
