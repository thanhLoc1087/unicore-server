package com.unicore.classevent_service.dto.request;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubmissionFeedbackRequest {
    private Float grade;
    private String feedback;
    private Map<String, Float> memberGrades;
}
