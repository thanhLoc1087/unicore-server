package com.unicore.classevent_service.dto.request;

import java.util.List;

import com.unicore.classevent_service.entity.StudentInGroup;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectGroupRequest {

    private String name;

    private List<StudentInGroup> members;
    
}
