package com.detyra.miniiotsystem.entity;

import com.detyra.miniiotsystem.entity.enums.DeviceAttribute;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplianceAttribute {

    private DeviceAttribute attribute;
    private Double min;
    private Double max;
}