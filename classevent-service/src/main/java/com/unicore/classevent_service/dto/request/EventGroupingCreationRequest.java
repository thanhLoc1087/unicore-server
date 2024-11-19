package com.unicore.classevent_service.dto.request;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.classevent_service.entity.Group;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventGroupingCreationRequest {
    @JsonProperty("event_id")
    private String eventId;

    @JsonProperty("start_register_date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startRegisterDate;

    @JsonProperty("end_register_date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endRegisterDate;
    
    @JsonProperty("use_default_groups")
    private boolean useDefaultGroups;

    @JsonProperty("has_leader")
    private boolean hasLeader;

    @JsonProperty("max_size")
    private int maxSize;

    @JsonProperty("min_size")
    private int minSize;
    
    @JsonProperty("create_subclass")
    private boolean createSubclass;

    private List<Group> groups;
}
