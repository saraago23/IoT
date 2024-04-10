package com.detyra.miniiotsystem.service;

import com.detyra.miniiotsystem.entity.Appliance;
import com.detyra.miniiotsystem.entity.ApplianceAttribute;
import com.detyra.miniiotsystem.entity.DataPoint;
import com.detyra.miniiotsystem.entity.SignalData;
import com.detyra.miniiotsystem.exception.GeneralException;
import com.detyra.miniiotsystem.repository.ApplianceRepository;
import com.detyra.miniiotsystem.repository.DataPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;


@Service
public class CoreService {
    @Autowired
    private ApplianceRepository applianceRepository;
    @Autowired
    private DataPointRepository dataPointRepository;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void handleDataPoint(DataPoint dp) {

        // 1. Find the device in the DB (by type & attribute)
        Appliance appliance = applianceRepository.findByType(dp.getType()).orElseThrow(() -> new GeneralException("No appliance found!"));

        ApplianceAttribute applianceAttribute = appliance.getAttributes().stream().filter(attribute -> attribute.getAttribute().equals(dp.getAttribute())).findFirst().orElseThrow(() -> new GeneralException("This device does not have this attribute "));

        // 2. write data point to DB
        storeInDb(dp);

        // 3. sendSignal
        SignalData signalData = new SignalData();
        signalData.setValue(dp.getValue());
        signalData.setMessage("Value of: " + dp.getType());

        // 4. Check if signal value is within appliance attribute Min & Max, if yes sendAlert
        if (signalData.getValue() > applianceAttribute.getMax() || signalData.getValue() < applianceAttribute.getMin()) {
            signalData.setCritical(true);
        } else {
            signalData.setCritical(false);
        }
        sendSignal(signalData);
    }

    private void storeInDb(DataPoint dp) {
        dataPointRepository.save(dp);
    }

    @SendTo("/topic/messages")
    private void sendSignal(SignalData d) {
        messagingTemplate.convertAndSend("/topic/messages", d);
    }

  /*  @SendTo("/topic/messages")
    private void sendAlert(SignalData d) {

    }*/
}
