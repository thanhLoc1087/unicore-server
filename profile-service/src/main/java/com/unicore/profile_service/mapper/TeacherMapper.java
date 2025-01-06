package com.unicore.profile_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.unicore.profile_service.dto.request.TeacherCreationRequest;
import com.unicore.profile_service.dto.request.TeacherUpdateRequest;
import com.unicore.profile_service.dto.response.TeacherResponse;
import com.unicore.profile_service.entity.Teacher;

@Mapper(componentModel = "spring")
public interface TeacherMapper {
    @Mapping(target = "role", constant = "TEACHER")
    public Teacher toTeacher(TeacherCreationRequest request);

    public TeacherResponse toTeacherResponse(Teacher teacher);

    public void updateTeacher(@MappingTarget Teacher teacher, TeacherUpdateRequest request);
}
