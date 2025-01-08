package com.unicore.classroom_service.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.classroom_service.entity.SubjectMetadata;
import com.unicore.classroom_service.enums.ExamFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectResponse {
    private String id;
    private String name;
    private String code;
    private String description;

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

    public SubjectMetadata toMetadata() {
        return new SubjectMetadata(
            id, 
            name, 
            midtermFormat, 
            practicalFormat, 
            finalFormat, 
            courseworkWeight, 
            midtermWeight, 
            practicalWeight, 
            finalWeight, 
            midtermTime, 
            finalTime, 
            semester, 
            year);
    }
}
