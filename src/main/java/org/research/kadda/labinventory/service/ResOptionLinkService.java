package org.research.kadda.labinventory.service;

import org.research.kadda.labinventory.entity.ResOptionLink;
import org.research.kadda.labinventory.repository.ResOptionLinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResOptionLinkService {
    private ResOptionLinkRepository resOptionLinkRepository;

    @Autowired
    public ResOptionLinkService(ResOptionLinkRepository resOptionLinkRepository) {
        this.resOptionLinkRepository = resOptionLinkRepository;
    }

	public boolean persistResOptionLink(ResOptionLink resOptionLink) {
		try {
			resOptionLinkRepository.save(resOptionLink);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Iterable<ResOptionLink> findByInstrId(String instrId) {
		return resOptionLinkRepository.findByInstrId(Integer.valueOf(instrId));
	}

	public void delete(ResOptionLink resOptLnk) {
		resOptionLinkRepository.delete(resOptLnk);
	}
}
