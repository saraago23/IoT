package com.detyra.miniiotsystem.service;


import com.detyra.miniiotsystem.entity.Appliance;
import com.detyra.miniiotsystem.entity.ApplianceAttribute;
import com.detyra.miniiotsystem.entity.enums.DeviceAttribute;
import com.detyra.miniiotsystem.entity.enums.DeviceType;
import com.detyra.miniiotsystem.entity.enums.Room;
import com.detyra.miniiotsystem.repository.ApplianceRepository;
import com.detyra.miniiotsystem.service.impl.ApplianceServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static com.detyra.miniiotsystem.mapper.ApplianceMapper.*;

@ExtendWith(MockitoExtension.class)
public class ApplianceServiceTests {

    @Mock
    ApplianceRepository applianceRepository;
    @InjectMocks
    ApplianceServiceImpl toTest;

    @Test
    public void test_find_all_ok() {
        List<ApplianceAttribute> attributes = new ArrayList<>();
        ApplianceAttribute applianceAttribute = new ApplianceAttribute();
        applianceAttribute.setAttribute(DeviceAttribute.HUMIDITY);
        applianceAttribute.setMax(100.0);
        applianceAttribute.setMin(0.0);
        attributes.add(applianceAttribute);

        var appliances = Arrays.asList(Appliance.builder()
                .powerOn(false)
                .room(Room.ROOM_1)
                .type(DeviceType.FRIDGE)
                .attributes(attributes)
                .build());

        when(applianceRepository.findAll()).thenReturn(appliances);

        var output = toTest.getAppliances();

        assertAll(
                () -> assertNotNull(output)
        );
    }

    @Test
    public void test_find_appliance_by_type_ok() {

        List<ApplianceAttribute> attributes = new ArrayList<>();
        ApplianceAttribute applianceAttribute = new ApplianceAttribute();
        applianceAttribute.setAttribute(DeviceAttribute.HUMIDITY);
        applianceAttribute.setMax(100.0);
        applianceAttribute.setMin(0.0);
        attributes.add(applianceAttribute);

        var appliance = Appliance.builder()
                .powerOn(false)
                .room(Room.ROOM_1)
                .type(DeviceType.FRIDGE)
                .attributes(attributes)
                .build();

        when(applianceRepository.findByType(any())).thenReturn(Optional.of(appliance));


        var output = toTest.findApplianceByType(appliance.getType().name());

        assertAll(
                () -> assertNotNull(output)
        );
    }

    @Test
    public void test_add_appliance_ok() {

        List<ApplianceAttribute> attributes = new ArrayList<>();
        ApplianceAttribute applianceAttribute = new ApplianceAttribute();
        applianceAttribute.setAttribute(DeviceAttribute.HUMIDITY);
        applianceAttribute.setMax(100.0);
        applianceAttribute.setMin(0.0);
        attributes.add(applianceAttribute);

        var appliance = Appliance.builder()
                .powerOn(false)
                .room(Room.ROOM_1)
                .type(DeviceType.FRIDGE)
                .attributes(attributes)
                .build();

        when(applianceRepository.save(any())).thenReturn(appliance);


        var output = toTest.addAppliance(APPLIANCE_MAPPER.toDto(appliance));

        assertAll(
                () -> assertNotNull(output)
        );
    }

    @Test
    public void test_delete_appliance_ok() {
        List<ApplianceAttribute> attributes = new ArrayList<>();
        ApplianceAttribute applianceAttribute = new ApplianceAttribute();
        applianceAttribute.setAttribute(DeviceAttribute.HUMIDITY);
        applianceAttribute.setMax(100.0);
        applianceAttribute.setMin(0.0);
        attributes.add(applianceAttribute);

        var appliance = Appliance.builder()
                .powerOn(false)
                .room(Room.ROOM_1)
                .type(DeviceType.FRIDGE)
                .attributes(attributes)
                .build();

        when(applianceRepository.findByType(any())).thenReturn(Optional.of(appliance));

        doNothing().when(applianceRepository).delete(any());

        toTest.deleteAppliance(appliance.getType().name());
    }
}
