package com.unicore.classevent_service.dto.request;

import java.util.List;

import org.springframework.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectAddTopicRequest {
    @NonNull
    private List<ProjectTopicRequest> topics;
}
