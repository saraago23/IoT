package com.detyra.miniiotsystem.service.impl;

import com.detyra.miniiotsystem.entity.Appliance;
import com.detyra.miniiotsystem.entity.ApplianceAttribute;
import com.detyra.miniiotsystem.entity.DataPoint;
import com.detyra.miniiotsystem.entity.SignalData;
import com.detyra.miniiotsystem.exception.GeneralException;
import com.detyra.miniiotsystem.repository.ApplianceRepository;
import com.detyra.miniiotsystem.repository.DataPointRepository;
import com.detyra.miniiotsystem.service.CoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CoreServiceImpl implements CoreService {
    private final ApplianceRepository applianceRepository;
    private final DataPointRepository dataPointRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public CoreServiceImpl(SimpMessagingTemplate messagingTemplate, DataPointRepository dataPointRepository, ApplianceRepository applianceRepository) {
        this.messagingTemplate = messagingTemplate;
        this.applianceRepository = applianceRepository;
        this.dataPointRepository = dataPointRepository;
    }

    public void sendSignal(SignalData d) {
        log.info(d.getMessage());
        messagingTemplate.convertAndSend("/topic/signals", d);

    }

    public void sendAlert(SignalData d) {
        log.error(d.getMessage() + " is critical! ");
        messagingTemplate.convertAndSend("/topic/alerts", d);
    }
    public void storeInDb(DataPoint dp) {
        dataPointRepository.save(dp);
    }
    public void handleDataPoint(DataPoint dp) {

        Appliance appliance = applianceRepository.findByType(dp.getType()).orElseThrow(() -> new GeneralException("No appliance found!"));

        ApplianceAttribute applianceAttribute = appliance.getAttributes().stream().filter(attribute -> attribute.getAttribute().equals(dp.getAttribute())).findFirst().orElseThrow(() -> new GeneralException("This device does not have this attribute "));

        storeInDb(dp);

        SignalData signalData = new SignalData();
        signalData.setValue(dp.getValue());
        signalData.setMessage("Value of: " + dp.getType() + " " + applianceAttribute.getAttribute().name() + " : " + dp.getValue());

        if (signalData.getValue() > applianceAttribute.getMax() || signalData.getValue() < applianceAttribute.getMin()) {
            signalData.setCritical(true);
            sendAlert(signalData);
        } else {
            signalData.setCritical(false);
            sendSignal(signalData);
        }
    }

}