package com.detyra.miniiotsystem.service;

import com.detyra.miniiotsystem.controller.dto.ApplianceDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface ApplianceService {

    ApplianceDTO addAppliance(@Valid ApplianceDTO appliance);

    List<ApplianceDTO> getAppliances();

    ApplianceDTO findApplianceByType(String type);

    void deleteAppliance(String deviceType);


}
