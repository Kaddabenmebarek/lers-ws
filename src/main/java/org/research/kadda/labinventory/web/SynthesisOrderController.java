package org.research.kadda.labinventory.web;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dozer.DozerBeanMapper;
import org.research.kadda.labinventory.data.JsonUtils;
import org.research.kadda.labinventory.data.SynthesisLibraryOrderDto;
import org.research.kadda.labinventory.entity.SynthesisLibraryOrder;
import org.research.kadda.labinventory.service.SynthesisOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping(path = "/synthesisorder")
public class SynthesisOrderController {
	private static Logger logger = LogManager.getLogger(SynthesisOrderController.class);
	private SynthesisOrderService synthesisOrderService;

	public SynthesisOrderController(SynthesisOrderService cSOrderService) {
		this.synthesisOrderService = cSOrderService;
	}

	protected SynthesisOrderController() {
	}

	@GetMapping("/all")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> getAllSynthesisOrders() {
		logger.info("Getting all Chemistry Service Orders");

		boolean firstOrder = true;
		Iterable<SynthesisLibraryOrder> orders = synthesisOrderService.findAll();
		StringBuffer bodyResponse = new StringBuffer("{");
		bodyResponse.append("\"synthesisorders\":[");
		try {
			for (SynthesisLibraryOrder order : orders) {
				if (firstOrder) {
					firstOrder = false;
				} else {
					bodyResponse.append(",");
				}

				bodyResponse.append(JsonUtils.mapToJson(order));
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		bodyResponse.append("]}");
		return ResponseEntity.status(HttpStatus.OK).header("Content-Type", "application/json")
				.body(bodyResponse.toString());
	}
	
	@GetMapping("/all/inrange")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> getAllSynthesisOrdersInRange(
			@RequestBody String jsonPayload) {
		logger.info("Getting all Chemistry Service Orders");
		Iterable<SynthesisLibraryOrder> orders = null;
		StringBuffer bodyResponse = new StringBuffer("{");

		try {
			String fromDate = StringUtils.replace(JsonUtils.getJsonNode(jsonPayload, "startDate").toString(), "\"", "");
			String toDate = StringUtils.replace(JsonUtils.getJsonNode(jsonPayload, "endDate").toString(), "\"", "");

			boolean firstOrder = true;
			
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date from = simpleDateFormat.parse(fromDate);
			Date to = simpleDateFormat.parse(toDate);
			String fromDateTime =  simpleDateFormat.format(from);
			String toDateTime =  simpleDateFormat.format(to);
			
			orders = synthesisOrderService.findAllInRange(fromDateTime,toDateTime);
			

			bodyResponse.append("\"synthesisorders\":[");
			for (SynthesisLibraryOrder order : orders) {
				if (firstOrder) {
					firstOrder = false;
				} else {
					bodyResponse.append(",");
				}
				bodyResponse.append(JsonUtils.mapToJson(order));
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
	
	@GetMapping("/add")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<String> addSynthesisLibraryOrder(@RequestBody SynthesisLibraryOrderDto synthesisOrderDto) {
		logger.info("Adding Order");
		StringBuffer bodyResponse = new StringBuffer("{");
		SynthesisLibraryOrder synthesisOrder = new DozerBeanMapper().map(synthesisOrderDto, SynthesisLibraryOrder.class);
		synthesisOrderService.save(synthesisOrder);
		bodyResponse.append("}");
		return ResponseEntity.status(HttpStatus.CREATED).header("Content-Type", "application/json")
				.body(bodyResponse.toString());
	}
	
	@GetMapping("/update")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<String> updateSynthesisLibraryOrder(@RequestBody SynthesisLibraryOrderDto synthesisOrderDto) {
		logger.info("Update order...");

		Optional<SynthesisLibraryOrder> order = synthesisOrderService.findById(String.valueOf(synthesisOrderDto.getId()));
		SynthesisLibraryOrder synthesisLibraryOrder = order.get();
		synthesisLibraryOrder.setTitle(synthesisOrderDto.getTitle());
		synthesisLibraryOrder.setRequester(synthesisOrderDto.getRequester());
		synthesisLibraryOrder.setRequestTime(synthesisOrderDto.getRequestTime());
		if(synthesisOrderDto.getProject() !=null) synthesisLibraryOrder.setProject(synthesisOrderDto.getProject());
		if(synthesisOrderDto.getLink() !=null) synthesisLibraryOrder.setLink(synthesisOrderDto.getLink());
		if(synthesisOrderDto.getLibraryoutcome() !=null) synthesisLibraryOrder.setLibraryoutcome(synthesisOrderDto.getLibraryoutcome());
		synthesisLibraryOrder.setFromTime(synthesisOrderDto.getFromTime());
		synthesisLibraryOrder.setToTime(synthesisOrderDto.getToTime());
		synthesisLibraryOrder.setUsername(synthesisOrderDto.getUsername());
		synthesisLibraryOrder.setDone(synthesisOrderDto.getDone());
		synthesisLibraryOrder.setDepartmentName(synthesisOrderDto.getDepartmentName());
		if(synthesisOrderDto.getCompound() !=null) synthesisLibraryOrder.setCompound(synthesisOrderDto.getCompound());
		if(synthesisOrderDto.getQuantity() !=null) synthesisLibraryOrder.setQuantity(synthesisOrderDto.getQuantity());
		if(synthesisOrderDto.getUnit() !=null) synthesisLibraryOrder.setUnit(synthesisOrderDto.getUnit());
		if(synthesisOrderDto.getRemarks() !=null) synthesisLibraryOrder.setRemarks(synthesisOrderDto.getRemarks());
		if(synthesisLibraryOrder.getDoneTime() == null && synthesisOrderDto.getDoneTime() !=null) synthesisLibraryOrder.setDoneTime(synthesisOrderDto.getDoneTime());
		synthesisLibraryOrder.setUpdateTime(new Date());
			
		synthesisOrderService.save(synthesisLibraryOrder);

		logger.info("Update done.");

		StringBuffer bodyResponse = new StringBuffer("{\"id\":" + synthesisLibraryOrder.getId() + "}");
		return ResponseEntity.status(HttpStatus.CREATED).header("Content-Type", "application/json")
				.body(bodyResponse.toString());
	}

	@GetMapping("/delete/{synthesisOrderId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> deleteSynthesisOrder(@PathVariable(value = "synthesisOrderId") String synthesisOrderId) {
		logger.info("Deleting order : " + synthesisOrderId);
		Optional<SynthesisLibraryOrder> synthesisOrderToRemove = synthesisOrderService.findById(synthesisOrderId);
		if (synthesisOrderToRemove == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Content-Type", "application/json")
					.body("{\"errorMessage\":\"Order cannot be removed.\"}");

		}
		SynthesisLibraryOrder order = synthesisOrderToRemove.get();
		StringBuffer bodyResponse = new StringBuffer("{}");
		synthesisOrderService.delete(order);
		bodyResponse.append("}");
		return ResponseEntity.status(HttpStatus.OK).header("Content-Type", "application/json")
				.body(bodyResponse.toString());
	}

	@GetMapping("/{synthesisOrderId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> getCSOrder(@PathVariable(value = "synthesisOrderId") String synthesisOrderId) {
		logger.info("Getting order : " + synthesisOrderId);

		Optional<SynthesisLibraryOrder> order = synthesisOrderService.findById(synthesisOrderId);
		if (order == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Content-Type", "application/json")
					.body("{\"errorMessage\":\"Order id must be an integer.\"}");

		}
		SynthesisLibraryOrder ord = order.get();
		StringBuffer bodyResponse = new StringBuffer();
		try {
			bodyResponse.append(JsonUtils.mapToJson(ord));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.OK).header("Content-Type", "application/json")
				.body(bodyResponse.toString());
	}
	
}
