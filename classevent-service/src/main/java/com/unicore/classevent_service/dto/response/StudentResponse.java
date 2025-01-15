package com.unicore.classevent_service.dto.response;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class StudentResponse {
    private String id;
    @JsonProperty("organization_id")
    private String organizationId;
    private String code;
    private String name;
    private String email;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dob;
    @JsonProperty("advisory_class")
    private String advisoryClass;
    @JsonProperty("academic_batch")
    private String academicBatch;
}
