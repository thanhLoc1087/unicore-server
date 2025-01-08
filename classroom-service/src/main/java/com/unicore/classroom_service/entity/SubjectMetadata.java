package com.unicore.classroom_service.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.classroom_service.enums.ExamFormat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubjectMetadata {
    private String id;
    private String name;
    
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
}
