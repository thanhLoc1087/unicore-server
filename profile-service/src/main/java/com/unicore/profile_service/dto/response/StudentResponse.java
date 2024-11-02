package com.unicore.profile_service.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.profile_service.enums.MemberGender;
import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class StudentResponse {
    private String id;
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
