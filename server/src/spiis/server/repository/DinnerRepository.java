package spiis.server.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import spiis.server.model.Dinner;

import java.time.LocalDate;
import java.time.LocalTime;

public interface DinnerRepository extends CrudRepository<Dinner, Long> {

    @Query("SELECT d FROM Dinner d WHERE d.cancelled = false AND "
            + "(d.date > :dateNow OR (d.date = :dateNow AND d.startTime > :timeNow))")
    Iterable<Dinner> findAllFuture(@Param("dateNow") LocalDate dateNow, @Param("timeNow") LocalTime timeNow);
}
