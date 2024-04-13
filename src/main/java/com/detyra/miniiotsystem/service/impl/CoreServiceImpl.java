package com.detyra.miniiotsystem.service.impl;

import com.detyra.miniiotsystem.entity.Appliance;
import com.detyra.miniiotsystem.entity.ApplianceAttribute;
import com.detyra.miniiotsystem.entity.DataPoint;
import com.detyra.miniiotsystem.entity.SignalData;
import com.detyra.miniiotsystem.entity.enums.DeviceAttribute;
import com.detyra.miniiotsystem.entity.enums.DeviceType;
import com.detyra.miniiotsystem.exception.GeneralException;
import com.detyra.miniiotsystem.repository.ApplianceRepository;
import com.detyra.miniiotsystem.repository.DataPointRepository;
import com.detyra.miniiotsystem.service.CoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;


@Service
@Slf4j
public class CoreServiceImpl implements CoreService {
    private final ApplianceRepository applianceRepository;
    private final DataPointRepository dataPointRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public CoreServiceImpl(SimpMessagingTemplate messagingTemplate, DataPointRepository dataPointRepository, ApplianceRepository applianceRepository) {
        this.messagingTemplate = messagingTemplate;
        this.applianceRepository = applianceRepository;
        this.dataPointRepository = dataPointRepository;
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

    public void sendSignal(SignalData d) {
        log.info(d.getMessage());
        messagingTemplate.convertAndSend("/topic/signals", d.getMessage());

    }

    public void sendAlert(SignalData d) {
        log.error(d.getMessage() + " is critical! ");
        messagingTemplate.convertAndSend("/topic/alerts", d.getMessage() + " is critical! ");
    }

    public void handleDataPoint(DataPoint dp) {

        // 1. Find the device in the DB (by type & attribute)
        Appliance appliance = applianceRepository.findByType(dp.getType()).orElseThrow(() -> new GeneralException("No appliance found!"));

        ApplianceAttribute applianceAttribute = appliance.getAttributes().stream().filter(attribute -> attribute.getAttribute().equals(dp.getAttribute())).findFirst().orElseThrow(() -> new GeneralException("This device does not have this attribute "));

        // 2. write data point to DB
        storeInDb(dp);

        // 3. sendSignal
        SignalData signalData = new SignalData();
        signalData.setValue(dp.getValue());
        signalData.setMessage("Value of: " + dp.getType() + " " + applianceAttribute.getAttribute().name() + " : " + dp.getValue());

        // 4. Check if signal value is within appliance attribute Min & Max, if yes sendAlert
        if (signalData.getValue() > applianceAttribute.getMax() || signalData.getValue() < applianceAttribute.getMin()) {
            signalData.setCritical(true);
            sendAlert(signalData);
        } else {
            signalData.setCritical(false);
            sendSignal(signalData);
        }
    }

    public void readData(DeviceType type, DeviceAttribute attr) {

        Appliance appliance = applianceRepository.findByType(type)
                .orElseThrow(() -> new GeneralException("No appliance found!"));

        if (appliance.getPowerOn().equals(true)) {

            for (int i = 0; i < 100; i++) {
                // generate random number;
                double value = Math.random() * 1000;
                Instant now = Instant.now();

                DataPoint dp = new DataPoint(type, attr, value, now);

                handleDataPoint(dp);
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

        // generate random number;
        double value = Math.round(Math.random());
        Instant now = Instant.now();
        if (value == 1) {
            appliance.setPowerOn(true);
            applianceRepository.save(appliance);
        } else {
            appliance.setPowerOn(false);
            applianceRepository.save(appliance);
        }
        DataPoint dt = new DataPoint(type, attr, value, now);

        handleDataPoint(dt);

    }

    private void storeInDb(DataPoint dp) {
        dataPointRepository.save(dp);
    }


}