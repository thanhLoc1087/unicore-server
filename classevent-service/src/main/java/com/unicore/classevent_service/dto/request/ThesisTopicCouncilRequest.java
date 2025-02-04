package com.unicore.classevent_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThesisTopicCouncilRequest {
    private String topicName;
    private String councilPrincipalCode;
    private String councilSecretaryCode;
    private String councilMemberCode;
}
