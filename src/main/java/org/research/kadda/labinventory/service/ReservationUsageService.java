package org.research.kadda.labinventory.service;

import java.util.List;
import java.util.Optional;

import org.research.kadda.labinventory.entity.ReservationUsage;
import org.research.kadda.labinventory.repository.ReservationUsageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationUsageService {

	
	    private ReservationUsageRepository reservationUsageRepository;

	    @Autowired
	    public ReservationUsageService(ReservationUsageRepository reservationUsageRepository) {
	        this.reservationUsageRepository = reservationUsageRepository;
	    }

	    public Iterable<ReservationUsage> findAll() { return reservationUsageRepository.findAll(); }

	    public long count() { return reservationUsageRepository.count(); }

	    public Optional<ReservationUsage> findById(String resid) {
	        int id;
	        try {
	            id = Integer.valueOf(resid);
	        } catch (NumberFormatException nfe) {
	            return null;
	        }
	        return this.reservationUsageRepository.findById(id);
	    }

	    public List<ReservationUsage> findReservationUsageByReservationId(String reservationId) {
			int rid;
			try {
				rid = Integer.valueOf(reservationId);
			} catch (NumberFormatException nfe) {
				return null;
			}
			return this.reservationUsageRepository.findReservationUsageByReservationid(rid);
		}

	    public ReservationUsage save(ReservationUsage reservationUsage) {
	        return reservationUsageRepository.save(reservationUsage);
	    }

	    public void deleteReservationUsagebyId(String reservationUsageid) throws NumberFormatException {
			int id = Integer.valueOf(reservationUsageid);
			reservationUsageRepository.deleteById(id);
		}
	    
	    public List<Integer> getAllowedInstrumentForReservationUsages(){
	    	List<Integer> res = reservationUsageRepository.getAllowedInstrumentForReservationUsages(); 
	    	return res;
	    }
}
