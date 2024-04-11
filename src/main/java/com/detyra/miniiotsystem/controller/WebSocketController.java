package com.detyra.miniiotsystem.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/hello")
    @SendTo("/topic/hello")
    public String greeting(String message) {
        return "Hello, " + message + "!";
    }
}