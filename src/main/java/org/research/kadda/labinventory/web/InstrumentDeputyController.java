package org.research.kadda.labinventory.web;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.research.kadda.labinventory.data.InstrumentDeputyDto;
import org.research.kadda.labinventory.data.JsonUtils;
import org.research.kadda.labinventory.entity.InstrumentDeputy;
import org.research.kadda.labinventory.service.InstrumentDeputyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping(path = "/instrumentDeputy")
public class InstrumentDeputyController {
	private static Logger logger = LogManager.getLogger(InstrumentDeputyController.class);
	private InstrumentDeputyService instrumentDeputyService;

	public InstrumentDeputyController(InstrumentDeputyService instrumentDeputyService) {
		this.instrumentDeputyService = instrumentDeputyService;
	}

	protected InstrumentDeputyController() {

	}

	@GetMapping("/all")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> getAllInstrumentDeputies() {
		logger.info("Getting all instrumentDeputies");

		boolean firstInstrumentDeputy = true;
		long count = instrumentDeputyService.count();
		Iterable<InstrumentDeputy> instrumentDeputies = instrumentDeputyService.findAll();
		StringBuffer bodyResponse = new StringBuffer("{");
		bodyResponse.append("\"count\":\"" + count + "\",");
		bodyResponse.append("\"instrumentDeputies\":[");
		try {
			for (InstrumentDeputy instrumentDeputy : instrumentDeputies) {
				if (firstInstrumentDeputy) {
					firstInstrumentDeputy = false;
				} else {
					bodyResponse.append(",");
				}
				bodyResponse.append(JsonUtils.mapToJson(instrumentDeputy));
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
	public ResponseEntity<String> getInstrumentDeputiesByInstrumentId(@PathVariable(value = "instrumentId") int instrumentId) {
		logger.info("Getting instrumentDeputies : " + instrumentId);
		StringBuffer bodyResponse = new StringBuffer();

		List<String> deputies = instrumentDeputyService.findInstrumentDeputiesByInstrumentId(instrumentId);
		if (deputies == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.header("Content-Type", "application/json")
					.body("{\"errorMessage\":\"Instrument id must be an integer.\"}");

		}

		try {
			bodyResponse.append(JsonUtils.mapToJson(deputies));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return ResponseEntity.status(HttpStatus.OK).header("Content-Type", "application/json")
				.body(bodyResponse.toString());
	}
	
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addInstrumentDeputy(@RequestBody InstrumentDeputyDto instrumentDeputyDto) {
        logger.info("Adding instrumentDeputy");

        StringBuffer bodyResponse = new StringBuffer("{");

        InstrumentDeputy instrumentDeputy = new InstrumentDeputy();
        instrumentDeputy.setInstrumentId(instrumentDeputyDto.getInstrumentId()); 
        instrumentDeputy.setDeputy(instrumentDeputyDto.getDeputy());
        instrumentDeputyService.save(instrumentDeputy);
        bodyResponse.append("}");
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Content-Type", "application/json")
                .body(bodyResponse.toString());
    }
    
	
	@DeleteMapping("/delete/{instrumentId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> deleteDeputies(@PathVariable(value = "instrumentId") String instrumentId) {
		logger.info("Delete deputies for instrument " + instrumentId);
		HttpStatus status = HttpStatus.OK;
		StringBuffer bodyResponse = new StringBuffer("{");

		try {
			List<InstrumentDeputy> deputies = instrumentDeputyService.findByInstrumentId(instrumentId);
	        for(InstrumentDeputy deputy : deputies) {
	        	instrumentDeputyService.delete(deputy);
	        }
		} catch (NumberFormatException nfe) {
			status = HttpStatus.BAD_REQUEST;
			bodyResponse.append("\"message\":\"NumberFormatException. The instrument id '"+ instrumentId +"' is not well formatted.\"");
		}
		bodyResponse.append("}");
		bodyResponse.append("}");
		return ResponseEntity.status(status).header("Content-Type", "application/json")
				.body(bodyResponse.toString());
	}
	
}
