package org.research.kadda.labinventory.repository;

import org.research.kadda.labinventory.entity.Group;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Author: Kadda
 */

public interface GroupRepository extends CrudRepository<Group, String> {
    @Query("select distinct g.groupname from Group g group by g.groupname")
    List<String> findGroupNames();
}
