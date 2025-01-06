package com.unicore.profile_service.dto.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.profile_service.enums.MemberGender;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentUpdateRequest {
    private String id;
    private String name;
    private String phone;
    
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dob;
    private MemberGender gender;
    @JsonProperty("advisory_class")
    private String advisoryClass;
    @JsonProperty("academic_batch")
    private String academicBatch;
}
