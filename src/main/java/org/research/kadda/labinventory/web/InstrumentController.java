package org.research.kadda.labinventory.web;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.research.kadda.labinventory.data.InstrumentDto;
import org.research.kadda.labinventory.data.JsonUtils;
import org.research.kadda.labinventory.entity.Instrument;
import org.research.kadda.labinventory.service.InstrumentService;
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

/**
 * Author: Kadda
 */

@RestController
@RequestMapping(path = "/instrument")
public class InstrumentController {
    private static Logger logger = LogManager.getLogger(InstrumentController.class);
    private InstrumentService instrumentService;

    public InstrumentController(InstrumentService instrumentService) {
        this.instrumentService = instrumentService;
    }


    protected InstrumentController() {

    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> getAllInstruments() {
        logger.info("Getting all instruments");

        boolean firstInstrument = true;
        long count = instrumentService.count();
        Iterable<Instrument> instruments = instrumentService.findAll();
        StringBuffer bodyResponse = new StringBuffer("{");
        bodyResponse.append("\"count\":\"" + count + "\",");
        bodyResponse.append("\"instruments\":[");
        try {
            for (Instrument instrument : instruments) {
                if (firstInstrument) {
                    firstInstrument = false;
                } else {
                    bodyResponse.append(",");
                }

                bodyResponse.append(JsonUtils.mapToJson(instrument));
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        bodyResponse.append("]}");

        return ResponseEntity.status(HttpStatus.OK)
                .header("Content-Type", "application/json;encoding=UTF-8")
                .body(bodyResponse.toString());
    }

    @GetMapping("/{instrumentId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> getInstrument(@PathVariable(value = "instrumentId") String instrumentId) {
        logger.info("Getting instrument : " + instrumentId);

        Optional<Instrument> instrument = instrumentService.findById(instrumentId);
        if (instrument == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .header("Content-Type", "application/json")
                    .body("{\"errorMessage\":\"Instrument id must be an integer.\"}");

        }
        Instrument inst = instrument.get();

        StringBuffer bodyResponse = new StringBuffer();
        try {
            bodyResponse.append(JsonUtils.mapToJson(inst));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.OK)
                .header("Content-Type", "application/json")
                .body(bodyResponse.toString());
    }

    @GetMapping("/{instrId}/resoptions")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> getResoptionsForInstrument(@PathVariable(value = "instrId") String instrId) {
        logger.info("Getting resoptions for instrument : " + instrId);

        List<Integer> resoptionIds = instrumentService.findByResoptionIdsByInstrumentId(instrId);
        if (resoptionIds == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .header("Content-Type", "application/json")
                    .body("{\"errorMessage\":\"Instrument id must be an integer.\"}");

        }
        StringBuffer bodyResponse = new StringBuffer();
        try {
            bodyResponse.append(JsonUtils.mapToJson(resoptionIds));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.OK)
                .header("Content-Type", "application/json")
                .body(bodyResponse.toString());
    }
    
    @GetMapping("/{instrId}/groupids")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> getGroupIdsForInstrument(@PathVariable(value = "instrId") String instrId) {
        logger.info("Getting group ids for instrument : " + instrId);

        List<Integer> groupIds = instrumentService.findGroupIdsByInstrumentId(instrId);
        if (groupIds == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .header("Content-Type", "application/json")
                    .body("{\"errorMessage\":\"Instrument id must be an integer.\"}");

        }
        StringBuffer bodyResponse = new StringBuffer();
        try {
            bodyResponse.append(JsonUtils.mapToJson(groupIds));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.OK)
                .header("Content-Type", "application/json")
                .body(bodyResponse.toString());
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addInstrument(@RequestBody InstrumentDto instrumentDto) {
        logger.info("Adding instrument");

        Instrument instrument = new Instrument();
        instrument.setGroupname(instrumentDto.getGroupname());
        instrument.setInfoMessage(instrumentDto.getInfoMessage());
        instrument.setInfoTitle(instrumentDto.getInfoTitle());
        instrument.setIsPublic(instrumentDto.getIsPublic());
        instrument.setSelectOverlap(instrumentDto.getSelectOverlap());
        instrument.setReservable(instrumentDto.getReservable());
        instrument.setUsername(instrumentDto.getUsername());
        instrument.setName(instrumentDto.getName());
        instrument.setStatus(instrumentDto.getStatus());
        instrument.setDescription(instrumentDto.getDescription());
        instrument.setLocation(instrumentDto.getLocation());
        instrument.setEmailNotification(instrumentDto.getEmailNotification());
        instrument.setStartTimepoint(instrumentDto.getStartTimepoint());
        instrument.setMaxDays(instrumentDto.getMaxDays());
        
        instrumentService.save(instrument);
        
        StringBuffer bodyResponse = new StringBuffer("{\"instId\":" + instrument.getId() + "}");
		return ResponseEntity.status(HttpStatus.CREATED).header("Content-Type", "application/json")
				.body(bodyResponse.toString());
    }
    
	@PutMapping("/update")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> updateInstrument(@RequestBody InstrumentDto instrumentDto) {
		logger.info("Updating instrument");
		String instrumentId = String.valueOf(instrumentDto.getId());
		Optional<Instrument> instrument = instrumentService.findById(instrumentId);
        if (instrument == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .header("Content-Type", "application/json")
                    .body("{\"errorMessage\":\"Instrument not saved.\"}");

        }
		Instrument inst = instrument.get();
		inst.setDescription(instrumentDto.getDescription());
		inst.setGroupname(instrumentDto.getGroupname());
		inst.setInfoMessage(instrumentDto.getInfoMessage());
		inst.setInfoTitle(instrumentDto.getInfoTitle());
		inst.setIsPublic(instrumentDto.getIsPublic());
        inst.setSelectOverlap(instrumentDto.getSelectOverlap());
		inst.setLocation(instrumentDto.getLocation());
		inst.setName(instrumentDto.getName());
		inst.setReservable(instrumentDto.getReservable());
		inst.setStatus(instrumentDto.getStatus());
		inst.setUsername(instrumentDto.getUsername());
		inst.setRatioComment(instrumentDto.getRatioComment());
		inst.setEmailNotification(instrumentDto.getEmailNotification());
		if(instrumentDto.getStepIncrement() != 0) {			
			inst.setStepIncrement(instrumentDto.getStepIncrement());
		}
		inst.setStartTimepoint(instrumentDto.getStartTimepoint());
		inst.setMaxDays(instrumentDto.getMaxDays());
		inst.setHighlightComment(instrumentDto.getHighlightComment());
        
		StringBuffer bodyResponse = new StringBuffer("{}");
		instrumentService.save(inst);
		bodyResponse.append("}");
		return ResponseEntity.status(HttpStatus.OK).header("Content-Type", "application/json")
				.body(bodyResponse.toString());
	}
	
    @DeleteMapping("/delete/{instrumentId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteInstrument(@PathVariable(value = "instrumentId") String instrumentId) {
        logger.info("Deleting instrument : " + instrumentId);

        Optional<Instrument> instrument = instrumentService.findById(instrumentId);
        if (instrument == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .header("Content-Type", "application/json")
                    .body("{\"errorMessage\":\"Instrument cannot be removed.\"}");

        }
        Instrument inst = instrument.get();
		StringBuffer bodyResponse = new StringBuffer("{}");
		instrumentService.delete(inst);
		bodyResponse.append("}");
		return ResponseEntity.status(HttpStatus.OK).header("Content-Type", "application/json")
				.body(bodyResponse.toString());
    }
    
    
    @GetMapping("/restricted_instruments/{instrId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> getRestrictedInstrumentsForInstrument(@PathVariable(value = "instrId") String instrId) {
        logger.info("Getting restricted instrument ids for instrument : " + instrId);
        List<Integer> restrictedInstIds = instrumentService.findRestrictedInstrumentsForInstrument(instrId);
        if (restrictedInstIds == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .header("Content-Type", "application/json")
                    .body("{\"errorMessage\":\"Instrument id must be an integer.\"}");
        }
        StringBuffer bodyResponse = new StringBuffer();
        try {
            bodyResponse.append(JsonUtils.mapToJson(restrictedInstIds));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK)
                .header("Content-Type", "application/json")
                .body(bodyResponse.toString());
    }
    
    
}
