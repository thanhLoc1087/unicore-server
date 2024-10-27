package com.unicore.organization_service.dto.request;

import com.unicore.organization_service.enums.ExamFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectCreationRequest {
    private String organizationId;
    private String name;
    private String code;

    private String description;

    private ExamFormat midtermFormat;
    private ExamFormat practicalFormat;
    private ExamFormat finalFormat;

    private int courseworkWeight;
    private int midtermWeight;
    private int practicalWeight;
    private int finalWeight;

    private int semester;
    private int year;
}
