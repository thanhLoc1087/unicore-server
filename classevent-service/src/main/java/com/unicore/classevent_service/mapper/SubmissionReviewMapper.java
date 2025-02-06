package com.unicore.classevent_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

import com.unicore.classevent_service.dto.request.ReviewCreationRequest;
import com.unicore.classevent_service.dto.response.SubmissionReviewResponse;
import com.unicore.classevent_service.entity.SubmissionReview;

@Mapper(componentModel = ComponentModel.SPRING)
public interface SubmissionReviewMapper {
    public SubmissionReviewResponse toResponse(SubmissionReview review);

    public SubmissionReview toSubmissionReview(ReviewCreationRequest request);
}
