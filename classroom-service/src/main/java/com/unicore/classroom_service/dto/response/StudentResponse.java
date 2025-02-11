package com.unicore.classroom_service.dto.response;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponse {
    private String id;
    @JsonProperty("organization_id")
    private String organizationId;
    private String code;
    private String name;
    private String phone;
    private String address;
    private String email;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;
    @JsonProperty("advisory_class")
    private String advisoryClass;
    @JsonProperty("academic_batch")
    private String academicBatch;
    private boolean deleted;
    
}
