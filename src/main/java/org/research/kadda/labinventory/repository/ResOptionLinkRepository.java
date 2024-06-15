package org.research.kadda.labinventory.repository;

import java.util.List;

import org.research.kadda.labinventory.entity.ResOptionLink;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ResOptionLinkRepository extends CrudRepository<ResOptionLink, Integer> {

	@Query("select rol from ResOptionLink rol where rol.resOptionLinkpk.instId = ?1")
    List<ResOptionLink> findByInstrId(int instrId);
	
}
