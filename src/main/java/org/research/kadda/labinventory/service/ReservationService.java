package org.research.kadda.labinventory.service;

import java.util.List;
import java.util.Optional;

import org.research.kadda.labinventory.entity.Reservation;
import org.research.kadda.labinventory.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

	private ReservationRepository reservationRepository;

	public ReservationService(ReservationRepository reservationRepository) {
		this.reservationRepository = reservationRepository;
	}

	public Iterable<Reservation> findAll() {
		return reservationRepository.findAll();
	}

	public long count() {
		return reservationRepository.count();
	}

	public Optional<Reservation> findById(String resid) {
		int id;
		try {
			id = Integer.valueOf(resid);
		} catch (NumberFormatException nfe) {
			return null;
		}
		return this.reservationRepository.findById(id);
	}

	public List<Reservation> findReservationsByInstrId(String instrId) {
		int iid;
		try {
			iid = Integer.valueOf(instrId);
		} catch (NumberFormatException nfe) {
			return null;
		}
		return this.reservationRepository.findReservationsByInstrid(iid);
	}

	public List<Reservation> findReservationsByInstrIdAndDateRangeStrict(List<Integer> instrIds, Integer reservationId,
			String fromDate, String toDate, Boolean strict) {
		return this.reservationRepository.findReservationsByInstrIdAndStrictDateRange(instrIds, reservationId, fromDate,
				toDate);
	}

	public List<Reservation> findReservationsByInstrIdAndDateRange(List<Integer> instrIds, String fromDate,
			String toDate, Boolean strict) {
		return this.reservationRepository.findReservationsByInstrIdAndDateRange(instrIds, fromDate, toDate);
	}

	public List<Reservation> findJustEndedReservations(String toDate1, String toDate2) {
		return this.reservationRepository.findJustEndedReservations(toDate1, toDate2);
	}

	public Reservation save(Reservation reservation) {
		return reservationRepository.save(reservation);
	}

	public void deleteReservationbyId(String resid) throws NumberFormatException {
		int id = Integer.valueOf(resid);
		reservationRepository.deleteById(id);
	}

}
