package com.unicore.classroom_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

import com.unicore.classroom_service.dto.request.StudentListCreationRequest;
import com.unicore.classroom_service.dto.response.StudentListResponse;
import com.unicore.classroom_service.entity.StudentList;

@Mapper(componentModel = ComponentModel.SPRING)
public interface StudentListMapper {
    public StudentList toStudentList(StudentListCreationRequest request);
    public StudentListResponse toStudentListResponse(StudentList studentList);
}
