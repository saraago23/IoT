package com.detyra.miniiotsystem.repository;

import com.detyra.miniiotsystem.entity.Appliance;
import com.detyra.miniiotsystem.entity.enums.DeviceAttribute;
import com.detyra.miniiotsystem.entity.enums.DeviceType;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ApplianceRepository extends MongoRepository<Appliance,String> {

     Optional<Appliance> findByType(DeviceType type);
     Optional<Appliance> findApplianceByType(DeviceType type, DeviceAttribute attribute);
}
