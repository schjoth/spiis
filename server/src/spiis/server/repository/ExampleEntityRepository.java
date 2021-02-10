package spiis.server.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import spiis.server.model.ExampleEntity;

import java.util.Optional;

public interface ExampleEntityRepository extends PagingAndSortingRepository<ExampleEntity, Long> {

}
