package com.unicore.classevent_service.dto.request;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.classevent_service.entity.QueryOption;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QueryUpdateRequest {
    private String name;
    private String description;
    @JsonProperty("start_date")
    private LocalDateTime startDate;
    @JsonProperty("end_date")
    private LocalDateTime endDate;
    @JsonProperty("allow_multiple")
    private Boolean allowMultiple;
    @JsonProperty("allow_suggestion")
    private Boolean allowSuggestion;

    private List<QueryOption> options;
}
