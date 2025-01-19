package com.unicore.classevent_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicRegisterScheduleRequest extends GroupingScheduleRequest {
    private boolean useDefaultGroups;
}
