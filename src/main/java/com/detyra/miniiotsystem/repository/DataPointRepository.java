package com.detyra.miniiotsystem.repository;

import com.detyra.miniiotsystem.entity.DataPoint;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DataPointRepository extends MongoRepository<DataPoint,String> {

}
