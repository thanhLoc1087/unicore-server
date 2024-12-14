package com.unicore.classevent_service.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.classevent_service.enums.ExamFormat;

import lombok.Data;

@Data
public class SubjectMetadata {
    private String id;
    
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

    private int semester;
    private int year;
}
