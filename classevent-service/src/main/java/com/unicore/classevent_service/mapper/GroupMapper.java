package com.unicore.classevent_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.MappingConstants.ComponentModel;

import com.unicore.classevent_service.dto.GroupRequest;
import com.unicore.classevent_service.entity.Group;

@Mapper(componentModel = ComponentModel.SPRING)
public interface GroupMapper {
    Group toGroup(GroupRequest request);
    Group updateGroup(@MappingTarget Group group, GroupRequest request);
}
