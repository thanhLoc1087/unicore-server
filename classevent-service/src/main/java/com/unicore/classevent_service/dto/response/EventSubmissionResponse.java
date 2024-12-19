package com.unicore.classevent_service.dto.response;

import com.unicore.classevent_service.entity.BaseEvent;
import com.unicore.classevent_service.entity.Submission;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventSubmissionResponse {
    private String studentCode;
    private BaseEvent event;
    private Submission submission;
}
