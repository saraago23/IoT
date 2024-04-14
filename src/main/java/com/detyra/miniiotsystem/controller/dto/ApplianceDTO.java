package com.detyra.miniiotsystem.controller.dto;

import com.detyra.miniiotsystem.entity.enums.DeviceType;
import com.detyra.miniiotsystem.entity.enums.Room;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "Please enter a type for your appliance")
    private DeviceType type;
    @NotNull(message = "Please provide the attributes for your appliance")
    @Valid
    private List<ApplianceAttributeDTO> attributes;
    @NotNull(message = "Please provide the status of your appliance")
    private Boolean powerOn;
    @NotNull(message = "Please provide the room of your appliance")
    private Room room;
}
