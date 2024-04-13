package com.detyra.miniiotsystem.mapper;

import com.detyra.miniiotsystem.entity.Appliance;
import com.detyra.miniiotsystem.controller.dto.ApplianceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ApplianceMapper extends BaseMapper<Appliance, ApplianceDTO> {
    ApplianceMapper APPLIANCE_MAPPER = Mappers.getMapper(ApplianceMapper.class);

}
