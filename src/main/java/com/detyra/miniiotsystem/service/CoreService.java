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

import java.util.ArrayList;
import java.util.List;

@Service
public class CoreService {
    @Autowired
    private ApplianceRepository applianceRepository;
    @Autowired
    private DataPointRepository dataPointRepository;

    public void handleDataPoint(DataPoint dp) {

        // 1. Find the device in the DB (by type & attribute)

        // 2. write data point to DB

        // 3. sendSignal

        // 4. Check if signal value is within appliance attribute Min & Max, if yes sendAlert

    }

    private void storeInDb(DataPoint dp) {
    }

    @SendTo("/topic/messages")
    private void sendSignal(SignalData d) {

    }

    @SendTo("/topic/messages")
    private void sendAlert(SignalData d) {

    }
}
