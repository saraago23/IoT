package com.detyra.miniiotsystem.service.impl;

import com.detyra.miniiotsystem.entity.Appliance;
import com.detyra.miniiotsystem.entity.DataPoint;
import com.detyra.miniiotsystem.entity.enums.DeviceAttribute;
import com.detyra.miniiotsystem.entity.enums.DeviceType;
import com.detyra.miniiotsystem.exception.GeneralException;
import com.detyra.miniiotsystem.repository.ApplianceRepository;
import com.detyra.miniiotsystem.service.CoreService;
import com.detyra.miniiotsystem.service.DeviceDataReaderService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Slf4j
public class DeviceDataReaderServiceImpl implements DeviceDataReaderService {
    private final CoreService coreService;
    private final ApplianceRepository applianceRepository;

    public DeviceDataReaderServiceImpl(CoreService coreService, ApplianceRepository applianceRepository) {
        this.coreService = coreService;
        this.applianceRepository = applianceRepository;
    }

    @PostConstruct
    public void initializeData() {
        this.readData(DeviceType.FRIDGE, DeviceAttribute.HUMIDITY);
        this.readData(DeviceType.TOASTER, DeviceAttribute.POWER);
        this.readData(DeviceType.TV, DeviceAttribute.VOLUME);
        this.readData(DeviceType.WASHING_MACHINE, DeviceAttribute.FREQUENCY);
        this.readData(DeviceType.BLENDER, DeviceAttribute.FREQUENCY);
        this.readData(DeviceType.MICROWAVE, DeviceAttribute.POWER);
        this.readData(DeviceType.AIR_CONDITIONER, DeviceAttribute.TEMPERATURE);
        this.readDataModeOnOff(DeviceType.FRIDGE, DeviceAttribute.HUMIDITY);
        log.info("You got some data");
    }

    public void readData(DeviceType type, DeviceAttribute attr) {

        Appliance appliance = applianceRepository.findByType(type)
                .orElseThrow(() -> new GeneralException("No appliance found!"));

        if (appliance.getPowerOn().equals(true)) {

            for (int i = 0; i < 100; i++) {

                double value = Math.random() * 1000;
                Instant now = Instant.now();

                DataPoint dp = new DataPoint(type, attr, value, now);

                coreService.handleDataPoint(dp);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    log.error("Something unexpected happened");
                }

            }
        }
    }

    public void readDataModeOnOff(DeviceType type, DeviceAttribute attr) {
        Appliance appliance = applianceRepository.findByType(type).orElseThrow(() -> new GeneralException("No appliance found!"));

        for (int i = 0; i <= 100; i++) {

            double value = Math.round(Math.random());
            Instant now = Instant.now();

            appliance.setPowerOn(value == 1);
            applianceRepository.save(appliance);

            DataPoint dt = new DataPoint(type, attr, value, now);

            coreService.handleDataPoint(dt);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                log.error("Something unexpected happened");
            }
        }
    }
}
