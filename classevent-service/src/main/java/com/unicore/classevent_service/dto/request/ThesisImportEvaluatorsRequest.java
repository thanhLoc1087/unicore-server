package com.unicore.classevent_service.dto.request;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThesisImportEvaluatorsRequest {
    private String projectId;
    private List<ThesisEvaluatorRequest> evaluators = new ArrayList<>();
}
