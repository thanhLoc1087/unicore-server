package com.unicore.classroom_service.dto.response;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubjectsAndTeachersForClassCreation {
    private Map<String, SubjectResponse> subjects;
    private Map<String, TeacherResponse> teachers;
}
