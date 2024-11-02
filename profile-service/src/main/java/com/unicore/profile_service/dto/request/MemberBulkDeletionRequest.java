package com.unicore.profile_service.dto.request;

import java.util.List;

import lombok.Data;

@Data
public class MemberBulkDeletionRequest {
    private List<String> ids;
}
