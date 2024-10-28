package com.unicore.organization_service.dto.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.organization_service.enums.MemberGender;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class StudentCreationRequest {
    @JsonProperty("organization_id")
    private String organizationId;
    private String code;
    private String name;
    @Email(message = "Invalid email format")
    private String email;
    private LocalDate dob;
    private MemberGender gender;
    @JsonProperty("advisory_class")
    private String advisoryClass;
    @JsonProperty("academic_batch")
    private String academicBatch;
}
