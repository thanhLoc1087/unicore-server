package com.unicore.classroom_service.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.unicore.classroom_service.dto.request.GetByClassRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomFilterResponse {
    private List<ClassroomResponse> classes = new ArrayList<>();
    private List<GetByClassRequest> noStudentLists = new ArrayList<>();
    private List<GetByClassRequest> noMidterm = new ArrayList<>();
    private List<GetByClassRequest> noFinal = new ArrayList<>();
    private List<GetByClassRequest> noThesisCouncil = new ArrayList<>();
    private List<GetByClassRequest> noInternCouncil = new ArrayList<>();
}
