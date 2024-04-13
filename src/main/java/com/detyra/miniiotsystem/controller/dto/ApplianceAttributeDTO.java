package com.detyra.miniiotsystem.controller.dto;

import com.detyra.miniiotsystem.entity.enums.DeviceAttribute;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplianceAttributeDTO {
    private DeviceAttribute attribute;
    private Double min;
    private Double max;
}
