package com.unicore.profile_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.unicore.profile_service.dto.request.StudentCreationRequest;
import com.unicore.profile_service.dto.request.StudentUpdateRequest;
import com.unicore.profile_service.dto.response.StudentResponse;
import com.unicore.profile_service.entity.Student;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    @Mapping(target = "role", constant = "STUDENT")
    public Student toStudent(StudentCreationRequest request);

    public StudentResponse toStudentResponse(Student student);
    
    public void updateStudent(@MappingTarget Student student, StudentUpdateRequest request);
}
