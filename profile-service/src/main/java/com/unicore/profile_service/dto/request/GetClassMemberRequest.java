package com.unicore.profile_service.dto.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetClassMemberRequest {
    private String leaderCode;
    private List<String> studentCodes;
}
