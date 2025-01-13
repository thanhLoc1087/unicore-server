package com.unicore.classevent_service.entity;

import java.util.List;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Group {
    @Id
    private String id;

    @JsonProperty("grouping_id")
    private String groupingId;

    private String index;
    private String name;

    private List<StudentInGroup> members;

    public boolean hasMember(String memberCode) {
        for (StudentInGroup member: members) {
            if (member.getStudentCode().equals(memberCode))
                return true;
        }
        return false;
    }
}
