package com.unicore.classevent_service.dto.request;

import com.unicore.classevent_service.enums.WeightType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetStudentClassGradeRequest {
    private String studentCode; 
    private String classId;
    private WeightType weightType;
}
