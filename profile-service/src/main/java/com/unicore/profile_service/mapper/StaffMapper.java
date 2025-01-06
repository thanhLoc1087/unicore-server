package com.unicore.profile_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.unicore.profile_service.dto.request.StaffCreationRequest;
import com.unicore.profile_service.dto.request.StaffUpdateRequest;
import com.unicore.profile_service.dto.response.StaffResponse;
import com.unicore.profile_service.entity.Staff;

@Mapper(componentModel = "spring")
public interface StaffMapper {
    @Mapping(target = "role", constant = "STAFF")
    public Staff toStaff(StaffCreationRequest request);

    public StaffResponse toStaffResponse(Staff staff);

    public void updateStaff(@MappingTarget Staff staff, StaffUpdateRequest request);
}
