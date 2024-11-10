package com.unicore.classroom_service.entity;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Group {
    private String index;
    private String name;
    private String teacherCode;
    private List<StudentInGroup> members;
}
