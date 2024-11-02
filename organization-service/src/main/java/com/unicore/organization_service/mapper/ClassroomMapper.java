package com.unicore.organization_service.mapper;

import org.mapstruct.Mapper;

import com.unicore.organization_service.dto.request.ClassCreationRequest;
import com.unicore.organization_service.dto.response.ClassroomResponse;
import com.unicore.organization_service.entity.Classroom;

@Mapper(componentModel = "spring")
public interface ClassroomMapper {
    public Classroom toClassroom(ClassCreationRequest request);
    public ClassroomResponse toClassroomResponse(Classroom classroom);
}
