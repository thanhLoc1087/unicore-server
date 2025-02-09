package com.unicore.classevent_service.dto.response;

import java.util.List;

import com.unicore.classevent_service.entity.StudentInGroup;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectTopicResponse extends TopicResponse {
    private String nameEn;
    private String projectId;
    private Float supervisorGrade;
    private boolean inGroup;
    private String selectorId;
    private List<StudentInGroup> students;
    private String turnDownReason;
}
