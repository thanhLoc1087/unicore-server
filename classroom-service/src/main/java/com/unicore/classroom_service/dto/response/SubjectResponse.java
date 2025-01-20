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

    public static SubjectResponse fromSubjectClientResponse(SubjectClientResponse client) {
        return new SubjectResponse(
            client.getId(),
            client.getName(),
            client.getCode(),
            client.getDescription(),
            client.getMetadata().getMidtermFormat(),
            client.getMetadata().getPracticalFormat(),
            client.getMetadata().getFinalFormat(),
            client.getMetadata().getCourseworkWeight(),
            client.getMetadata().getMidtermWeight(),
            client.getMetadata().getPracticalWeight(),
            client.getMetadata().getFinalWeight(),
            client.getMetadata().getMidtermTime(),
            client.getMetadata().getFinalTime(),
            client.getMetadata().getSemester(),
            client.getMetadata().getYear()
        );
    }
}
