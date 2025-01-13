package com.unicore.classevent_service.dto.request;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class GroupingScheduleRequest {
    @JsonProperty("start_register_date")
    private LocalDateTime startRegisterDate;

    @JsonProperty("end_register_date")
    private LocalDateTime endRegisterDate;

    @JsonProperty("max_size")
    private int maxSize;

    @JsonProperty("min_size")
    private int minSize;
}