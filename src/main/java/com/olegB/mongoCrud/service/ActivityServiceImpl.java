package com.olegB.mongoCrud.service;


import com.olegB.mongoCrud.exeption.ResourceNotFoundException;
import com.olegB.mongoCrud.model.Activity;
import com.olegB.mongoCrud.model.History;
import com.olegB.mongoCrud.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService{


    private final ActivityRepository activityRepository;

    public ActivityServiceImpl(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public List<Activity> findAll() {
        return activityRepository.findAll();
    }

    @Override
    public Activity findById(Long id) throws ResourceNotFoundException {
        return activityRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Cant found activity by id - " + id));
    }

    @Override
    public Activity save(Activity activity) {
        return activityRepository.save(activity);
    }

    @Override
    public void delete(Activity activity) {
        activityRepository.delete(activity);
    }

    @Override
    public List<Activity> findByTitle(String title) {
        return activityRepository.findByTitle(title);
    }


}
