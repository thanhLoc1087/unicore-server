package com.unicore.profile_service.dto.request;

import java.util.List;

import lombok.Data;

@Data
public class GetClassMemberRequest {
    private String leaderCode;
    private List<String> studentCodes;
}
