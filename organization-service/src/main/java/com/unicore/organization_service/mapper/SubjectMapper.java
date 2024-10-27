package com.unicore.organization_service.mapper;

import org.mapstruct.Mapper;

import com.unicore.organization_service.dto.request.SubjectCreationRequest;
import com.unicore.organization_service.dto.response.SubjectResponse;
import com.unicore.organization_service.entity.Subject;
import com.unicore.organization_service.entity.SubjectMetadata;

@Mapper(componentModel = "spring")
public interface SubjectMapper {
    public Subject toSubject(SubjectCreationRequest request);

    public Subject toSubject(SubjectResponse response);

    public SubjectMetadata toSubjectMetadata(SubjectCreationRequest request);

    public SubjectResponse toSubjectResponse(Subject subject);
}
