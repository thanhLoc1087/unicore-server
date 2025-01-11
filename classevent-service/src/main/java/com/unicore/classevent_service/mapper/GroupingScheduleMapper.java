package com.unicore.classevent_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.MappingConstants.ComponentModel;

import com.unicore.classevent_service.dto.request.ClassGroupingScheduleRequest;
import com.unicore.classevent_service.dto.request.GroupingScheduleRequest;
import com.unicore.classevent_service.dto.response.GroupingResponse;
import com.unicore.classevent_service.entity.GroupingSchedule;

@Mapper(componentModel = ComponentModel.SPRING)
public interface GroupingScheduleMapper {
    GroupingSchedule toGroupingSchedule(GroupingScheduleRequest request);
    GroupingSchedule classGroupingToGroupingSchedule(ClassGroupingScheduleRequest request);
    void updateSchedule(@MappingTarget GroupingSchedule schedule, GroupingScheduleRequest request);
    GroupingResponse toResponse(GroupingSchedule groupingSchedule);
}
