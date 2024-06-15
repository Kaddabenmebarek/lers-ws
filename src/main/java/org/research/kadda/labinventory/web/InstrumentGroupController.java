package org.research.kadda.labinventory.web;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.research.kadda.labinventory.data.JsonUtils;
import org.research.kadda.labinventory.entity.InstrumentGroup;
import org.research.kadda.labinventory.service.InstrumentGroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Author: Kadda
 */

@RestController
@RequestMapping(path = "/instrumentGroup")
public class InstrumentGroupController {
	private static Logger logger = LogManager.getLogger(InstrumentGroupController.class);
	private InstrumentGroupService instrumentGroupService;

	public InstrumentGroupController(InstrumentGroupService instrumentGroupService) {
		this.instrumentGroupService = instrumentGroupService;
	}

	protected InstrumentGroupController() {

	}

	@GetMapping("/all")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> getAllInstrumentGroups() {
		logger.info("Getting all instrumentGroups");

		boolean firstInstrumentGroup = true;
		long count = instrumentGroupService.count();
		Iterable<InstrumentGroup> instrumentGroups = instrumentGroupService.findAll();
		StringBuffer bodyResponse = new StringBuffer("{");
		bodyResponse.append("\"count\":\"" + count + "\",");
		bodyResponse.append("\"instrumentGroups\":[");
		try {
			for (InstrumentGroup instrumentGroup : instrumentGroups) {
				if (firstInstrumentGroup) {
					firstInstrumentGroup = false;
				} else {
					bodyResponse.append(",");
				}
				bodyResponse.append(JsonUtils.mapToJson(instrumentGroup));
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		bodyResponse.append("]}");

		return ResponseEntity.status(HttpStatus.OK).header("Content-Type", "application/json")
				.body(bodyResponse.toString());
	}

	@GetMapping("/{instrumentGroupId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> getInstrumentGroup(
			@PathVariable(value = "instrumentGroupId") String instrumentGroupId) {
		logger.info("Getting instrumentGroup : " + instrumentGroupId);

		Optional<InstrumentGroup> instrumentGroup = instrumentGroupService.findById(instrumentGroupId);
		if (instrumentGroup == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Content-Type", "application/json")
					.body("{\"errorMessage\":\"InstrumentGroup id must be an integer.\"}");

		}
		InstrumentGroup instGroup = instrumentGroup.get();

		StringBuffer bodyResponse = new StringBuffer();
		try {
			bodyResponse.append(JsonUtils.mapToJson(instGroup));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return ResponseEntity.status(HttpStatus.OK).header("Content-Type", "application/json")
				.body(bodyResponse.toString());
	}

	@GetMapping("/category/{instrumentGroupId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> getInstrumentIdsByGroupId(@PathVariable(value = "instrumentGroupId") String instrumentGroupId) {
		logger.info("Getting instrumentGroup : " + instrumentGroupId);
		StringBuffer bodyResponse = new StringBuffer();

		List<Integer> ids = instrumentGroupService.findInstrumentIdsByGroupId(instrumentGroupId);
		if (ids == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.header("Content-Type", "application/json")
					.body("{\"errorMessage\":\"Instrument id must be an integer.\"}");

		}

		try {
			bodyResponse.append(JsonUtils.mapToJson(ids));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return ResponseEntity.status(HttpStatus.OK).header("Content-Type", "application/json")
				.body(bodyResponse.toString());
	}
}
