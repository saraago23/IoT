package com.detyra.miniiotsystem.service;

import com.detyra.miniiotsystem.entity.Appliance;
import com.detyra.miniiotsystem.entity.ApplianceAttribute;
import com.detyra.miniiotsystem.entity.DataPoint;
import com.detyra.miniiotsystem.entity.enums.DeviceAttribute;
import com.detyra.miniiotsystem.entity.enums.DeviceType;
import com.detyra.miniiotsystem.entity.enums.Room;
import com.detyra.miniiotsystem.repository.ApplianceRepository;
import com.detyra.miniiotsystem.service.impl.DeviceDataReaderServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeviceDataReaderServiceTests {
    @Mock
    CoreService coreService;
    @Mock
    ApplianceRepository applianceRepository;
    @InjectMocks
    DeviceDataReaderServiceImpl toTest;

    @Test
    public void read_data_test() {

        DeviceType type = DeviceType.FRIDGE;
        DeviceAttribute attr = DeviceAttribute.HUMIDITY;

        List<ApplianceAttribute> attributes = new ArrayList<>();
        ApplianceAttribute applianceAttribute = new ApplianceAttribute();
        applianceAttribute.setAttribute(DeviceAttribute.HUMIDITY);
        applianceAttribute.setMax(100.0);
        applianceAttribute.setMin(0.0);
        attributes.add(applianceAttribute);

        var appliance = Appliance.builder()
                .powerOn(true)
                .room(Room.ROOM_1)
                .type(DeviceType.FRIDGE)
                .attributes(attributes)
                .build();

        when(applianceRepository.findByType(type)).thenReturn(Optional.of(appliance));

        toTest.readData(type, attr);

        ArgumentCaptor<DataPoint> captor = ArgumentCaptor.forClass(DataPoint.class);
        verify(coreService, times(100)).handleDataPoint(captor.capture());

        captor.getAllValues().forEach(Assertions::assertNotNull);
    }

    @Test
    public void testReadDataModeOnOff() {
        DeviceType type = DeviceType.FRIDGE;
        DeviceAttribute attr = DeviceAttribute.HUMIDITY;

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

        when(applianceRepository.findByType(type)).thenReturn(Optional.of(appliance));

        toTest.readDataModeOnOff(type, attr);

        verify(applianceRepository).save(appliance);

        verify(coreService).handleDataPoint(any(DataPoint.class));

        ArgumentCaptor<DataPoint> captor = ArgumentCaptor.forClass(DataPoint.class);
        verify(coreService).handleDataPoint(captor.capture());

        captor.getAllValues().forEach(dataPoint -> {
            assertNotNull(dataPoint);
            assertEquals(DeviceType.FRIDGE, dataPoint.getType());
            assertEquals(DeviceAttribute.HUMIDITY, dataPoint.getAttribute());
            assertTrue(dataPoint.getValue() == 0.0 || dataPoint.getValue() == 1.0);
            assertNotNull(dataPoint.getNow());
        });
    }

}
