package com.detyra.miniiotsystem.service;
import com.detyra.miniiotsystem.entity.DataPoint;
import com.detyra.miniiotsystem.entity.enums.DeviceAttribute;
import com.detyra.miniiotsystem.entity.enums.DeviceType;


public interface CoreService {

    public void handleDataPoint(DataPoint dp);

    public void readData(DeviceType type, DeviceAttribute attr);

    public void readDataModeOnOff(DeviceType type, DeviceAttribute attr);

}