package org.research.kadda.labinventory.repository;

import org.research.kadda.labinventory.entity.InstrumentDeputy;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface InstrumentDeputyRepository extends CrudRepository<InstrumentDeputy, Integer> {
	Optional<InstrumentDeputy> findById(Integer id);

	@Query(value = "select deputy from CHEMINFRA.INSTRUMENT_DEPUTY where instrument_id = ?1", nativeQuery = true)
    List<String> findInstrumentDeputiesByInstrumentId(int id);
	
    @Query(value = "delete from CHEMINFRA.INSTRUMENT_DEPUTY where instrument_id = ?1", nativeQuery = true)
    void deleteInstrumentDeputies(int instrumentId);

    @Query(value = "select * from CHEMINFRA.INSTRUMENT_DEPUTY where instrument_id = ?1", nativeQuery = true)
	List<InstrumentDeputy> findByInstrumentId(Integer instrumentDeputyId);
	

}
