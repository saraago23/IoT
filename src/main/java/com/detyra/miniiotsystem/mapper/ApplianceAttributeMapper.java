package com.detyra.miniiotsystem.mapper;

import com.detyra.miniiotsystem.controller.dto.ApplianceAttributeDTO;
import com.detyra.miniiotsystem.entity.ApplianceAttribute;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ApplianceAttributeMapper extends BaseMapper<ApplianceAttribute, ApplianceAttributeDTO> {
    ApplianceAttributeMapper APPLIANCE_ATTRIBUTE_MAPPER = Mappers.getMapper(ApplianceAttributeMapper.class);

}
