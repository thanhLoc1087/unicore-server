package com.unicore.classroom_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetSubjectByYearAndSemesterRequest {
    private int semester;
    private int year;
}
