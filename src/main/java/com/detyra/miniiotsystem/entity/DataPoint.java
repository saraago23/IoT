package com.detyra.miniiotsystem.entity;

import com.detyra.miniiotsystem.entity.enums.DeviceAttribute;
import com.detyra.miniiotsystem.entity.enums.DeviceType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class DataPoint {
    @Id
    private String id;
    private DeviceType type;
    private DeviceAttribute attribute;
    private Double value;
    private Instant now;

    public DataPoint(DeviceType type, DeviceAttribute attribute, Double value, Instant now) {
        this.type = type;
        this.attribute = attribute;
        this.value = value;
        this.now = now;
    }
}
