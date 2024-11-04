package com.unicore.classroom_service.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.classroom_service.enums.ClassType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Subclass {
    private String code;

    @JsonProperty("teacher_code")
    private String teacherCode;

    @JsonProperty("teacher_assistant_code")
    private String teacherAssistantCode;

    @JsonProperty("start_date")
    private LocalDate startDate;
    
    @JsonProperty("end_date")
    private LocalDate endDate;

    private ClassType type;
    private String note;
}
