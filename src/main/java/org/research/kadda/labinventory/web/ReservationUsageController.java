package org.research.kadda.labinventory.web;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.research.kadda.labinventory.data.JsonUtils;
import org.research.kadda.labinventory.data.ReservationUsageDto;
import org.research.kadda.labinventory.entity.ReservationUsage;
import org.research.kadda.labinventory.service.ReservationUsageService;
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

@RestController
@RequestMapping(path = "/reservationUsage")
public class ReservationUsageController {
	private static Logger logger = LogManager.getLogger(ReservationUsageController.class);
	private ReservationUsageService reservationUsageService;

	public ReservationUsageController(ReservationUsageService reservationUsageService) {
		this.reservationUsageService = reservationUsageService;
	}

	protected ReservationUsageController() {

	}

	@PostMapping("/add")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<String> addReservationUsage(@RequestBody ReservationUsageDto reservationUsageDto) {
		logger.info("Adding reservation");

		ReservationUsage reservationUsage = new ReservationUsage();
		reservationUsage.setReservationId(reservationUsageDto.getReservationId());
		reservationUsage.setProject(reservationUsageDto.getProject());
		reservationUsage.setCompound(reservationUsageDto.getCompound());
		reservationUsage.setBatch(reservationUsageDto.getBatch());
		reservationUsage.setSample(reservationUsageDto.getSample());
		reservationUsage.setSampleType(reservationUsageDto.getSampleType());
		reservationUsage.setSpecie(reservationUsageDto.getSpecie());
		reservationUsageService.save(reservationUsage);

		StringBuffer bodyResponse = new StringBuffer("{\"resUsageId\":" + reservationUsage.getId() + "}");
		return ResponseEntity.status(HttpStatus.CREATED).header("Content-Type", "application/json")
				.body(bodyResponse.toString());
	}

	@PutMapping("/update")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<String> updateReservationUsage(@RequestBody ReservationUsageDto reservationUsageDto) {
		logger.info("Update reservation...");

		Optional<ReservationUsage> resu = reservationUsageService.findById(String.valueOf(reservationUsageDto.getId()));
		ReservationUsage reservationUsage = resu.get();
		reservationUsage.setReservationId(reservationUsageDto.getReservationId());
		reservationUsage.setProject(reservationUsageDto.getProject());
		reservationUsage.setCompound(reservationUsageDto.getCompound());
		reservationUsage.setBatch(reservationUsageDto.getBatch());
		reservationUsage.setSample(reservationUsageDto.getSample());
		reservationUsage.setSampleType(reservationUsageDto.getSampleType());
		reservationUsage.setSpecie(reservationUsageDto.getSpecie());
		reservationUsageService.save(reservationUsage);

		logger.info("Update done.");

		StringBuffer bodyResponse = new StringBuffer("{\"reservationUsageId\":" + reservationUsage.getId() + "}");
		return ResponseEntity.status(HttpStatus.CREATED).header("Content-Type", "application/json")
				.body(bodyResponse.toString());
	}

	@DeleteMapping("/delete/{reservationUsageid}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> deleteReservationUsage(@PathVariable(value = "reservationUsageid") String reservationUsageid) {
		logger.info("Delete reservationUsage");
		HttpStatus status = HttpStatus.OK;
		StringBuffer bodyResponse = new StringBuffer("{");

		try {
			reservationUsageService.deleteReservationUsagebyId(reservationUsageid);
		} catch (NumberFormatException nfe) {
			status = HttpStatus.BAD_REQUEST;
			bodyResponse.append("\"message\":\"NumberFormatException. The reservationUsage id '" + reservationUsageid
					+ "' is not well formatted.\"");
		}
		bodyResponse.append("}");

		bodyResponse.append("}");
		return ResponseEntity.status(status).header("Content-Type", "application/json").body(bodyResponse.toString());
	}

	@GetMapping("/all")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> getAllReservationUsages() {
		logger.info("Getting all reservations");

		boolean firstReservationUsage = true;
		long count = reservationUsageService.count();
		Iterable<ReservationUsage> reservationUsages = reservationUsageService.findAll();
		StringBuffer bodyResponse = new StringBuffer("{");
		bodyResponse.append("\"count\":\"" + count + "\",");
		bodyResponse.append("\"reservationUsages\":[");
		try {
			for (ReservationUsage reservationUsage : reservationUsages) {
				if (firstReservationUsage) {
					firstReservationUsage = false;
				} else {
					bodyResponse.append(",");
				}
				bodyResponse.append(JsonUtils.mapToJson(reservationUsage));
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		bodyResponse.append("]}");
		return ResponseEntity.status(HttpStatus.OK).header("Content-Type", "application/json")
				.body(bodyResponse.toString());
	}

	@GetMapping("/{reservationUsageId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> getReservationUsage(
			@PathVariable(value = "reservationUsageId") String reservationUsageId) {
		logger.info("Getting reservation usage : " + reservationUsageId);

		Optional<ReservationUsage> reservationUsage = reservationUsageService.findById(reservationUsageId);
		if (reservationUsage == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Content-Type", "application/json")
					.body("{\"errorMessage\":\"ReservationUsage id must be an integer.\"}");

		}
		ReservationUsage resu = reservationUsage.get();

		StringBuffer bodyResponse = new StringBuffer();
		try {
			bodyResponse.append(JsonUtils.mapToJson(resu));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return ResponseEntity.status(HttpStatus.OK).header("Content-Type", "application/json")
				.body(bodyResponse.toString());
	}
	@GetMapping("/reservationId/{reservationId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> getReservationUsageByReservationId(
			@PathVariable(value = "reservationId") String reservationId) {
		logger.info("Getting reservationUsage for Reservation id:" + reservationId);

		boolean firstReservation = true;
		Iterable<ReservationUsage> reservationUsages = reservationUsageService
				.findReservationUsageByReservationId(reservationId);
		StringBuffer bodyResponse = new StringBuffer("{");
		bodyResponse.append("\"reservationUsages\":[");
		try {
			for (ReservationUsage reservationUsage : reservationUsages) {
				if (firstReservation) {
					firstReservation = false;
				} else {
					bodyResponse.append(",");
				}
				bodyResponse.append(JsonUtils.mapToJson(reservationUsage));
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		bodyResponse.append("]}");
		return ResponseEntity.status(HttpStatus.OK).header("Content-Type", "application/json")
				.body(bodyResponse.toString());
	}
	
	//getAllowedInstrumentForReservationUsages
	@GetMapping("/allowed-instruments")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> getAllowedInstrumentForReservationUsages() {
		logger.info("Getting instruments id for reservation usages");

		boolean firstAllw = true;
		Iterable<Integer> instIds = reservationUsageService.getAllowedInstrumentForReservationUsages();
		StringBuffer bodyResponse = new StringBuffer("{");
		bodyResponse.append("\"allowedInstrumentsResaUsage\":[");
		try {
			for (Integer i : instIds) {
				if (firstAllw) {
					firstAllw = false;
				} else {
					bodyResponse.append(",");
				}
				bodyResponse.append(JsonUtils.mapToJson(i));
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		bodyResponse.append("]}");
		return ResponseEntity.status(HttpStatus.OK).header("Content-Type", "application/json")
				.body(bodyResponse.toString());
	}
}
