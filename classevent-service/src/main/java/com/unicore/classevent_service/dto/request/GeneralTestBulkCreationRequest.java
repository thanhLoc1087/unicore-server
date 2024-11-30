package com.unicore.classevent_service.dto.request;

import java.util.List;

import lombok.Data;

@Data
public class GeneralTestBulkCreationRequest {
    private List<GeneralTestCreationRequest> requests;
}
