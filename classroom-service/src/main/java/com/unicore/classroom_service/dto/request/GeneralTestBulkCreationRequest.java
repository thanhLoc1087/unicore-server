package com.unicore.classroom_service.dto.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeneralTestBulkCreationRequest {
        private List<GeneralTestCreationRequest> requests;    
}
