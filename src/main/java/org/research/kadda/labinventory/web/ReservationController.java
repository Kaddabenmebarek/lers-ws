package org.research.kadda.labinventory.web;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.research.kadda.labinventory.data.JsonUtils;
import org.research.kadda.labinventory.data.ReservationDto;
import org.research.kadda.labinventory.entity.Reservation;
import org.research.kadda.labinventory.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
@RequestMapping(path = "/reservation")
public class ReservationController {
	private static Logger logger = LogManager.getLogger(ReservationController.class);
	private ReservationService reservationService;

	public ReservationController(ReservationService reservationService) {
		this.reservationService = reservationService;
	}

	protected ReservationController() {

	}

	@GetMapping("/all")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> getAllReservations() {
		logger.info("Getting all reservations");

		boolean firstReservation = true;
		long count = reservationService.count();
		Iterable<Reservation> reservations = reservationService.findAll();
		StringBuffer bodyResponse = new StringBuffer("{");
		bodyResponse.append("\"count\":\"" + count + "\",");
		bodyResponse.append("\"reservations\":[");
		try {
			for (Reservation reservation : reservations) {
				if (firstReservation) {
					firstReservation = false;
				} else {
					bodyResponse.append(",");
				}
				bodyResponse.append(JsonUtils.mapToJson(reservation));
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		bodyResponse.append("]}");
		return ResponseEntity.status(HttpStatus.OK).header("Content-Type", "application/json")
				.body(bodyResponse.toString());
	}

	@GetMapping("/{reservationId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> getReservation(@PathVariable(value = "reservationId") String reservationId) {
		logger.info("Getting reservation : " + reservationId);

		Optional<Reservation> reservation = reservationService.findById(reservationId);
		if (reservation == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Content-Type", "application/json")
					.body("{\"errorMessage\":\"Reservation id must be an integer.\"}");

		}
		Reservation res = reservation.get();

		StringBuffer bodyResponse = new StringBuffer();
		try {
			bodyResponse.append(JsonUtils.mapToJson(res));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return ResponseEntity.status(HttpStatus.OK).header("Content-Type", "application/json")
				.body(bodyResponse.toString());
	}

	@GetMapping("/instr/{instrId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> getReservationsByInstrId(@PathVariable(value = "instrId") String instrId) {
		logger.info("Getting reservations for Instrument id:" + instrId);

		boolean firstReservation = true;
		Iterable<Reservation> reservations = reservationService.findReservationsByInstrId(instrId);
		StringBuffer bodyResponse = new StringBuffer("{");
		bodyResponse.append("\"reservations\":[");
		try {
			for (Reservation reservation : reservations) {
				if (firstReservation) {
					firstReservation = false;
				} else {
					bodyResponse.append(",");
				}
				bodyResponse.append(JsonUtils.mapToJson(reservation));
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		bodyResponse.append("]}");
		return ResponseEntity.status(HttpStatus.OK).header("Content-Type", "application/json")
				.body(bodyResponse.toString());
	}

	@PostMapping("/instruments")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> getReservationsByInstrIdsAndDateRange(
			@RequestBody String jsonPayload) {
		logger.info("Getting reservations for instrument list within a time range");
		Iterable<Reservation> reservations = null;
		StringBuffer bodyResponse = new StringBuffer("{");

		try {
			String jsoninstrIds = JsonUtils.getJsonNode(jsonPayload, "instrIds").toString();
			JsonNode resaNode = JsonUtils.getJsonNode(jsonPayload, "reservationId");
			Integer reservationId = null; 
			if(resaNode !=null) {
				reservationId = JsonUtils.getJsonNode(jsonPayload, "reservationId").intValue();
			}
			Boolean strict = JsonUtils.getJsonNode(jsonPayload, "strict").booleanValue();
			String fromDate = StringUtils.replace(JsonUtils.getJsonNode(jsonPayload, "startDate").toString(), "\"", "");
			String toDate = StringUtils.replace(JsonUtils.getJsonNode(jsonPayload, "endDate").toString(), "\"", "");
			List<Integer> instrIds = JsonUtils.mapFromJson(jsoninstrIds, List.class);

			boolean firstReservation = true;
			
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date from = simpleDateFormat.parse(fromDate);
			Date to = simpleDateFormat.parse(toDate);
			String fromDateTime =  simpleDateFormat.format(from);
			String toDateTime =  simpleDateFormat.format(to);
			
			if (strict && reservationId != null) {
				reservations = reservationService.findReservationsByInstrIdAndDateRangeStrict(instrIds,reservationId,fromDateTime,toDateTime,strict);
			}else {
				reservations = reservationService.findReservationsByInstrIdAndDateRange(instrIds,fromDateTime,toDateTime,strict);
			}
			

			bodyResponse.append("\"reservations\":[");
			for (Reservation reservation : reservations) {
				if (firstReservation) {
					firstReservation = false;
				} else {
					bodyResponse.append(",");
				}
				bodyResponse.append(JsonUtils.mapToJson(reservation));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		bodyResponse.append("]}");
		return ResponseEntity.status(HttpStatus.OK).header("Content-Type", "application/json")
				.body(bodyResponse.toString());
	}

	@PostMapping("/add")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<String> addReservation(@RequestBody ReservationDto reservationDto) {
		logger.info("Adding reservation");

		Reservation reservation = new Reservation();
		reservation.setInstrid(reservationDto.getInstrid());
		reservation.setFromTime(reservationDto.getFromTime());
		reservation.setToTime(reservationDto.getToTime());
		reservation.setUsername(reservationDto.getUsername());
		reservation.setRemark(reservationDto.getRemark());
		reservation.setResoptid(reservationDto.getResoptid());
		reservation.setRatio(reservationDto.getRatio());
		reservationService.save(reservation);

		StringBuffer bodyResponse = new StringBuffer("{\"resId\":" + reservation.getId() + "}");
		return ResponseEntity.status(HttpStatus.CREATED).header("Content-Type", "application/json")
				.body(bodyResponse.toString());
	}

	@PutMapping("/update")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<String> updateReservation(@RequestBody ReservationDto reservationDto) {
		logger.info("Update reservation...");

		Optional<Reservation> res = reservationService.findById(String.valueOf(reservationDto.getId()));
		Reservation reservation = res.get();
		reservation.setFromTime(reservationDto.getFromTime());
		reservation.setToTime(reservationDto.getToTime());
		reservation.setUsername(reservationDto.getUsername());
		reservation.setRemark(reservationDto.getRemark());
		reservation.setResoptid(reservationDto.getResoptid());
		reservation.setRatio(reservationDto.getRatio());
		reservationService.save(reservation);

		logger.info("Update done.");

		StringBuffer bodyResponse = new StringBuffer("{\"resId\":" + reservation.getId() + "}");
		return ResponseEntity.status(HttpStatus.CREATED).header("Content-Type", "application/json")
				.body(bodyResponse.toString());
	}

	@DeleteMapping("/delete/{resid}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> deleteReservation(@PathVariable(value = "resid") String resid) {
		logger.info("Delete reservation");
		HttpStatus status = HttpStatus.OK;
		StringBuffer bodyResponse = new StringBuffer("{");

		try {
			reservationService.deleteReservationbyId(resid);
		} catch (NumberFormatException nfe) {
			status = HttpStatus.BAD_REQUEST;
			bodyResponse.append("\"message\":\"NumberFormatException. The reservation id '"+ resid +"' is not well formatted.\"");
		}
		bodyResponse.append("}");

		bodyResponse.append("}");
		return ResponseEntity.status(status).header("Content-Type", "application/json")
				.body(bodyResponse.toString());
	}
	
	//use requestparameter instead
	@GetMapping("/instrument-in-range/{instrId}/{from}/{to}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> getReservationsByInstrIdsDateRange(@PathVariable Integer instrId,
			@PathVariable Long from, @PathVariable Long to) {
		logger.info("Getting reservations for instrument list within a time range");
		Iterable<Reservation> reservations = null;
		StringBuffer bodyResponse = new StringBuffer("{");

		try {
			boolean firstReservation = true;
			List<Integer> instrIds = new ArrayList<Integer>();
			instrIds.add(Integer.valueOf(instrId));
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//add 1min to 'from' to make sure that it doesn't overlap
			from += (60 * 1000);
			//remove 1min to 'to' to make sure that it doesn't overlap
			to -= (60 * 1000);
			String fromDateTime = simpleDateFormat.format(new Date(Long.valueOf(from)));
			String toDateTime = simpleDateFormat.format(new Date(Long.valueOf(to)));
			reservations = reservationService.findReservationsByInstrIdAndDateRange(instrIds, fromDateTime, toDateTime,
					false);
			bodyResponse.append("\"reservations\":[");
			for (Reservation reservation : reservations) {
				if (firstReservation) {
					firstReservation = false;
				} else {
					bodyResponse.append(",");
				}
				bodyResponse.append(JsonUtils.mapToJson(reservation));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		bodyResponse.append("]}");
		return ResponseEntity.status(HttpStatus.OK).header("Content-Type", "application/json")
				.body(bodyResponse.toString());
	}

}
