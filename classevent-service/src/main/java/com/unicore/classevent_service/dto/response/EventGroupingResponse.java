package com.unicore.classevent_service.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.classevent_service.entity.Group;
import com.unicore.classevent_service.enums.EventType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventGroupingResponse {
    private String id;

    @JsonProperty("event_id")
    private String eventId;

    @JsonProperty("event_type")
    private EventType eventType;

    @JsonProperty("start_register_date")
    private LocalDateTime startRegisterDate;

    @JsonProperty("end_register_date")
    private LocalDateTime endRegisterDate;

    @JsonIgnore
    private boolean hasLeader;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer maxSize;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer minSize;

    private boolean useDefaultGroups;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Group> groups;

}
