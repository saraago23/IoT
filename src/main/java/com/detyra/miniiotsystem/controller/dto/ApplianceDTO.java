package com.detyra.miniiotsystem.controller.dto;

import com.detyra.miniiotsystem.entity.enums.DeviceType;
import com.detyra.miniiotsystem.entity.enums.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplianceDTO {
    private DeviceType type;
    private List<ApplianceAttributeDTO> attributes;
    private Boolean powerOn;
    private Room room;
}
