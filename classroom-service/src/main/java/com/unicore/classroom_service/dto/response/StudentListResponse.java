package com.unicore.classroom_service.dto.response;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentListResponse {
    private String id;
    @JsonProperty("class_code")
    private String classCode;
    @JsonProperty("leader_code")
    private String leaderCode;
    @JsonProperty("student_codes")
    private Set<String> studentCodes;
}
