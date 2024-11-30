package com.unicore.classevent_service.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewCreationRequest {
    @JsonProperty("submission_id")
    private String submissionId;
    @JsonProperty("submitter_id")
    private String submitterId;
    @JsonProperty("name_id")
    private String nameId;

    @JsonProperty("class_id")
    private String classId;
    @JsonProperty("subclass_code")
    private String subclassCode;
}
