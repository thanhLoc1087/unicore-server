package com.unicore.classroom_service.dto.request;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddStudentsToListRequest {
    private String classId;
    private String subclassCode;
    private Set<String> studentCodes;
}
