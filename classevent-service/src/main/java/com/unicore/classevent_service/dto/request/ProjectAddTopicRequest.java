package com.unicore.classevent_service.dto.request;

import java.util.List;

import org.springframework.lang.NonNull;

import com.unicore.classevent_service.entity.Topic;

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
    private List<Topic> topics;
}
