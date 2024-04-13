package com.detyra.miniiotsystem.controller;

import com.detyra.miniiotsystem.controller.dto.ApplianceDTO;
import com.detyra.miniiotsystem.service.ApplianceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appliances")
@RequiredArgsConstructor
public class ApplianceController {

    private final ApplianceService applianceService;

    @PostMapping
    public ResponseEntity<ApplianceDTO> addAppliance(@RequestBody ApplianceDTO appliance) {
        return ResponseEntity.ok(applianceService.addAppliance(appliance));
    }

    @GetMapping("/{type}")
    public ResponseEntity<ApplianceDTO> findApplianceByType(@PathVariable String type) {
        return ResponseEntity.ok(applianceService.findApplianceByType(type));
    }

    @GetMapping
    public ResponseEntity<List<ApplianceDTO>> getAllAppliances() {
        return ResponseEntity.ok(applianceService.getAppliances());
    }

    @DeleteMapping("/{deviceType}")
    public ResponseEntity<Void> deleteAppliance(@PathVariable String deviceType) {
        applianceService.deleteAppliance(deviceType);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
