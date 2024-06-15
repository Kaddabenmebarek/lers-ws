package org.research.kadda.labinventory.repository;

import java.util.List;

import org.research.kadda.labinventory.entity.SynthesisLibraryOrder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SynthesisOrderRepository extends CrudRepository<SynthesisLibraryOrder, Integer> {
    
    @Query("select s from SynthesisLibraryOrder s where s.title = ?1")
    List<SynthesisLibraryOrder> findSynthesisOrderByTitle(String title);
    
    
    /**
     * S=startdate and E=enddate, in range if:  
     * [S----E]
     * S[----]E
     * S[----E]
     * [S----]E
     * @param fromDate
     * @param toDate
     * @return
     */
    @Query("select s from SynthesisLibraryOrder s where" +
    		" ((s.fromTime between TO_DATE(?1,'yyyy/mm/dd hh24:mi:ss') and TO_DATE(?2,'yyyy/mm/dd hh24:mi:ss')) and (s.toTime between TO_DATE(?1,'yyyy/mm/dd hh24:mi:ss') and TO_DATE(?2,'yyyy/mm/dd hh24:mi:ss')))" +
    		" or ((s.fromTime between TO_DATE(?1,'yyyy/mm/dd hh24:mi:ss') and TO_DATE(?2,'yyyy/mm/dd hh24:mi:ss')) and s.toTime >= TO_DATE(?2,'yyyy/mm/dd hh24:mi:ss'))" +
    		" or (s.fromTime <= TO_DATE(?1,'yyyy/mm/dd hh24:mi:ss') and (s.toTime between TO_DATE(?1,'yyyy/mm/dd hh24:mi:ss') and TO_DATE(?2,'yyyy/mm/dd hh24:mi:ss')))" +
			" or (s.fromTime <= TO_DATE(?1,'yyyy/mm/dd hh24:mi:ss') and s.toTime >= TO_DATE(?2,'yyyy/mm/dd hh24:mi:ss'))")
    List<SynthesisLibraryOrder> findSynthesisOrderByRangeDate(String fromDate, String toDate);
    
    
    @Query(value = "SELECT * FROM cheminfra.synthesis_library_order WHERE" + 
    		" (fromtime BETWEEN to_date(?1, 'yyyy/mm/dd hh24:mi:ss') AND to_date(?2, 'yyyy/mm/dd hh24:mi:ss')  AND totime BETWEEN to_date(?1, 'yyyy/mm/dd hh24:mi:ss') AND to_date(?2, 'yyyy/mm/dd hh24:mi:ss'))" + 
    		" OR (fromtime BETWEEN to_date(?1, 'yyyy/mm/dd hh24:mi:ss') AND to_date(?2, 'yyyy/mm/dd hh24:mi:ss') AND totime >= to_date(?2, 'yyyy/mm/dd hh24:mi:ss'))" + 
    		" OR (fromtime <= to_date(?1, 'yyyy/mm/dd hh24:mi:ss') AND  totime BETWEEN to_date(?1, 'yyyy/mm/dd hh24:mi:ss') AND to_date(?2, 'yyyy/mm/dd hh24:mi:ss'))" + 
    		" OR (fromtime <= to_date(?1, 'yyyy/mm/dd hh24:mi:ss') AND totime >= to_date(?2, 'yyyy/mm/dd hh24:mi:ss'))", nativeQuery = true)
	List<SynthesisLibraryOrder> findOrdersByRangeDate(String fromDate, String toDate);
    
}
