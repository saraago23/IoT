package com.detyra.miniiotsystem.service;

import com.detyra.miniiotsystem.entity.DataPoint;
import com.detyra.miniiotsystem.entity.enums.DeviceAttribute;
import com.detyra.miniiotsystem.entity.enums.DeviceType;

public interface CoreService {

    void readDataModeOnOff(DeviceType type, DeviceAttribute attr);
    void handleDataPoint(DataPoint dp);
    void readData(DeviceType type, DeviceAttribute attr);

}