package com.unicore.classevent_service.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.classevent_service.enums.EventType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventGrouping {
    @Id
    private String id;

    @JsonProperty("event_id")
    @Indexed(unique = true)
    private String eventId;

    @JsonProperty("event_type")
    private EventType eventType;

    @JsonProperty("start_register_date")
    private LocalDateTime startRegisterDate;

    @JsonProperty("end_register_date")
    private LocalDateTime endRegisterDate;

    @JsonProperty("has_leader")
    private boolean hasLeader;

    @JsonProperty("max_size")
    private int maxSize;

    @JsonProperty("min_size")
    private int minSize;
    
    private boolean useDefaultGroups;

    private List<Group> groups;

    
    @JsonIgnore
    public boolean hasLeader() {
        return !useDefaultGroups && hasLeader;
    }

    @JsonIgnore
    public Integer getMaxSize() {
        return useDefaultGroups ? null : maxSize;
    }

    @JsonIgnore
    public Integer getMinSize() {
        return useDefaultGroups ? null : minSize;
    }

    @JsonIgnore
    public List<Group> getGroups() {
        return useDefaultGroups ? null : groups;
    }
}
