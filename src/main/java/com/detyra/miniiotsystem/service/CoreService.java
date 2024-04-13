package com.detyra.miniiotsystem.service;
import com.detyra.miniiotsystem.entity.DataPoint;
<<<<<<< HEAD
import com.detyra.miniiotsystem.entity.enums.DeviceAttribute;
import com.detyra.miniiotsystem.entity.enums.DeviceType;


public interface CoreService {

    public void handleDataPoint(DataPoint dp);

    public void readData(DeviceType type, DeviceAttribute attr);
=======
import com.detyra.miniiotsystem.entity.SignalData;
import com.detyra.miniiotsystem.exception.GeneralException;
import com.detyra.miniiotsystem.repository.ApplianceRepository;
import com.detyra.miniiotsystem.repository.DataPointRepository;
import com.detyra.miniiotsystem.service.handler.AppHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;


@Service
@RequiredArgsConstructor
@Slf4j
public class CoreService {
    private final ApplianceRepository applianceRepository;
    private final DataPointRepository dataPointRepository;
>>>>>>> 5b62396769bd52ea9197c790f4c8cff77b00b2d2

    public void readDataModeOnOff(DeviceType type, DeviceAttribute attr);

<<<<<<< HEAD
}
=======
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
        //  messagingTemplate.convertAndSend("/topic/messages", d);
        if (AppHandler.SESSION != null) {
            try {
                if (d.getCritical()) {
                    AppHandler.SESSION.sendMessage(new TextMessage("Message: " + d.getMessage() + " value: " + d.getValue() + " is critical! "));
                }else{
                    AppHandler.SESSION.sendMessage(new TextMessage("Message: " + d.getMessage() + " value: " + d.getValue()));
                }
            } catch (IOException e) {
                log.error("Something went wrong: {} ", e.getMessage());
            }
        }
    }

   /* @SendTo("/topic/messages")
    private void sendAlert(SignalData d) {

    }*/

}
>>>>>>> 5b62396769bd52ea9197c790f4c8cff77b00b2d2
