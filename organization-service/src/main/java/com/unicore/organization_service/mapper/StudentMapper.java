package com.unicore.organization_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.unicore.organization_service.dto.request.StudentCreationRequest;
import com.unicore.organization_service.dto.response.StudentResponse;
import com.unicore.organization_service.entity.Student;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    @Mapping(target = "role", constant = "STUDENT")
    public Student toStudent(StudentCreationRequest request);

    public StudentResponse toStudentResponse(Student student);
    
}
