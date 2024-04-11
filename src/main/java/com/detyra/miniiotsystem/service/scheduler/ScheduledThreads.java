package com.detyra.miniiotsystem.service.scheduler;

import com.detyra.miniiotsystem.entity.DataPoint;
import com.detyra.miniiotsystem.entity.enums.DeviceAttribute;
import com.detyra.miniiotsystem.entity.enums.DeviceType;
import com.detyra.miniiotsystem.service.CoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@Slf4j
public class ScheduledThreads {
    @Autowired
    private CoreService coreService;

    @Scheduled(fixedRate = 10000)
    public void fixedRateSch() {
        this.readData(DeviceType.FRIDGE, DeviceAttribute.HUMIDITY);
        //this.readData(DeviceType.TV, DeviceAttribute.VOLUME);
        //this.readData(DeviceType.STOVE, DeviceAttribute.TEMPERATURE);
        this.readDataModeOnOff(DeviceType.FRIDGE, DeviceAttribute.HUMIDITY);
        log.info("You got some data");
    }

    public void readData(DeviceType type, DeviceAttribute attr) {

        // generate random number;
        double value = Math.random()*1000;
        Instant now = Instant.now();

        DataPoint dt = new DataPoint(type, attr, value, now);

        coreService.handleDataPoint(dt);

    }

    public void readDataModeOnOff(DeviceType type, DeviceAttribute attr) {

        // generate random number;
        double value = Math.random();
        Instant now = Instant.now();

        DataPoint dt = new DataPoint(type, attr, 0.0, now);

        coreService.handleDataPoint(dt);

    }

}

