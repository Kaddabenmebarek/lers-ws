package org.research.kadda.labinventory.repository;

import org.research.kadda.labinventory.entity.Instrument;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * Author: Kadda
 */

public interface InstrumentRepository extends CrudRepository<Instrument, Integer> {
    Optional<Instrument> findById(Integer id);

    @Query(value = "select l.OPTID from CHEMINFRA.RESOPTIONSLINK l where l.INSTID = ?1 order by l.OPTID", nativeQuery = true)
    List<Integer> findResoptionIdsByInstId(Integer instrId);

    @Query(value = "select g.GID from CHEMINFRA.GROUPINSTRUMENT g where g.IID = ?1", nativeQuery = true)
    List<Integer> findGroupIdsByInstrumentId(Integer instrId);

    @Query(value = "select RESTRICTED_INSTRUMENT_ID from CHEMINFRA.INSTRUMENT_RESTRICTION where INSTRUMENT_ID = ?1", nativeQuery = true)
	List<Integer> findRestrictedInstrumentsForInstrument(Integer instrId);
}
