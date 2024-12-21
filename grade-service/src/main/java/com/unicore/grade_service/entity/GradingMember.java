package com.unicore.grade_service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GradingMember {
    private String code;
    private String name;
    private String email;
}
