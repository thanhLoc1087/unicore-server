package com.unicore.classroom_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateClassImportStatusRequest {
    private String classCode;
    private int semester;
    private int year;

    private boolean midtermImported;
    private boolean finalImported;
    private boolean councilImported;
}
