package com.olegB.mongoCrud.service;
import com.olegB.mongoCrud.exeption.ResourceNotFoundException;
import com.olegB.mongoCrud.model.Activity;
import com.olegB.mongoCrud.model.History;

import javax.management.Query;
import java.util.List;

public interface ActivityService {
    List<Activity> findAll();
    Activity findById(Long id) throws ResourceNotFoundException;
    Activity save(Activity activity);
    void delete(Activity activity);
    List<Activity> findByTitle(String title);

}
