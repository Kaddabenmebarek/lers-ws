package org.research.kadda.labinventory.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.research.kadda.labinventory.data.JsonUtils;
import org.research.kadda.labinventory.data.ResOptionLinkDto;
import org.research.kadda.labinventory.entity.ResOptionLink;
import org.research.kadda.labinventory.entity.ResOptionLinkPK;
import org.research.kadda.labinventory.service.ResOptionLinkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping(path = "/resoptionlink")
public class ResOptionLinkController {
	
	private static Logger logger = LogManager.getLogger(ResOptionLinkController.class);
	private ResOptionLinkService resOptionLinkService;

	public ResOptionLinkController(ResOptionLinkService resOptionLinkService) {
		this.resOptionLinkService = resOptionLinkService;
	}

	protected ResOptionLinkController() {
	}

	@PostMapping("/add")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<String> addResOptionLink(@RequestBody ResOptionLinkDto resOptionLinkDto) {
		logger.info("Adding reservation option link");
		StringBuffer bodyResponse = new StringBuffer("{");
		
		ResOptionLinkPK resOptLnkPk = new ResOptionLinkPK();
		resOptLnkPk.setInstId(resOptionLinkDto.getInstId());
		resOptLnkPk.setOptId(resOptionLinkDto.getOptId());
		ResOptionLink resOptLnk = new ResOptionLink();
		resOptLnk.setPk(resOptLnkPk);
		
		if(!resOptionLinkService.persistResOptionLink(resOptLnk)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Content-Type", "application/json")
					.body("{\"errorMessage\":\"Resoption id must be an integer.\"}");
		}
		
		bodyResponse.append("}");
		
		return ResponseEntity.status(HttpStatus.OK).header("Content-Type", "application/json")
				.body(bodyResponse.toString());
	}
	
	@GetMapping("/instr/{instrId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> getResOptionLinkByInstrId(@PathVariable(value = "instrId") String instrId) {
		logger.info("Getting group reservation options for Instrument id:" + instrId);

		boolean firstROL = true;
		Iterable<ResOptionLink> resOptionsLink = resOptionLinkService.findByInstrId(instrId);
		StringBuffer bodyResponse = new StringBuffer("{");
		bodyResponse.append("\"groupInstruments\":[");
		try {
			for (ResOptionLink resOptLnk : resOptionsLink) {
				if (firstROL) {
					firstROL = false;
				} else {
					bodyResponse.append(",");
				}
				bodyResponse.append(JsonUtils.mapToJson(resOptLnk));
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		bodyResponse.append("]}");
		return ResponseEntity.status(HttpStatus.OK).header("Content-Type", "application/json")
				.body(bodyResponse.toString());
	}
	
	@GetMapping("/delete/{instrId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> deleteGroupInstrument(@PathVariable(value = "instrId") String instId) {
		logger.info("Delete groups instrument");
		HttpStatus status = HttpStatus.OK;
		StringBuffer bodyResponse = new StringBuffer("{");
		try {
			Iterable<ResOptionLink> resOptLinks = resOptionLinkService.findByInstrId(instId);
	        for(ResOptionLink resOptLnk : resOptLinks) {
	        	resOptionLinkService.delete(resOptLnk);
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
