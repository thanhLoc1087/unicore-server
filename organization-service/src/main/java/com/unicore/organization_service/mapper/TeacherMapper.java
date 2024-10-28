package com.unicore.organization_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.unicore.organization_service.dto.request.TeacherCreationRequest;
import com.unicore.organization_service.dto.response.TeacherResponse;
import com.unicore.organization_service.entity.Teacher;

@Mapper(componentModel = "spring")
public interface TeacherMapper {
    @Mapping(target = "role", constant = "TEACHER")
    public Teacher toTeacher(TeacherCreationRequest request);

    public TeacherResponse toTeacherResponse(Teacher teacher);
}
