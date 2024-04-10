package com.detyra.miniiotsystem.service;

import com.detyra.miniiotsystem.entity.DataPoint;
import com.detyra.miniiotsystem.entity.enums.DeviceAttribute;
import com.detyra.miniiotsystem.entity.enums.DeviceType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Slf4j
public class DeviceDataReaderService {
    @Autowired
    private CoreService coreService;

    public DeviceDataReaderService() {
        this.readData(DeviceType.FRIDGE, DeviceAttribute.HUMIDITY);
        this.readData(DeviceType.TV, DeviceAttribute.VOLUME);
        this.readData(DeviceType.STOVE, DeviceAttribute.TEMPERATURE);
        this.readDataModeOnOff(DeviceType.FRIDGE, DeviceAttribute.HUMIDITY);
    }

    // simulojme leximin e te dhenave nga pajsijsa elektroshtepiake
    public void readData(DeviceType type, DeviceAttribute attr) {

        for (int i = 0; i < 1000; i++) {
            // generate random number;
            double value = Math.random();
            Instant now = Instant.now();

            DataPoint dt = new DataPoint(type, attr, value, now);

            coreService.handleDataPoint(dt);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                log.error("Something went wrong");
            }
        }

    }

    public void readDataModeOnOff(DeviceType type, DeviceAttribute attr){

        for (int i = 0; i < 1000; i++) {
            // generate random number;
            double value = Math.random();
            Instant now = Instant.now();

            DataPoint dt = new DataPoint(type, attr, 0.0, now);

            coreService.handleDataPoint(dt);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                log.error("Something went wrong");
            }
        }

    }
}
