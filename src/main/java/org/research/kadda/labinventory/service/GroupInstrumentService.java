package org.research.kadda.labinventory.service;

import org.research.kadda.labinventory.entity.GroupInstrument;
import org.research.kadda.labinventory.entity.InstrumentDeputy;
import org.research.kadda.labinventory.repository.GroupInstrumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupInstrumentService {
    private GroupInstrumentRepository groupInstrumentRepository;

    public GroupInstrumentService(GroupInstrumentRepository groupInstrumentRepository) {
        this.groupInstrumentRepository = groupInstrumentRepository;
    }


	public boolean persistGroupInstrumentLink(GroupInstrument groupInstrument) {
		try {
			groupInstrumentRepository.save(groupInstrument);
			return true;
		} catch (Exception e) {
			return false;
		}
	}


	public Iterable<GroupInstrument> findByInstrId(String instrId) {
		int iid;
		try {
			iid = Integer.valueOf(instrId);
		} catch (NumberFormatException nfe) {
			return null;
		}
		return this.groupInstrumentRepository.findByInstrid(iid);
	}


	public void deleteGroupInstrumentByInstrument(String instId) {
		int id = Integer.valueOf(instId);
		groupInstrumentRepository.deleteByInstId(id);		
	}
	
	public void delete(GroupInstrument gi) {
		groupInstrumentRepository.delete(gi);
    }
}
