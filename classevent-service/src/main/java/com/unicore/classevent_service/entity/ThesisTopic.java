package com.unicore.classevent_service.entity;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.unicore.classevent_service.enums.TopicType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ThesisTopic extends NewTopic {
    {
        setType(TopicType.KLTN);
    }
    private String nameEn;

    private String projectId;

    private String councilCode;
    private TopicGradingMember councilPrincipal;
    private TopicGradingMember councilSecretary;
    private TopicGradingMember councilMember;
    private boolean councilGraded;

    private Group group;

    private String turnDownReason;

    private TopicGradingMember evaluator;
    private boolean evaluatorGraded;

    private Float supervisorGrade;

    @Override
    public String genId() {
        String id = projectId;
        List<StudentInGroup> students = List.copyOf(group.getMembers());
        students.sort(Comparator.comparing(StudentInGroup::getStudentCode));
        for (StudentInGroup student : students) {
            id += "_" + student.getStudentCode();
        }
        setId(id);
        return id;
    }
}
