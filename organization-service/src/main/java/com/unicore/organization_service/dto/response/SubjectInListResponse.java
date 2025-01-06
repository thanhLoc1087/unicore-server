package com.unicore.organization_service.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.organization_service.enums.ExamFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubjectInListResponse {
    private String id;
    private String name;
    private String code;
    private String description;
    @JsonProperty("organization_id")
    private String organizationId;

    @JsonProperty("midterm_format")
    private ExamFormat midtermFormat;
    @JsonProperty("practical_format")
    private ExamFormat practicalFormat;
    @JsonProperty("final_format")
    private ExamFormat finalFormat;

    @JsonProperty("coursework_weight")
    private int courseworkWeight;
    @JsonProperty("midterm_weight")
    private int midtermWeight;
    @JsonProperty("practical_weight")
    private int practicalWeight;
    @JsonProperty("final_weight")
    private int finalWeight;
    
    @JsonProperty("midterm_time")
    private int midtermTime;
    @JsonProperty("final_time")
    private int finalTime;

    private int semester;
    private int year;

    static public SubjectInListResponse fromSubject(SubjectResponse subject) {
        return SubjectInListResponse.builder()
            .id(subject.getId())
            .name(subject.getName())
            .code(subject.getCode())
            .description(subject.getDescription())
            .organizationId(subject.getOrganizationId())
            .midtermFormat(subject.getMetadata().getMidtermFormat())
            .practicalFormat(subject.getMetadata().getPracticalFormat())
            .finalFormat(subject.getMetadata().getFinalFormat())
            .courseworkWeight(subject.getMetadata().getCourseworkWeight())
            .midtermWeight(subject.getMetadata().getMidtermWeight())
            .practicalWeight(subject.getMetadata().getPracticalWeight())
            .finalWeight(subject.getMetadata().getFinalWeight())
            .midtermTime(subject.getMetadata().getMidtermTime())
            .finalTime(subject.getMetadata().getFinalTime())
            .build();
    }
}
