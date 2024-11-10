package com.unicore.classroom_service.dto.request;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.classroom_service.entity.Group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentGroupingUpdateRequest {
    private String id;

    @JsonProperty("start_register_date")
    private LocalDate startRegisterDate;

    @JsonProperty("end_register_date")
    private LocalDate endRegisterDate;

    @JsonProperty("has_leader")
    private Optional<Boolean> hasLeader;

    @JsonProperty("max_size")
    private int maxSize;

    @JsonProperty("min_size")
    private int minSize;

    private List<Group> groups;
}
