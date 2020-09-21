package com.olegB.mongoCrud.repository;



import com.olegB.mongoCrud.model.Activity;
import com.olegB.mongoCrud.model.History;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends MongoRepository<Activity, Long> {

    @Query("{'title': ?0}")
    List<Activity> findByTitle(String title);

}
