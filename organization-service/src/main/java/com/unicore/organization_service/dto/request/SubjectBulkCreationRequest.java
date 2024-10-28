package com.unicore.organization_service.dto.request;

import java.util.List;

import lombok.Data;

@Data
public class SubjectBulkCreationRequest {
    private String organizationId;
    private List<SubjectCreationRequest> subjects;
}
