package org.research.kadda.labinventory.repository;

import java.util.List;

import org.research.kadda.labinventory.entity.GroupInstrument;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface GroupInstrumentRepository extends CrudRepository<GroupInstrument, Integer> {

	@Query("select g from GroupInstrument g where g.groupInstrumentpk.instId = ?1")
    List<GroupInstrument> findByInstrid(int iid);
	
	@Query(value = "delete from CHEMINFRA.GROUPINSTRUMENT where IID = ?1", nativeQuery = true)
	void deleteByInstId(int id);
}
