package com.unicore.classevent_service.entity;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseworkWeight {
    @Id
    private String id;
    private String name;
    private String description;
    private int value;
}
