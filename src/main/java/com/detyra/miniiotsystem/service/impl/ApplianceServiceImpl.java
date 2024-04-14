package com.detyra.miniiotsystem.service.impl;


import com.detyra.miniiotsystem.entity.Appliance;
import com.detyra.miniiotsystem.entity.enums.DeviceType;
import com.detyra.miniiotsystem.controller.dto.ApplianceDTO;
import com.detyra.miniiotsystem.exception.GeneralException;
import com.detyra.miniiotsystem.repository.ApplianceRepository;
import com.detyra.miniiotsystem.service.ApplianceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

import static com.detyra.miniiotsystem.mapper.ApplianceMapper.*;
import static com.detyra.miniiotsystem.mapper.ApplianceAttributeMapper.*;

@Service
@RequiredArgsConstructor
@Validated
public class ApplianceServiceImpl implements ApplianceService {

    private final ApplianceRepository applianceRepository;

    public ApplianceDTO addAppliance(@Valid @RequestBody ApplianceDTO appliance) {

        if (applianceRepository.findByType(appliance.getType()).isPresent()) {
            throw new GeneralException("This device type already exists on the db");
        }
        Appliance appliance1 = Appliance.builder()
                .attributes(appliance.getAttributes().stream().map(APPLIANCE_ATTRIBUTE_MAPPER::toEntity).toList())
                .powerOn(appliance.getPowerOn())
                .room(appliance.getRoom())
                .type(appliance.getType())
                .build();
        return APPLIANCE_MAPPER.toDto(applianceRepository.save(appliance1));
    }

    public List<ApplianceDTO> getAppliances() {
        return applianceRepository.findAll().stream().map(APPLIANCE_MAPPER::toDto).collect(Collectors.toList());
    }

    public ApplianceDTO findApplianceByType(String type) {
        return APPLIANCE_MAPPER.toDto(applianceRepository.findByType(DeviceType.valueOf(type))
                .orElseThrow(() -> new GeneralException("No appliance of type: " + type + " was found on the db")));
    }


    public void deleteAppliance(String deviceType) {

        Appliance applianceToBeDeleted = applianceRepository.findByType(DeviceType.valueOf(deviceType)).
                orElseThrow(() -> new GeneralException("Device type: " + deviceType + " was not found"));

        applianceRepository.delete(applianceToBeDeleted);
    }

}
