package com.unicore.profile_service.dto.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.profile_service.enums.MemberGender;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherUpdateRequest {
    private String id;
    
    private String code;
    private String name;
    private String phone;
    private String address;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dob;    
    
    private MemberGender gender;
    private String degree;
    @JsonProperty("research_direction")
    private String researchDirection;
    @JsonProperty("research_concern")
    private String researchConcern;
}
