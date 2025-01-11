package com.unicore.classevent_service.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupingSchedule {
    @Id
    private String id;

    @JsonProperty("class_id")
    private String classId;

    @JsonProperty("subclass_code")
    private String subclassCode;

    @JsonProperty("start_register_date")
    private LocalDateTime startRegisterDate;

    @JsonProperty("end_register_date")
    private LocalDateTime endRegisterDate;

    @JsonProperty("max_size")
    private int maxSize;

    @JsonProperty("min_size")
    private int minSize;
}
