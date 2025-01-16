package com.unicore.classevent_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateClassImportStatusRequest {
    private String classCode;
    private int semester;
    private int year;

    private boolean midtermImported;
    private boolean finalImported;
    private boolean councilImported;
}