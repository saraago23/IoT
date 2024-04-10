package com.detyra.miniiotsystem.entity;

import com.detyra.miniiotsystem.entity.enums.DeviceType;
import com.detyra.miniiotsystem.entity.enums.Room;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Appliance {
    @Id
    private String id;
    private DeviceType type;
    private List<ApplianceAttribute> attributes;
    private Boolean powerOn;
    private Room room;
}

