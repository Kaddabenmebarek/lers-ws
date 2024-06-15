package org.research.kadda.labinventory.web;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.research.kadda.labinventory.data.JsonUtils;
import org.research.kadda.labinventory.entity.Group;
import org.research.kadda.labinventory.service.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Author: Kadda
 */

@RestController
@RequestMapping(path = "/group")
public class GroupController {
    private static Logger logger = LogManager.getLogger(GroupController.class);
    private GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    protected GroupController() {
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> getAllGroups() {
        logger.info("Getting all Groups");
        boolean firstGroup = true;
        long count = groupService.count();
        Iterable<Group> groups = groupService.findAll();
        StringBuffer bodyResponse = new StringBuffer("{");
        bodyResponse.append("\"count\":\"" + count + "\",");
        bodyResponse.append("\"groups\":[");
        try {
            for (Group group : groups) {
                if (firstGroup) {
                    firstGroup = false;
                } else {
                    bodyResponse.append(",");
                }
                bodyResponse.append(JsonUtils.mapToJson(group));
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        bodyResponse.append("]}");

        return ResponseEntity.status(HttpStatus.OK).header("Content-Type", "application/json")
                .body(bodyResponse.toString());
    }

    @GetMapping("/names")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> getGroupNames() {
        logger.info("Getting Group Names");
        boolean firstGroup = true;
        List<String> groupNames = groupService.findGroupNames();
        StringBuffer bodyResponse = new StringBuffer("{");
        bodyResponse.append("\"groupNames\":[");
        for (String groupName : groupNames) {
            if (firstGroup) {
                firstGroup = false;
            } else {
                bodyResponse.append(",");
            }
            bodyResponse.append("\"" + groupName + "\"");
        }

        bodyResponse.append("]}");

        return ResponseEntity.status(HttpStatus.OK).header("Content-Type", "application/json")
                .body(bodyResponse.toString());
    }
}
