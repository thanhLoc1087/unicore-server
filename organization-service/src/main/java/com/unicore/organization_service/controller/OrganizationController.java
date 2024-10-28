package com.unicore.organization_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unicore.organization_service.dto.request.OrganizationCreationRequest;
import com.unicore.organization_service.dto.response.OrganizationResponse;
import com.unicore.organization_service.dto.response.StaffResponse;
import com.unicore.organization_service.dto.response.StudentResponse;
import com.unicore.organization_service.dto.response.SubjectResponse;
import com.unicore.organization_service.dto.response.TeacherResponse;
import com.unicore.organization_service.service.OrganizationService;
import com.unicore.organization_service.service.StaffService;
import com.unicore.organization_service.service.StudentService;
import com.unicore.organization_service.service.SubjectService;
import com.unicore.organization_service.service.TeacherService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/manage")
@RequiredArgsConstructor
@Slf4j
public class OrganizationController {
    private final OrganizationService organizationService;
    private final SubjectService subjectService;
    private final TeacherService teacherService;
    private final StudentService studentService;
    private final StaffService staffService;

    @PostMapping
    public ResponseEntity<Mono<OrganizationResponse>> createOrg(@RequestBody OrganizationCreationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(organizationService.createOrg(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mono<OrganizationResponse>> getOrgById(@PathVariable String id) {
        return ResponseEntity.ok(organizationService.getOrgById(id));
    }

    @GetMapping
    public ResponseEntity<Flux<OrganizationResponse>> getAllOrgs() {
        return ResponseEntity.ok(organizationService.getOrgs());
    }

    @GetMapping("/{id}/subjects")
    public ResponseEntity<Flux<SubjectResponse>> getOrgSubjects(@PathVariable String id) {
        return ResponseEntity.ok(subjectService.getSubjectsByOrg(id));
    }   

    @GetMapping("/{id}/teachers")
    public ResponseEntity<Flux<TeacherResponse>> getOrgTeachers(@PathVariable String id) {
        return ResponseEntity.ok(teacherService.getTeachersByOrganizationId(id));
    }   

    @GetMapping("/{id}/student")
    public ResponseEntity<Flux<StudentResponse>> getOrgStudeints(@PathVariable String id) {
        return ResponseEntity.ok(studentService.getStudentsByOrganizationId(id));
    }   

    @GetMapping("/{id}/staff")
    public ResponseEntity<Flux<StaffResponse>> getOrgStaff(@PathVariable String id) {
        return ResponseEntity.ok(staffService.getStaffsByOrganizationId(id));
    }   
}
