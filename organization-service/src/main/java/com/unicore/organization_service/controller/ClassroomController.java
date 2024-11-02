package com.unicore.organization_service.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unicore.organization_service.dto.request.MemberBulkDeletionRequest;
import com.unicore.organization_service.dto.request.ClassBulkCreationRequest;
import com.unicore.organization_service.dto.request.ClassCreationRequest;
import com.unicore.organization_service.dto.response.ClassroomResponse;
import com.unicore.organization_service.service.ClassroomService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/classrooms")
public class ClassroomController {
    private final ClassroomService classroomService;

    @PostMapping
    public Mono<ClassroomResponse> createClassroom(@RequestBody @Valid ClassCreationRequest request) {
        log.info("LOCLOCLOC");
        return classroomService.createClassroom(request);
    }
    
    @PostMapping("/bulk")
    public Flux<ClassroomResponse> createClassrooms(@RequestBody @Valid ClassBulkCreationRequest request) {
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

    // @DeleteMapping("/bulk")
    // public Mono<Void> deleteClassroomsBulk(MemberBulkDeletionRequest request) {
    //     return classroomService.deleteByIds(request.getIds());
    // }
}
