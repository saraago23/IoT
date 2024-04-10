package com.detyra.miniiotsystem.service;

import com.detyra.miniiotsystem.entity.DataPoint;
import com.detyra.miniiotsystem.entity.enums.DeviceAttribute;
import com.detyra.miniiotsystem.entity.enums.DeviceType;

import java.time.Instant;

public class DeviceDataReaderService {

    public DeviceDataReaderService(CoreSerivce coreSerivce) {
        this.readData(DeviceType.FRIDGE);
        this.readData(DeviceType.TV);
        this.readData(DeviceType.STOVE);

        this.readDataModeOnOff(DeviceType.FRIDGE);
    }

    // simulojme leximin e te dhenave nga pajsijsa elektroshtepiake
    public void readData(DeviceType type, DeviceAttribute attr) throws InterruptedException {

        for (int i = 0; i < 1000; i++) {
            // generate random number;
            double value = Math.random();
            Instant now = Instant.now();

            DataPoint dt = new DataPoint(type, attr, value, now);

            coreSerivce.handleDataPoint(dt);
            Thread.sleep(500);
        }

    }

    public void readDataModeOnOff(DeviceType type, DeviceAttribute attr) throws InterruptedException {

        for (int i = 0; i < 1000; i++) {
            // generate random number;
            double value = Math.random();
            Instant now = Instant.now();

            DataPoint dt = new DataPoint(type,attr, 0-1, now);

            coreSerivce.handleDataPoint(dt);
            Thread.sleep(500);
        }

    }
}
