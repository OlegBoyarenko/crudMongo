package com.olegB.mongoCrud.controller;

import com.olegB.mongoCrud.exeption.ActivityIsEqualsException;
import com.olegB.mongoCrud.exeption.ResourceNotFoundException;
import com.olegB.mongoCrud.history.Type;
import com.olegB.mongoCrud.model.Activity;
import com.olegB.mongoCrud.model.History;
import com.olegB.mongoCrud.service.ActivityService;
import com.olegB.mongoCrud.service.ActivityServiceImpl;
import com.olegB.mongoCrud.service.SequenceIncrementationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("")
public class ActivityController {
    @Autowired
    private ActivityServiceImpl activityService;

    @Autowired
    private SequenceIncrementationService sequenceIncrementationService;

    @GetMapping("/activity")
    @ApiOperation(value = "Get all activity", response = List.class)
    public List<Activity> getAllActivity() {
        return activityService.findAll();
    }

    @GetMapping("/activity/{id}")
    @ApiOperation(value = "Get activity by id", response = ResponseEntity.class)
    public ResponseEntity<Activity> getActivityById(@PathVariable(value = "id") Long activityId)
            throws ResourceNotFoundException {
        Activity responseActivity = activityService.findById(activityId);
        return ResponseEntity.ok().body(responseActivity);
    }

    @PostMapping("/activity")
    @ApiOperation(value = "Add activity to database", response = ResponseEntity.class)
    public ResponseEntity<Activity> createActivity(@Valid @RequestBody Activity activity) {
        activity.setId(sequenceIncrementationService.generateSequence(Activity.SEQUENCE_NAME));

        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-HH:mm");
        df.setTimeZone(tz);
        String nowAsISO = df.format(new Date());
        activity.setHistory(new History(nowAsISO, Type.COMPOSE));
        
        Activity responseActivity = activityService.save(activity);
        return ResponseEntity.ok().body(responseActivity);
    }

    @PutMapping("/activity/{id}")
    @ApiOperation(value = "Update activity field", response = ResponseEntity.class)
    public ResponseEntity<Activity> updateActivity(@PathVariable(value = "id") Long activityId,
                                                       @Valid @RequestBody Activity activityDetails)
            throws ResourceNotFoundException, ActivityIsEqualsException, InvocationTargetException, IllegalAccessException {
        Activity activity = activityService.findById(activityId);
        activity.setupChanges(activityDetails);
        final Activity updatedActivity = activityService.save(activity);
        return ResponseEntity.ok(updatedActivity);
    }

    @DeleteMapping("/activity/delete/{id}")
    @ApiOperation(value = "Delete activity by id", response = HashMap.class)
    public Map<String, Boolean> deleteActivity(@PathVariable(value = "id") Long activityId)
            throws ResourceNotFoundException {
        Activity activity = activityService.findById(activityId);

        activityService.delete(activity);
        Map <String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @GetMapping("/activity/title")
    @ApiOperation(value = "Get activity by title field", response = List.class)
    public List<Activity> getByTitle(@RequestParam(name = "title") String data)
            throws ResourceNotFoundException {
        return activityService.findByTitle(data);
    }


}
