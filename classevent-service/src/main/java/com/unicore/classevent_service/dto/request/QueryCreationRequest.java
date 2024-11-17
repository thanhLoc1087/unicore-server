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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QueryCreationRequest {
    @JsonProperty("class_id")
    private String classId;
    @JsonProperty("subclass_code")
    private String subclassCode;
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

    // student grouping id or list id
    private String groupingId;

    private List<QueryOption> options;
}
