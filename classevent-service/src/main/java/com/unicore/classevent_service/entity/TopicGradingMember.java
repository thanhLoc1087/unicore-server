package com.unicore.classevent_service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicGradingMember {
    private String id;
    private String code;
    private String name;
    private float grade;
}
