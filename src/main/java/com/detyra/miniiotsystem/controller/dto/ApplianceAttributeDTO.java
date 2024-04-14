package com.detyra.miniiotsystem.controller.dto;

import com.detyra.miniiotsystem.entity.enums.DeviceAttribute;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplianceAttributeDTO {
    @NotNull(message = "Please enter an attribute name for your appliance")
    private DeviceAttribute attribute;
    @NotNull(message = "Please provide a min value")
    @PositiveOrZero(message = "Please provide a zero or positive value for the min field")
    private Double min;
    @NotNull(message = "Please provide a max value")
    @Positive(message = "Please provide a positive value for the max field")
    private Double max;
}
