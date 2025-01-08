package com.unicore.classroom_service.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.unicore.classroom_service.dto.request.AddStudentsToListRequest;
import com.unicore.classroom_service.dto.request.ClassFilterRequest;
import com.unicore.classroom_service.dto.request.ClassroomBulkCreationRequest;
import com.unicore.classroom_service.dto.request.GetByClassRequest;
import com.unicore.classroom_service.dto.request.GetClassBySemesterAndYearRequest;
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
                LocalDateTime.now()
            ));
    }
    
    @GetMapping
    public Mono<ApiResponse<List<ClassroomResponse>>> getClassrooms() {
        return classroomService.getClassrooms()
            .collectList()
            .map(response -> new ApiResponse<>(
                HttpStatus.OK.toString(), 
                "Success", 
                response, 
                LocalDateTime.now()
            ));
    }

    @GetMapping("/{id}")
    public Mono<ApiResponse<ClassroomResponse>> getClassroomById(@PathVariable String id) {
        return classroomService.getClassroomById(id)
            .map(response -> new ApiResponse<>(
                HttpStatus.OK.toString(), 
                "Success", 
                response, 
                LocalDateTime.now()
            ));
    }

    @GetMapping("/filter")
    public Mono<ApiResponse<List<ClassroomResponse>>> filterClassrooms(@RequestBody ClassFilterRequest request) {
        return classroomService.filterClassrooms(request)
            .collectList()
            .map(response -> new ApiResponse<>(
                HttpStatus.OK.toString(), 
                "Success", 
                response, 
                LocalDateTime.now()
            ));
    }

    @PostMapping("/code")
    public Mono<ApiResponse<ClassroomResponse>> getClassroomByCode(@RequestBody GetClassBySemesterAndYearRequest request) {
        log.info(request.toString());
        return classroomService.getClassroomByCodeSemesterYear(request)
            .map(response -> new ApiResponse<>(
                HttpStatus.OK.toString(), 
                "Success", 
                response, 
                LocalDateTime.now()
            ));
    }

    @PostMapping("/students")
    public Mono<ApiResponse<List<StudentListResponse>>> addStudentLists(@RequestBody List<StudentListCreationRequest> requests) {
        return studentListService.createStudentListBulk(requests)
            .collectList()
            .map(response -> new ApiResponse<>(
                HttpStatus.OK.toString(), 
                "Success", 
                response,
                LocalDateTime.now()
            ));
    }

    @PostMapping("/students/add")
    public Mono<ApiResponse<StudentListResponse>> addStudentsToList(@RequestBody AddStudentsToListRequest request) {
        return studentListService.addStudents(request)
            .map(response -> new ApiResponse<>(
                HttpStatus.OK.toString(), 
                "Success", 
                response,
                LocalDateTime.now()
            ));
    }

    @GetMapping("/students")
    public Mono<ApiResponse<StudentListResponse>> getClassStudentList(@RequestBody GetByClassRequest request) {
        return studentListService.getStudentList(request)
            .map(response -> new ApiResponse<>(
                HttpStatus.OK.toString(), 
                "Success", 
                response,
                LocalDateTime.now()
            ));
    }

    @GetMapping("/students/{studentCode}")
    public Mono<ApiResponse<List<ClassroomResponse>>> getStudentClasses(@PathVariable String studentCode) {
        return studentListService.getStudentClasses(studentCode)
            .map(response -> new ApiResponse<>(
                HttpStatus.OK.toString(), 
                "Success", 
                response,
                LocalDateTime.now()
            ));
    }
    
}