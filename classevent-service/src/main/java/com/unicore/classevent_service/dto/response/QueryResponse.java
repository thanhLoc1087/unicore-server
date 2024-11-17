package com.unicore.classevent_service.dto.response;

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
public class QueryResponse {
    private String id;
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
    private boolean allowMultiple;
    @JsonProperty("allow_suggestion")
    private boolean allowSuggestion;

    private List<QueryOption> options;
    
}
