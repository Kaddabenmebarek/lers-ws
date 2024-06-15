package org.research.kadda.labinventory.service;

import java.util.List;
import java.util.Optional;

import org.research.kadda.labinventory.entity.InstrumentGroup;
import org.research.kadda.labinventory.repository.InstrumentGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstrumentGroupService {

	
	    private InstrumentGroupRepository instrumentGroupRepository;

	    @Autowired
	    public InstrumentGroupService(InstrumentGroupRepository instrumentGroupRepository) {
	        this.instrumentGroupRepository = instrumentGroupRepository;
	    }

	    public Iterable<InstrumentGroup> findAll() { return instrumentGroupRepository.findAll(); }

	    public long count() { return instrumentGroupRepository.count(); }

	    public Optional<InstrumentGroup> findById(String groupId) {
	        int id;
	        try {
	            id = Integer.valueOf(groupId);
	        } catch (NumberFormatException nfe) {
	            return null;
	        }
	        return this.instrumentGroupRepository.findById(id);
	    }

		public List<Integer> findInstrumentIdsByGroupId(String id) {
			int gid;
			try {
				gid = Integer.valueOf(id);
			} catch (NumberFormatException nfe) {
				return null;
			}
	    	return this.instrumentGroupRepository.findInstrumentIdsByGroupId(gid);
		}
}
