package org.research.kadda.labinventory.repository;

import org.research.kadda.labinventory.entity.Reservation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReservationRepository extends CrudRepository<Reservation, Integer> {
    @Query("select r from Reservation r where r.instrid = ?1")
    List<Reservation> findReservationsByInstrid(int instrId);

    @Query("select r from Reservation r where r.instrid in (?1) and (" + 
    		"(r.fromTime between TO_DATE(?2,'yyyy/mm/dd hh24:mi:ss') and TO_DATE(?3,'yyyy/mm/dd hh24:mi:ss')"+
    		" or r.toTime between TO_DATE(?2,'yyyy/mm/dd hh24:mi:ss') and TO_DATE(?3,'yyyy/mm/dd hh24:mi:ss'))" + 
    		"or (r.fromTime >= TO_DATE(?2,'yyyy/mm/dd hh24:mi:ss') and r.toTime <= TO_DATE(?3,'yyyy/mm/dd hh24:mi:ss'))" + 
    		"or (r.fromTime <= TO_DATE(?2,'yyyy/mm/dd hh24:mi:ss') and r.toTime >= TO_DATE(?3,'yyyy/mm/dd hh24:mi:ss'))" +
    		")")
    List<Reservation> findReservationsByInstrIdAndDateRange(List<Integer> instrIds, String fromDate, String toDate);

    @Query("select r from Reservation r where r.instrid in (?1) and r.id <> ?2 and (" + 
    		"(r.fromTime between TO_DATE(?3,'yyyy/mm/dd hh24:mi:ss') and TO_DATE(?4,'yyyy/mm/dd hh24:mi:ss')"+
    		" or r.toTime between TO_DATE(?3,'yyyy/mm/dd hh24:mi:ss') and TO_DATE(?4,'yyyy/mm/dd hh24:mi:ss'))" + 
    		"or (r.fromTime >= TO_DATE(?3,'yyyy/mm/dd hh24:mi:ss') and r.toTime <= TO_DATE(?4,'yyyy/mm/dd hh24:mi:ss'))" + 
    		"or (r.fromTime <= TO_DATE(?3,'yyyy/mm/dd hh24:mi:ss') and r.toTime >= TO_DATE(?4,'yyyy/mm/dd hh24:mi:ss'))" +
    		")")
    List<Reservation> findReservationsByInstrIdAndStrictDateRange(List<Integer> instrIds, Integer reservationId, String fromDate, String toDate);
    
    @Query("select r from Reservation r where r.toTime between TO_DATE(?1,'yyyy/mm/dd hh24:mi:ss') and TO_DATE(?2,'yyyy/mm/dd hh24:mi:ss')")
    List<Reservation> findJustEndedReservations(String toDate1, String toDate2);    
}
