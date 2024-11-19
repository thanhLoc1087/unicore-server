package com.unicore.classevent_service.dto.request;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.classevent_service.entity.Group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventGroupingUpdateRequest {
    @JsonProperty("start_register_date")
    private LocalDateTime startRegisterDate;

    @JsonProperty("end_register_date")
    private LocalDateTime endRegisterDate;

    @JsonProperty("use_default_groups")
    private Boolean useDefaultGroups;

    @JsonProperty("has_leader")
    private Boolean hasLeader;

    @JsonProperty("max_size")
    private int maxSize;

    @JsonProperty("min_size")
    private int minSize;

    private List<Group> groups;
}
