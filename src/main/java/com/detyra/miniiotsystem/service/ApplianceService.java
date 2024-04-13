package com.detyra.miniiotsystem.service;

import com.detyra.miniiotsystem.controller.dto.ApplianceDTO;

import java.util.List;

public interface ApplianceService {

    ApplianceDTO addAppliance(ApplianceDTO appliance);

    List<ApplianceDTO> getAppliances();

    ApplianceDTO findApplianceByType(String type);

    void deleteAppliance(String deviceType);


}
