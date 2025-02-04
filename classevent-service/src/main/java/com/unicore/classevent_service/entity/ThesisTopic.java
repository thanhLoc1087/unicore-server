package com.unicore.classevent_service.entity;

import com.unicore.classevent_service.enums.TopicType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ThesisTopic extends ProjectTopic {
    {
        setType(TopicType.KLTN);
    }
    private String councilCode;
    private TopicGradingMember councilPrincipal;
    private TopicGradingMember councilSecretary;
    private TopicGradingMember councilMember;
    private boolean councilGraded;

    private TopicGradingMember evaluator;
    private boolean evaluatorGraded;
}
