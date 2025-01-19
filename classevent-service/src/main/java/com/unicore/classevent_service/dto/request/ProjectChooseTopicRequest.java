package com.unicore.classevent_service.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.classevent_service.dto.GroupRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectChooseTopicRequest {
    @JsonProperty("project_id")
    private String projectId;
    @JsonProperty("topic_id")
    private String topicId;
    private String selector;

    private GroupRequest group;
}
