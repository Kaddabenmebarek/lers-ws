package org.research.kadda.labinventory.web;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.research.kadda.labinventory.data.JsonUtils;
import org.research.kadda.labinventory.entity.InstrumentEmployeeGroup;
import org.research.kadda.labinventory.service.InstrumentEmployeeGroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping(path = "/instrument_employee_group")
public class InstrumentEmployeeGroupController {
	private static Logger logger = LogManager.getLogger(InstrumentEmployeeGroupController.class);
	private InstrumentEmployeeGroupService instrumentEmployeeGroupService;

	public InstrumentEmployeeGroupController(InstrumentEmployeeGroupService instrumentEmployeeGroupService) {
		this.instrumentEmployeeGroupService = instrumentEmployeeGroupService;
	}

	protected InstrumentEmployeeGroupController() {

	}

	@GetMapping("/all")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> getAllInstrumentGroups() {
		logger.info("Getting all employee group for instruments");

		boolean firstInstrumentEmployeeGroup = true;
		long count = instrumentEmployeeGroupService.count();
		Iterable<InstrumentEmployeeGroup> instrumentEmployeeGroups = instrumentEmployeeGroupService.findAll();
		StringBuffer bodyResponse = new StringBuffer("{");
		bodyResponse.append("\"count\":\"" + count + "\",");
		bodyResponse.append("\"instrumentEmployeeGroups\":[");
		try {
			for (InstrumentEmployeeGroup instrumentEmployeeGroup : instrumentEmployeeGroups) {
				if (firstInstrumentEmployeeGroup) {
					firstInstrumentEmployeeGroup = false;
				} else {
					bodyResponse.append(",");
				}
				bodyResponse.append(JsonUtils.mapToJson(instrumentEmployeeGroup));
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		bodyResponse.append("]}");

		return ResponseEntity.status(HttpStatus.OK).header("Content-Type", "application/json")
				.body(bodyResponse.toString());
	}

	@GetMapping("/instrument/{instrumentId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> getEmployeeGroupsByInstrumentId(@PathVariable(value = "instrumentId") String instrumentId) {
		logger.info("Getting employee groups for instrument : " + instrumentId);
		StringBuffer bodyResponse = new StringBuffer();

		List<String> employeeGroups = instrumentEmployeeGroupService.findGroupIdsByInstrumentId(instrumentId);
		if (employeeGroups == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.header("Content-Type", "application/json")
					.body("{\"errorMessage\":\"Instrument id must be an integer.\"}");

		}

		try {
			bodyResponse.append(JsonUtils.mapToJson(employeeGroups));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return ResponseEntity.status(HttpStatus.OK).header("Content-Type", "application/json")
				.body(bodyResponse.toString());
	}
	
	
	@DeleteMapping("/delete/{instrId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> deleteInstrumentEmployeeGroup(@PathVariable(value = "instrId") String instId) {
		logger.info("Delete instrument employee groups");
		HttpStatus status = HttpStatus.OK;
		StringBuffer bodyResponse = new StringBuffer("{");
		try {
			Iterable<InstrumentEmployeeGroup> instEmpGrps = instrumentEmployeeGroupService.findByInstrumentId(instId);
	        for(InstrumentEmployeeGroup ieg : instEmpGrps) {
	        	instrumentEmployeeGroupService.delete(ieg);
	        }
		} catch (NumberFormatException nfe) {
			status = HttpStatus.BAD_REQUEST;
			bodyResponse.append("\"message\":\"NumberFormatException. The instrument id '"+ instId +"' is not well formatted.\"");
		}
		bodyResponse.append("}");

		bodyResponse.append("}");
		return ResponseEntity.status(status).header("Content-Type", "application/json")
				.body(bodyResponse.toString());
	}
	
}
