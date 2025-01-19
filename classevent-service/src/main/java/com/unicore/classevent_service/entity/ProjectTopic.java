package com.unicore.classevent_service.entity;

import java.util.Comparator;
import java.util.List;

import com.unicore.classevent_service.enums.TopicType;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
    @Builder.Default
    private boolean inGroup = true;
    private String selectorId;
    private String turnDownReason;
    @Override
    public String genId() {
        String id = projectId + "_" + selectorId;
        setId(id);
        return id;
    }
}
