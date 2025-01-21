package com.unicore.classroom_service.dto.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddForeignStudentsRequest {
    private String classId; 
    private String subclassCode; 
    private List<String> foreignStudentCodes;
}
