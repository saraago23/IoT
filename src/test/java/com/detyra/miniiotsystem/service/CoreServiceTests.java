package com.detyra.miniiotsystem.service;

import com.detyra.miniiotsystem.entity.Appliance;
import com.detyra.miniiotsystem.entity.ApplianceAttribute;
import com.detyra.miniiotsystem.entity.DataPoint;
import com.detyra.miniiotsystem.entity.SignalData;
import com.detyra.miniiotsystem.entity.enums.DeviceAttribute;
import com.detyra.miniiotsystem.entity.enums.DeviceType;
import com.detyra.miniiotsystem.entity.enums.Room;
import com.detyra.miniiotsystem.repository.ApplianceRepository;
import com.detyra.miniiotsystem.repository.DataPointRepository;
import com.detyra.miniiotsystem.service.impl.CoreServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CoreServiceTests {
    @Mock
    SimpMessagingTemplate messagingTemplate;
    @Mock
    DataPointRepository dataPointRepository;
    @Mock
    ApplianceRepository applianceRepository;

    @InjectMocks
    CoreServiceImpl toTest;

    @Test
    public void handle_data_point(){
        DataPoint dp = new DataPoint();
        dp.setType(DeviceType.FRIDGE);
        dp.setAttribute(DeviceAttribute.HUMIDITY);
        dp.setValue(50.0);

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

        when(applianceRepository.findByType(DeviceType.FRIDGE)).thenReturn(Optional.of(appliance));

        when(dataPointRepository.save(any(DataPoint.class))).thenReturn(dp);

        toTest.handleDataPoint(dp);

        ArgumentCaptor<SignalData> captor = ArgumentCaptor.forClass(SignalData.class);
        verify(messagingTemplate).convertAndSend(eq("/topic/signals"), captor.capture());
        SignalData signalData = captor.getValue();

        if (dp.getValue() > applianceAttribute.getMax() || dp.getValue() < applianceAttribute.getMin()) {
            verify(messagingTemplate).convertAndSend(eq("/topic/alerts"), eq("Value of: DeviceType DeviceAttribute : 50.0 is critical! "));
            assert(signalData.getCritical());
        } else {
            verify(messagingTemplate, never()).convertAndSend(eq("/topic/alerts"), anyString());
            assert(!signalData.getCritical());
        }
    }
}
