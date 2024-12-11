package com.unicore.classroom_service.controller;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.unicore.classroom_service.dto.request.ClassroomBulkCreationRequest;
import com.unicore.classroom_service.dto.request.StudentListCreationRequest;
import com.unicore.classroom_service.dto.response.ApiResponse;
import com.unicore.classroom_service.dto.response.ClassroomResponse;
import com.unicore.classroom_service.dto.response.StudentListResponse;
import com.unicore.classroom_service.service.ClassroomService;
import com.unicore.classroom_service.service.StudentListService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;


@RestController
@Slf4j
@RequiredArgsConstructor
public class ClassroomController {
    private final ClassroomService classroomService;
    private final StudentListService studentListService;
    
    @PostMapping("/bulk")
    public Mono<ApiResponse<List<ClassroomResponse>>> createClassrooms(@RequestBody ClassroomBulkCreationRequest request) {
        log.info("HELOOOOOOOOO?");
        return classroomService.createClassrooms(request)
            .collectList()
            .map(response -> new ApiResponse<>(
                HttpStatus.OK.toString(), 
                "Success", 
                response, 
                Date.from(Instant.now()))
            );
    }
    
    @GetMapping
    public Mono<ApiResponse<List<ClassroomResponse>>> getClassrooms() {
        return classroomService.getClassrooms()
            .collectList()
            .map(response -> new ApiResponse<>(
                HttpStatus.OK.toString(), 
                "Success", 
                response, 
                Date.from(Instant.now()))
            );
    }

    @GetMapping("/{id}")
    public Mono<ApiResponse<ClassroomResponse>> getClassroomById(@PathVariable String id) {
        return classroomService.getClassroomById(id)
            .map(response -> new ApiResponse<>(
                HttpStatus.OK.toString(), 
                "Success", 
                response, 
                Date.from(Instant.now()))
            );
    }

    @PostMapping("/students")
    public Mono<ApiResponse<List<StudentListResponse>>> addStudentLists(@RequestBody List<StudentListCreationRequest> requests) {
        return studentListService.createStudentListBulk(requests)
            .collectList()
            .map(response -> new ApiResponse<>(
                HttpStatus.OK.toString(), 
                "Success", 
                response, 
                Date.from(Instant.now()))
            );
    }

    @GetMapping("/students/{studentCode}")
    public Mono<ApiResponse<List<ClassroomResponse>>> getStudentClasses(@PathVariable String studentCode) {
        return studentListService.getStudentClasses(studentCode)
            .map(response -> new ApiResponse<>(
                HttpStatus.OK.toString(), 
                "Success", 
                response, 
                Date.from(Instant.now()))
            );
    }
    
}