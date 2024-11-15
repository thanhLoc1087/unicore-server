package com.unicore.classroom_service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseworkWeightDetail {
    private String code;
    private String name;
    private String description;
    private int value;
}
