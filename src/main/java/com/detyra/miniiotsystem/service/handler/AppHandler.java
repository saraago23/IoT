package com.detyra.miniiotsystem.service.handler;

import com.detyra.miniiotsystem.entity.DataPoint;
import com.detyra.miniiotsystem.entity.enums.DeviceAttribute;
import com.detyra.miniiotsystem.entity.enums.DeviceType;
import com.detyra.miniiotsystem.service.CoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@Service
@Slf4j
public class AppHandler implements WebSocketHandler {
    public static WebSocketSession SESSION;
    @Autowired
    private CoreService coreService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("Connection established on session: {}", session.getId());
        SESSION = session;
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {

        String appId = (String) message.getPayload();
        session.sendMessage(new TextMessage("Started processing: " + appId));
        Thread.sleep(1000);
        session.sendMessage(new TextMessage("Completed processing: " + appId));
    }

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
        double value = Math.random() * 1000;
        Instant now = Instant.now();

        DataPoint dt = new DataPoint(type, attr, value, now);

        coreService.handleDataPoint(dt);

    }

    public void readDataModeOnOff(DeviceType type, DeviceAttribute attr) {

        // generate random number;
        double value = Math.round(Math.random());
        Instant now = Instant.now();

        DataPoint dt = new DataPoint(type, attr, value, now);

        coreService.handleDataPoint(dt);

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.info("Exception occured: {} on session: {}", exception.getMessage(), session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        log.info("Connection closed on session: {} with status: {}", session.getId(), closeStatus.getCode());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}