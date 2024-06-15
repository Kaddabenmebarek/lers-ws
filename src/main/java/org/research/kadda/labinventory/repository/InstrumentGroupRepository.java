package org.research.kadda.labinventory.repository;

import org.research.kadda.labinventory.entity.InstrumentGroup;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InstrumentGroupRepository extends CrudRepository<InstrumentGroup, Integer> {
    @Query(value = "select gi.iid from CHEMINFRA.GROUPINSTRUMENT gi where gi.gid = ?1", nativeQuery = true)
    List<Integer> findInstrumentIdsByGroupId(int id);
}
