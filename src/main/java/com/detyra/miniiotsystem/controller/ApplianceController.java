package com.detyra.miniiotsystem.controller;

import com.detyra.miniiotsystem.controller.dto.ApplianceDTO;
import com.detyra.miniiotsystem.service.ApplianceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appliances")
@RequiredArgsConstructor
public class ApplianceController {

    private final ApplianceService applianceService;

    @PostMapping
    public ApplianceDTO addAppliance(@RequestBody ApplianceDTO appliance) {
        return applianceService.addAppliance(appliance);
    }

    @GetMapping("/{type}")
    public ApplianceDTO findApplianceByType(@PathVariable String type) {
        return applianceService.findApplianceByType(type);
    }

    @GetMapping("/all")
    public List<ApplianceDTO> getAllAppliances() {
        return applianceService.getAppliances();
    }

}
