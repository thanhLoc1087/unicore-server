package com.unicore.classroom_service.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.classroom_service.enums.ClassType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Subclass {
    private String code;

    @JsonProperty("main_teacher_code")
    private String mainTeacherCode;

    @JsonProperty("teacher_codes")
    private List<String> teacherCodes;
    @JsonProperty("teacher_names")
    private List<String> teacherNames;

    @JsonProperty("start_date")
    private LocalDate startDate;
    
    @JsonProperty("end_date")
    private LocalDate endDate;

    @JsonProperty("max_size")
    private int maxSize;

    @JsonProperty("current_size")
    private int currentSize;

    private int credits;
    private ClassType type;
    private String note;


}
