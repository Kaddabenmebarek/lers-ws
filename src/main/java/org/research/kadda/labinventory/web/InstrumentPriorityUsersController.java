package org.research.kadda.labinventory.web;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.research.kadda.labinventory.data.InstrumentPriorityUsersDto;
import org.research.kadda.labinventory.data.JsonUtils;
import org.research.kadda.labinventory.entity.InstrumentPriorityUsers;
import org.research.kadda.labinventory.service.InstrumentPriorityUsersService;
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
@RequestMapping(path = "/instrumentPriorityUsers")
public class InstrumentPriorityUsersController {
	private static Logger logger = LogManager.getLogger(InstrumentPriorityUsersController.class);
	private InstrumentPriorityUsersService instrumentPriorityUsersService;

	public InstrumentPriorityUsersController(InstrumentPriorityUsersService instrumentPriorityUsersService) {
		this.instrumentPriorityUsersService = instrumentPriorityUsersService;
	}

	protected InstrumentPriorityUsersController() {

	}

	@GetMapping("/all")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> getAllInstrumentPriorityUsers() {
		logger.info("Getting all instrumentPriorityUsers");

		boolean firstInstrumentPriorityUser = true;
		long count = instrumentPriorityUsersService.count();
		Iterable<InstrumentPriorityUsers> instrumentPriorityUsersList = instrumentPriorityUsersService.findAll();
		StringBuffer bodyResponse = new StringBuffer("{");
		bodyResponse.append("\"count\":\"" + count + "\",");
		bodyResponse.append("\"instrumentPriorityUsers\":[");
		try {
			for (InstrumentPriorityUsers instrumentPriorityUsers : instrumentPriorityUsersList) {
				if (firstInstrumentPriorityUser) {
					firstInstrumentPriorityUser = false;
				} else {
					bodyResponse.append(",");
				}
				bodyResponse.append(JsonUtils.mapToJson(instrumentPriorityUsers));
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
	public ResponseEntity<String> getInstrumentPriorityUsersByInstrumentId(@PathVariable(value = "instrumentId") int instrumentId) {
		logger.info("Getting instrumentPriorityUsers : " + instrumentId);
		StringBuffer bodyResponse = new StringBuffer();

		List<String> prioUsers = instrumentPriorityUsersService.findInstrumentPrioriyUsersByInstrumentId(instrumentId);
		if (prioUsers == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.header("Content-Type", "application/json")
					.body("{\"errorMessage\":\"Instrument id must be an integer.\"}");
		}

		try {
			bodyResponse.append(JsonUtils.mapToJson(prioUsers));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return ResponseEntity.status(HttpStatus.OK).header("Content-Type", "application/json")
				.body(bodyResponse.toString());
	}
	
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addInstrumentPriorityUser(@RequestBody InstrumentPriorityUsersDto instrumentPriorityUsersDto) {
        logger.info("Adding instrument Priority User");

        StringBuffer bodyResponse = new StringBuffer("{");

        InstrumentPriorityUsers instrumentPriorityUsers = new InstrumentPriorityUsers();
        instrumentPriorityUsers.setInstrumentId(instrumentPriorityUsersDto.getInstrumentId()); 
        instrumentPriorityUsers.setPriorityUser(instrumentPriorityUsersDto.getPriorityUser());
        instrumentPriorityUsersService.save(instrumentPriorityUsers);
        bodyResponse.append("}");
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Content-Type", "application/json")
                .body(bodyResponse.toString());
    }
    
	
	@DeleteMapping("/delete/{instrumentId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> deleteInstrumentPriorityUsers(@PathVariable(value = "instrumentId") String instrumentId) {
		logger.info("Delete Priority Users for instrument " + instrumentId);
		HttpStatus status = HttpStatus.OK;
		StringBuffer bodyResponse = new StringBuffer("{");

		try {
			List<InstrumentPriorityUsers> instrumentPriorityUsers = instrumentPriorityUsersService.findByInstrumentId(instrumentId);
	        for(InstrumentPriorityUsers instPrioUsr : instrumentPriorityUsers) {
	        	instrumentPriorityUsersService.delete(instPrioUsr);
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
