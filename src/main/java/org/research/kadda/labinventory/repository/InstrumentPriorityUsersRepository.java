package org.research.kadda.labinventory.repository;

import java.util.List;
import java.util.Optional;

import org.research.kadda.labinventory.entity.InstrumentPriorityUsers;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface InstrumentPriorityUsersRepository extends CrudRepository<InstrumentPriorityUsers, Integer> {
	Optional<InstrumentPriorityUsers> findById(Integer id);

	@Query(value = "select PRIORITY_USER from CHEMINFRA.INSTRUMENT_PRIORITY_USERS where instrument_id = ?1", nativeQuery = true)
    List<String> findInstrumentPrioriyUsersByInstrumentId(int id);
	
    @Query(value = "delete from CHEMINFRA.INSTRUMENT_PRIORITY_USERS where instrument_id = ?1", nativeQuery = true)
    void deleteInstrumentPrioriyUsersByInstrumentId(int instrumentId);

    @Query(value = "select * from CHEMINFRA.INSTRUMENT_PRIORITY_USERS where instrument_id = ?1", nativeQuery = true)
	List<InstrumentPriorityUsers> findByInstrumentId(Integer instrumentDeputyId);
	

}
