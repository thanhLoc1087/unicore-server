package com.unicore.profile_service.dto.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.profile_service.enums.MemberGender;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class TeacherCreationRequest {
    @JsonProperty("organization_id")
    private String organizationId;
    private String code;
    private String name;
    @Email(message = "Invalid email format")
    private String email;
    private String phone;
    private LocalDate dob;
    private MemberGender gender;
    private String degree;
    @JsonProperty("research_direction")
    private String researchDirection;
}
