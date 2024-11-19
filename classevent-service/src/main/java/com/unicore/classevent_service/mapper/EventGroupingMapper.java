package com.unicore.classevent_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.MappingConstants.ComponentModel;

import com.unicore.classevent_service.dto.request.EventGroupingCreationRequest;
import com.unicore.classevent_service.dto.request.EventGroupingUpdateRequest;
import com.unicore.classevent_service.dto.response.EventGroupingResponse;
import com.unicore.classevent_service.entity.EventGrouping;

@Mapper(componentModel = ComponentModel.SPRING)
public interface EventGroupingMapper {
    public EventGrouping toEventGrouping(EventGroupingCreationRequest request);

    // TODO: rewrite this
    public EventGrouping toEventGrouping(@MappingTarget EventGrouping eventGrouping, EventGroupingUpdateRequest request);
    
    public EventGroupingResponse toEventGroupingResponse(EventGrouping grouping);
}
