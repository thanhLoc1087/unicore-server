package com.unicore.classevent_service.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicRegisterScheduleRequest extends GroupingScheduleRequest {
    @JsonProperty("use_default_groups")
    private boolean useDefaultGroups;
}
