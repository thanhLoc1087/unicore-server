package com.unicore.grade_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewFormCreationRequest {
    private String projectId;
    private String topicId;
}
