package com.unicore.profile_service.dto.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.profile_service.enums.MemberGender;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class StaffCreationRequest {
    @JsonProperty("organization_id")
    private String organizationId;
    private String code;
    private String name;
    @Email(message = "Invalid email format")
    private String email;
    private String phone;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dob;
    private MemberGender gender;
    private String position;
}
