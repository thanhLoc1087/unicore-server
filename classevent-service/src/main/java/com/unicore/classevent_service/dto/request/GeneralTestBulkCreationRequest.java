package com.unicore.classevent_service.dto.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneralTestBulkCreationRequest {
    private List<GeneralTestCreationRequest> requests;
}
