package com.unicore.classroom_service.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentForGroupingResponse {
    private String id;
    private String code;
    private String name;
    private String email;
    @JsonProperty("advisory_class")
    private String advisoryClass;

    private boolean valid;

    private String classId;
    private String subclassCode;

    private String reasonForInvalid;
}
