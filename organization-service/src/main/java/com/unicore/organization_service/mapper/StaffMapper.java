package com.unicore.organization_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.unicore.organization_service.dto.request.StaffCreationRequest;
import com.unicore.organization_service.dto.response.StaffResponse;
import com.unicore.organization_service.entity.Staff;

@Mapper(componentModel = "spring")
public interface StaffMapper {
    @Mapping(target = "role", constant = "STAFF")
    public Staff toStaff(StaffCreationRequest request);

    public StaffResponse toStaffResponse(Staff staff);
}
