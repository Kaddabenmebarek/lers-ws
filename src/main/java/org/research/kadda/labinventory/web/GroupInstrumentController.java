package org.research.kadda.labinventory.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.research.kadda.labinventory.data.GroupInstrumentDto;
import org.research.kadda.labinventory.data.JsonUtils;
import org.research.kadda.labinventory.entity.GroupInstrument;
import org.research.kadda.labinventory.entity.GroupInstrumentPK;
import org.research.kadda.labinventory.service.GroupInstrumentService;
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
@RequestMapping(path = "/groupinstrument")
public class GroupInstrumentController {
	
	private static Logger logger = LogManager.getLogger(GroupInstrumentController.class);
	private GroupInstrumentService groupInstrumentService;

	public GroupInstrumentController(GroupInstrumentService groupInstrumentService) {
		this.groupInstrumentService = groupInstrumentService;
	}

	protected GroupInstrumentController() {
	}


	@PostMapping("/add")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<String> addResOptionLink(@RequestBody GroupInstrumentDto groupInstrumentDto) {
		logger.info("Adding group instrument link");
		StringBuffer bodyResponse = new StringBuffer("{");
		
		GroupInstrumentPK groupInstrumentPK = new GroupInstrumentPK();
		groupInstrumentPK.setInstId(groupInstrumentDto.getInstId());
		groupInstrumentPK.setgId(groupInstrumentDto.getgId());
		GroupInstrument groupInstrument = new GroupInstrument();
		groupInstrument.setGroupInstrumentpk(groupInstrumentPK);
		
		if(!groupInstrumentService.persistGroupInstrumentLink(groupInstrument)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Content-Type", "application/json")
					.body("{\"errorMessage\":\"Resoption id must be an integer.\"}");
		}
		
		bodyResponse.append("}");
		
		return ResponseEntity.status(HttpStatus.OK).header("Content-Type", "application/json")
				.body(bodyResponse.toString());
	}
	
	@GetMapping("/instr/{instrId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> getGroupInstrumentByInstrId(@PathVariable(value = "instrId") String instrId) {
		logger.info("Getting group instruments for Instrument id:" + instrId);

		boolean firstGI = true;
		Iterable<GroupInstrument> groupInstruments = groupInstrumentService.findByInstrId(instrId);
		StringBuffer bodyResponse = new StringBuffer("{");
		bodyResponse.append("\"groupInstruments\":[");
		try {
			for (GroupInstrument groupInstrument : groupInstruments) {
				if (firstGI) {
					firstGI = false;
				} else {
					bodyResponse.append(",");
				}
				bodyResponse.append(JsonUtils.mapToJson(groupInstrument));
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		bodyResponse.append("]}");
		return ResponseEntity.status(HttpStatus.OK).header("Content-Type", "application/json")
				.body(bodyResponse.toString());
	}
	
	@DeleteMapping("/delete/{instrId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> deleteGroupInstrument(@PathVariable(value = "instrId") String instId) {
		logger.info("Delete groups instrument");
		HttpStatus status = HttpStatus.OK;
		StringBuffer bodyResponse = new StringBuffer("{");
		try {
			Iterable<GroupInstrument> grpsInst = groupInstrumentService.findByInstrId(instId);
	        for(GroupInstrument gi : grpsInst) {
	        	groupInstrumentService.delete(gi);
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
