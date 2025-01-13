package com.unicore.classevent_service.dto;

import java.util.List;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unicore.classevent_service.entity.StudentInGroup;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupRequest {
    @NonNull
    @JsonProperty("grouping_id")
    private String groupingId;

    private String name;

    private List<StudentInGroup> members;
}
