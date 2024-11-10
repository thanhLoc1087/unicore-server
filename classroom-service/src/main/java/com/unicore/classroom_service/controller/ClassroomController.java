package com.unicore.classroom_service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.unicore.classroom_service.dto.request.ClassroomBulkCreationRequest;
import com.unicore.classroom_service.dto.request.StudentListCreationRequest;
import com.unicore.classroom_service.dto.response.ClassroomResponse;
import com.unicore.classroom_service.dto.response.StudentListResponse;
import com.unicore.classroom_service.service.ClassroomService;
import com.unicore.classroom_service.service.StudentListService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@Slf4j
@RequiredArgsConstructor
public class ClassroomController {
    private final ClassroomService classroomService;
    private final StudentListService studentListService;
    
    @PostMapping("/bulk")
    public Flux<ClassroomResponse> createClassrooms(@RequestBody @Valid ClassroomBulkCreationRequest request) {
        log.info(request.toString());
        return classroomService.createClassrooms(request);
    }
    
    @GetMapping
    public Flux<ClassroomResponse> getClassrooms() {
        return classroomService.getClassrooms();
    }

    @GetMapping("/{id}")
    public Mono<ClassroomResponse> getClassroomById(@PathVariable String id) {
        return classroomService.getClassroomById(id);
    }

    @PostMapping("/students")
    public Flux<StudentListResponse> addStudentLists(@RequestBody List<StudentListCreationRequest> requests) {
        return studentListService.createStudentListBulk(requests);
    }

    @GetMapping("/students/{studentCode}")
    public Mono<List<ClassroomResponse>> getStudentClasses(@PathVariable String studentCode) {
        return studentListService.getStudentClasses(studentCode);
    }
    
}