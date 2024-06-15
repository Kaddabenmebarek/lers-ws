package org.research.kadda.labinventory.service;

import java.util.Optional;

import org.research.kadda.labinventory.entity.SynthesisLibraryOrder;
import org.research.kadda.labinventory.repository.SynthesisOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SynthesisOrderService {
    private SynthesisOrderRepository synthesisOrderRepository;

    @Autowired
    public SynthesisOrderService(SynthesisOrderRepository cSOrderRepository) {
        this.synthesisOrderRepository = cSOrderRepository;
    }

    public Iterable<SynthesisLibraryOrder> findAll() { return synthesisOrderRepository.findAll(); }


    public SynthesisLibraryOrder save(SynthesisLibraryOrder cSOrder) {
        return synthesisOrderRepository.save(cSOrder);
    }
    
    public void delete(SynthesisLibraryOrder cSOrder) {
    	synthesisOrderRepository.delete(cSOrder);
    }
    
    public Optional<SynthesisLibraryOrder> findById(String cSOrderId) {
        int id;
        try {
            id = Integer.valueOf(cSOrderId);
        } catch (NumberFormatException nfe) {
            return null;
        }
        return this.synthesisOrderRepository.findById(id);
    }

	public Iterable<SynthesisLibraryOrder> findAllInRange(String fromDate, String toDate) {
		return synthesisOrderRepository.findOrdersByRangeDate(fromDate, toDate);
	}
}
