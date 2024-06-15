package org.research.kadda.labinventory.repository;

import org.research.kadda.labinventory.entity.ReservationUsage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReservationUsageRepository extends CrudRepository<ReservationUsage, Integer> {
    
    @Query("select ru from ReservationUsage ru where ru.reservationId = ?1")
    List<ReservationUsage> findReservationUsageByReservationid(int rid);

    @Query(value = "select INSTRUMENT_ID from CHEMINFRA.ALLOWED_INSTRUMENT_RESERVATION_USAGE", nativeQuery = true)
    List<Integer> getAllowedInstrumentForReservationUsages();
    
}
