package com.unicore.classroom_service.dto.request;

import com.unicore.classroom_service.entity.Group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentGroupingAddGroupRequest {
    String classId;
    String subclassCode;
    Group group;
}
