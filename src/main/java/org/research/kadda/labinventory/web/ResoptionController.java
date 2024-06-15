package org.research.kadda.labinventory.web;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.research.kadda.labinventory.data.JsonUtils;
import org.research.kadda.labinventory.entity.Resoption;
import org.research.kadda.labinventory.service.ResoptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping(path = "/resoption")
public class ResoptionController {
	private static Logger logger = LogManager.getLogger(ResoptionController.class);
	private ResoptionService resoptionService;

	public ResoptionController(ResoptionService resoptionService) {
		this.resoptionService = resoptionService;
	}

	protected ResoptionController() {

	}
	
	@GetMapping("/all")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> getAllResoptions() {
		logger.info("Getting all resoptions");

		boolean firstResoption = true;
		long count = resoptionService.count();
		Iterable<Resoption> resoptions = resoptionService.findAll();
		StringBuffer bodyResponse = new StringBuffer("{");
		bodyResponse.append("\"count\":\"" + count + "\",");
		bodyResponse.append("\"resoptions\":[");
		try {
			for (Resoption resoption : resoptions) {
				if (firstResoption) {
					firstResoption = false;
				} else {
					bodyResponse.append(",");
				}
				bodyResponse.append(JsonUtils.mapToJson(resoption));
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		bodyResponse.append("]}");

		return ResponseEntity.status(HttpStatus.OK).header("Content-Type", "application/json")
				.body(bodyResponse.toString());
	}

	@GetMapping("/{resoptionId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> getResoption(@PathVariable(value = "resoptionId") String resoptionId) {
		logger.info("Getting resoption : " + resoptionId);

		Optional<Resoption> resoption = resoptionService.findById(resoptionId);
		if (resoption == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Content-Type", "application/json")
					.body("{\"errorMessage\":\"Resoption id must be an integer.\"}");

		}
		Resoption resopt = resoption.get();

		StringBuffer bodyResponse = new StringBuffer();
		try {
			bodyResponse.append(JsonUtils.mapToJson(resopt));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return ResponseEntity.status(HttpStatus.OK).header("Content-Type", "application/json")
				.body(bodyResponse.toString());
	}
}
