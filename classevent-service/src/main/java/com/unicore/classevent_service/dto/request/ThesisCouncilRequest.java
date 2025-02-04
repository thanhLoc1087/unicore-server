package com.unicore.classevent_service.dto.request;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThesisCouncilRequest {
    private String projectId;
    private String councilCode;
    private List<String> teacherCodes;
    private String staffCode;
    private LocalDateTime reportTime;
    private String location;
    private List<ThesisTopicCouncilRequest> topics = new ArrayList<>();
}
