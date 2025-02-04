package com.unicore.classevent_service.entity;

import com.unicore.classevent_service.dto.response.TeacherResponse;

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
    private Float grade;

    public TopicGradingMember(TeacherResponse teacherResponse) {
        this(teacherResponse.getId(), teacherResponse.getCode(), teacherResponse.getName(), null);
    }
}
