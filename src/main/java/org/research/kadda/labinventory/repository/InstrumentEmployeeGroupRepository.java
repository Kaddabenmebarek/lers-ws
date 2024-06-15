package org.research.kadda.labinventory.repository;

import org.research.kadda.labinventory.entity.InstrumentEmployeeGroup;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface InstrumentEmployeeGroupRepository extends CrudRepository<InstrumentEmployeeGroup, Integer> {
	Optional<InstrumentEmployeeGroup> findById(Integer id);

	@Query(value = "select employee_group_name from CHEMINFRA.INSTRUMENT_EMPLOYEE_GROUP where instrument_id = ?1", nativeQuery = true)
    List<String> findGroupsByInstrumentId(int id);
	
    @Query(value = "select * from CHEMINFRA.INSTRUMENT_EMPLOYEE_GROUP where instrument_id = ?1", nativeQuery = true)
	List<InstrumentEmployeeGroup> findByInstrumentId(Integer instrumentDeputyId);

    @Query(value = "select group_id from CHEMINFRA.INSTRUMENT_EMPLOYEE_GROUP where instrument_id = ?1", nativeQuery = true)
	List<Integer> findGroupIdsByInstrumentId(Integer valueOf);
	

}
