package com.unicore.classevent_service.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Query {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean allowMultiple;
    private boolean allowSuggestion;

    private List<QueryOption> options;
}