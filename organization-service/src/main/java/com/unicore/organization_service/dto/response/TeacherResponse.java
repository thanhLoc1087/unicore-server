package com.unicore.organization_service.dto.response;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.organization_service.enums.MemberGender;
import com.unicore.organization_service.enums.MemberRole;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeacherResponse {
    private String id;
    @JsonProperty("organization_id")
    private String organizationId;
    private String code;
    private String name;
    private String email;
    private LocalDate dob;
    private MemberGender gender;
    private MemberRole role;
    private String degree;
    @JsonProperty("research_direction")
    private String researchDirection;
}