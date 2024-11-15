package com.unicore.classroom_service.dto.response;

import java.util.List;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.classroom_service.entity.CourseworkWeightDetail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseworkWeightResponse {
    @Id
    private String id;
    @JsonProperty("class_id")
    private String classId;
    @JsonProperty("subclass_code")
    private String subclassCode;
    
    private List<CourseworkWeightDetail> details;
}
