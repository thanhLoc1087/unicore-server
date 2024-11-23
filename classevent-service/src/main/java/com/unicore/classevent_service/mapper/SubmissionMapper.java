package com.unicore.classevent_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

import com.unicore.classevent_service.dto.request.SubmissionCreationRequest;
import com.unicore.classevent_service.dto.response.SubmissionResponse;
import com.unicore.classevent_service.entity.Submission;

@Mapper(componentModel = ComponentModel.SPRING)
public interface SubmissionMapper {
    public Submission toSubmission(SubmissionCreationRequest request);
    public SubmissionResponse toSubmissionResponse(Submission submission);
}
