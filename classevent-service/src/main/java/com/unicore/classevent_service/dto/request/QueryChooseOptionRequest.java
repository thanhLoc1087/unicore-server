package com.unicore.classevent_service.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryChooseOptionRequest {
    @JsonProperty("query_id")
    private String queryId;
    @JsonProperty("option_id")
    private String optionId;
    private String selector;
    private Boolean adding;
}
