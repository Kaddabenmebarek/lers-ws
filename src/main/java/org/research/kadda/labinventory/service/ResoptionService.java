package org.research.kadda.labinventory.service;

import org.research.kadda.labinventory.entity.Resoption;
import org.research.kadda.labinventory.repository.ResoptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ResoptionService {
    private ResoptionRepository resoptionRepository;

    @Autowired
    public ResoptionService(ResoptionRepository resoptionRepository) {
        this.resoptionRepository = resoptionRepository;
    }

    public Iterable<Resoption> findAll() { return resoptionRepository.findAll(); }

    public long count() { return resoptionRepository.count(); }

    public Optional<Resoption> findById(String resoptionId) {
        int id;
        try {
            id = Integer.valueOf(resoptionId);
        } catch (NumberFormatException nfe) {
            return null;
        }
        return this.resoptionRepository.findById(id);
    }
}
