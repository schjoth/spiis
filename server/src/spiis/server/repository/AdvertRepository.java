package spiis.server.repository;

import org.springframework.data.repository.CrudRepository;
import spiis.server.model.Advert;

public interface AdvertRepository extends CrudRepository<Advert, Long> {
}
