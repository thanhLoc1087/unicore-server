package com.unicore.classroom_service.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Group {
    private String id;

    @JsonProperty("grouping_id")
    private String groupingId;

    private Integer index;
    private String name;

    private String leaderCode;
    private List<StudentInGroup> members;

    public boolean hasMember(String memberCode) {
        for (StudentInGroup member: members) {
            if (member.getStudentCode().equals(memberCode))
                return true;
        }
        return false;
    }
}