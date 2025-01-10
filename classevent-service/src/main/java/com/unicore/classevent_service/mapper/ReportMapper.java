package com.unicore.classevent_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.MappingConstants.ComponentModel;

import com.unicore.classevent_service.dto.request.ReportCreationRequest;
import com.unicore.classevent_service.dto.request.ReportUpdateRequest;
import com.unicore.classevent_service.dto.response.ReportResponse;
import com.unicore.classevent_service.entity.Report;

@Mapper(componentModel = ComponentModel.SPRING)
public interface ReportMapper {
    
    public Report toReport(ReportCreationRequest request);

    public Report toReport(@MappingTarget Report report, ReportUpdateRequest request);

    public ReportResponse toReportResponse(Report report);
}
