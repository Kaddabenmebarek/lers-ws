package org.research.kadda.labinventory.service;

import org.research.kadda.labinventory.entity.Instrument;
import org.research.kadda.labinventory.repository.InstrumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Author: Kadda
 */

@Service
public class InstrumentService {
    private InstrumentRepository instrumentRepository;

    public InstrumentService(InstrumentRepository instrumentRepository) {
        this.instrumentRepository = instrumentRepository;
    }

    public Iterable<Instrument> findAll() { return instrumentRepository.findAll(); }

    public long count() { return instrumentRepository.count(); }

    public Optional<Instrument> findById(String instrumentId) {
        int id;
        try {
            id = Integer.valueOf(instrumentId);
        } catch (NumberFormatException nfe) {
            return null;
        }
        return this.instrumentRepository.findById(id);
    }

    public List<Integer> findByResoptionIdsByInstrumentId(String instrId) {
        int id;
        try {
            id = Integer.valueOf(instrId);
        } catch (NumberFormatException nfe) {
            return null;
        }
        return this.instrumentRepository.findResoptionIdsByInstId(id);
    }

    public List<Integer> findGroupIdsByInstrumentId(String instrId) {
        int id;
        try {
            id = Integer.valueOf(instrId);
        } catch (NumberFormatException nfe) {
            return null;
        }
        return this.instrumentRepository.findGroupIdsByInstrumentId(id);
    }

    public Instrument save(Instrument instrument) {
        return instrumentRepository.save(instrument);
    }
    
    public void delete(Instrument instrument) {
        instrumentRepository.delete(instrument);
    }

	public List<Integer> findRestrictedInstrumentsForInstrument(String instrId) {
        int id;
        try {
            id = Integer.valueOf(instrId);
        } catch (NumberFormatException nfe) {
            return null;
        }
        return this.instrumentRepository.findRestrictedInstrumentsForInstrument(id);		
	}
}
