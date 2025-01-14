package com.unicore.classevent_service.entity;

import java.util.Comparator;
import java.util.List;

import com.unicore.classevent_service.enums.TopicType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ProjectTopic extends NewTopic {
    {
        setType(TopicType.BTL);
    }
    private String nameEn;
    private String projectId;
    private Float supervisorGrade;
    private Group group;
    private String turnDownReason;
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
