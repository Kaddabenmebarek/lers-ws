package org.research.kadda.labinventory.service;

import org.research.kadda.labinventory.entity.Group;
import org.research.kadda.labinventory.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: Kadda
 */

@Service
public class GroupService {

    private GroupRepository groupRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public Iterable<Group> findAll() { return groupRepository.findAll(); }

    public long count() { return groupRepository.count(); }

    public List<String> findGroupNames() { return groupRepository.findGroupNames(); }
}
