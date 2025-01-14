package com.unicore.classevent_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetEventCreationVariablesRequest {
    private String classId;
    private String subclassCode;
    private String teacherCode;
}
