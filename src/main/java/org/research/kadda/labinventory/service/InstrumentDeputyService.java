package org.research.kadda.labinventory.service;

import java.util.List;
import java.util.Optional;

import org.research.kadda.labinventory.entity.InstrumentDeputy;
import org.research.kadda.labinventory.repository.InstrumentDeputyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstrumentDeputyService {

    private InstrumentDeputyRepository instrumentDeputyRepository;

    @Autowired
    public InstrumentDeputyService(InstrumentDeputyRepository instrumentDeputyRepository) {
        this.instrumentDeputyRepository = instrumentDeputyRepository;
    }

	public long count() {
		return instrumentDeputyRepository.count();
	}

	public Iterable<InstrumentDeputy> findAll() {
		return instrumentDeputyRepository.findAll();
	}

	public List<String> findInstrumentDeputiesByInstrumentId(int instrumentId) {
		return instrumentDeputyRepository.findInstrumentDeputiesByInstrumentId(instrumentId);
	}
	

    public InstrumentDeputy save(InstrumentDeputy instrumentDeputy) {
        return instrumentDeputyRepository.save(instrumentDeputy);
    }

	public Optional<InstrumentDeputy> findById(Integer instrumentDeputyId) {
		return instrumentDeputyRepository.findById(instrumentDeputyId);
	}
	
	public List<InstrumentDeputy> findByInstrumentId(String instrumentDeputyId) {
		return instrumentDeputyRepository.findByInstrumentId(Integer.valueOf(instrumentDeputyId));
	}

	public void deleteInstrumentDeputies(String instrumentId) {
		instrumentDeputyRepository.deleteInstrumentDeputies(Integer.valueOf(instrumentId));
	}
	
    public void delete(InstrumentDeputy instrumentDeputy) {
    	instrumentDeputyRepository.delete(instrumentDeputy);
    }

}
