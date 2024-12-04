package com.unicore.classevent_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetGroupingRequest {
    private String eventId;
    private String classId;
    private String subclassCode;
}
