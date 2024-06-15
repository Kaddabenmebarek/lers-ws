package org.research.kadda.labinventory.service;

import java.util.ArrayList;
import java.util.List;

import org.research.kadda.labinventory.entity.InstrumentEmployeeGroup;
import org.research.kadda.labinventory.repository.InstrumentEmployeeGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstrumentEmployeeGroupService {

    private InstrumentEmployeeGroupRepository instrumentEmployeeGroupRepository;

    @Autowired
    public InstrumentEmployeeGroupService(InstrumentEmployeeGroupRepository instrumentEmployeeRepository) {
        this.instrumentEmployeeGroupRepository = instrumentEmployeeRepository;
    }

	public long count() {
		return instrumentEmployeeGroupRepository.count();
	}

	public Iterable<InstrumentEmployeeGroup> findAll() {
		return instrumentEmployeeGroupRepository.findAll();
	}

	public List<String> findGroupsByInstrumentId(String instrumentId) {
		return instrumentEmployeeGroupRepository.findGroupsByInstrumentId(Integer.valueOf(instrumentId));
	}
	
	public List<String> findGroupIdsByInstrumentId(String instrumentId) {
		List<String> groupIds = new ArrayList<String>();
		List<Integer> groups = instrumentEmployeeGroupRepository.findGroupIdsByInstrumentId(Integer.valueOf(instrumentId));
		for(Integer idGroup : groups) {
			groupIds.add(String.valueOf(idGroup));
		}
		return groupIds;
	}

    public InstrumentEmployeeGroup save(InstrumentEmployeeGroup instrumentEmployeeGroup) {
        return instrumentEmployeeGroupRepository.save(instrumentEmployeeGroup);
    }
	
	public List<InstrumentEmployeeGroup> findByInstrumentId(String instrumentId) {
		return instrumentEmployeeGroupRepository.findByInstrumentId(Integer.valueOf(instrumentId));
	}

	public void delete(InstrumentEmployeeGroup ieg) {
		instrumentEmployeeGroupRepository.delete(ieg);		
	}


}
