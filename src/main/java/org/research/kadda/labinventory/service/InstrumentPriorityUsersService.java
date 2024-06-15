package org.research.kadda.labinventory.service;

import java.util.List;
import java.util.Optional;

import org.research.kadda.labinventory.entity.InstrumentPriorityUsers;
import org.research.kadda.labinventory.repository.InstrumentPriorityUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstrumentPriorityUsersService {

    private InstrumentPriorityUsersRepository instrumentPriorityUsersRepository;

    @Autowired
    public InstrumentPriorityUsersService(InstrumentPriorityUsersRepository instrumentPriorityUsersRepository) {
        this.instrumentPriorityUsersRepository = instrumentPriorityUsersRepository;
    }

	public long count() {
		return instrumentPriorityUsersRepository.count();
	}

	public Iterable<InstrumentPriorityUsers> findAll() {
		return instrumentPriorityUsersRepository.findAll();
	}

	public List<String> findInstrumentPrioriyUsersByInstrumentId(int instrumentId) {
		return instrumentPriorityUsersRepository.findInstrumentPrioriyUsersByInstrumentId(instrumentId);
	}
	

    public InstrumentPriorityUsers save(InstrumentPriorityUsers instrumentPriorityUsers) {
        return instrumentPriorityUsersRepository.save(instrumentPriorityUsers);
    }

	public Optional<InstrumentPriorityUsers> findById(Integer instrumentDeputyId) {
		return instrumentPriorityUsersRepository.findById(instrumentDeputyId);
	}
	
	public List<InstrumentPriorityUsers> findByInstrumentId(String instrumentDeputyId) {
		return instrumentPriorityUsersRepository.findByInstrumentId(Integer.valueOf(instrumentDeputyId));
	}

	public void deleteInstrumentPrioriyUsersByInstrumentId(String instrumentId) {
		instrumentPriorityUsersRepository.deleteInstrumentPrioriyUsersByInstrumentId(Integer.valueOf(instrumentId));
	}
	
    public void delete(InstrumentPriorityUsers instrumentPriorityUsers) {
    	instrumentPriorityUsersRepository.delete(instrumentPriorityUsers);
    }

}
