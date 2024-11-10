package com.unicore.classroom_service.mapper;

import org.mapstruct.Mapper;

import com.unicore.classroom_service.dto.request.ClassroomCreationRequest;
import com.unicore.classroom_service.dto.response.ClassroomResponse;
import com.unicore.classroom_service.entity.Classroom;
import com.unicore.classroom_service.entity.Subclass;

@Mapper(componentModel = "spring")
public interface ClassroomMapper {
    public Classroom toClassroom(ClassroomCreationRequest request);
    public Classroom toClassroom(ClassroomResponse response);
    public ClassroomResponse toClassroomResponse(Classroom classroom);
    public Subclass toSubclass(ClassroomCreationRequest request);
}
