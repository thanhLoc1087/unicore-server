package com.unicore.classevent_service.dto.request;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class GroupingScheduleRequest {
    @JsonProperty("start_register_date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startRegisterDate;

    @JsonProperty("end_register_date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endRegisterDate;

    @JsonProperty("max_size")
    @Builder.Default
    private int maxSize = 5;
    
    @JsonProperty("min_size")
    @Builder.Default
    private int minSize = 1;

    @JsonProperty("has_leader")
    private boolean hasLeader;
}
