package com.unicore.classroom_service.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unicore.classroom_service.dto.request.AddForeignStudentsRequest;
import com.unicore.classroom_service.dto.request.StudentGroupingAddGroupRequest;
import com.unicore.classroom_service.dto.request.StudentGroupingCreationRequest;
import com.unicore.classroom_service.dto.request.GetByClassRequest;
import com.unicore.classroom_service.dto.request.StudentGroupingUpdateRequest;
import com.unicore.classroom_service.dto.response.StudentGroupingResponse;
import com.unicore.classroom_service.entity.StudentInGroup;
import com.unicore.classroom_service.exception.AppException;
import com.unicore.classroom_service.exception.ErrorCode;
import com.unicore.classroom_service.mapper.StudentGroupingMapper;
import com.unicore.classroom_service.repository.StudentGroupingRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentGroupingService {
    private final StudentGroupingRepository studentGroupingRepository;
    private StudentGroupingMapper studentGroupingMapper = new StudentGroupingMapper();
    private final ClassroomService classroomService;
    private final StudentListService studentListService;

    public Mono<StudentGroupingResponse> getGrouping(GetByClassRequest request) {
        log.info(request.toString());
        return studentGroupingRepository.findByClassIdAndSubclassCode(
                request.getClassId(), 
                request.getSubclassCode()
            )
            .map(studentGroupingMapper::toGroupingResponse)
            .switchIfEmpty(Mono.error(new AppException(ErrorCode.NOT_FOUND)));
    }

    @Transactional
    public Mono<StudentGroupingResponse> createStudentGrouping(StudentGroupingCreationRequest request) {
        return checkDuplicate(request)
            .flatMap(result -> Boolean.TRUE.equals(result) ? 
                Mono.error(new AppException(ErrorCode.DUPLICATE)) :
                Mono.just(request)
                    .map(studentGroupingMapper::toGrouping)
                    .flatMap(studentGroupingRepository::save)
                    .flatMap(response -> {
                        if (request.isCreateSubclass() && !request.getGroups().isEmpty()) {
                            classroomService.createSubclassFromGrouping(request).subscribe();
                        }
                        return Mono.just(response);
                    })
                    .map(studentGroupingMapper::toGroupingResponse)
            );
    }

    public Mono<StudentGroupingResponse> updateStudentGrouping(StudentGroupingUpdateRequest request) {
        return studentGroupingRepository.findById(request.getId())
            .map(studentGrouping -> studentGroupingMapper.toStudentGrouping(studentGrouping, request))
            .flatMap(studentGroupingRepository::save)
            .map(studentGroupingMapper::toGroupingResponse);
    }

    public Mono<StudentGroupingResponse> addGroupToStudentGrouping(StudentGroupingAddGroupRequest request) {
        List<StudentInGroup> groupStudents = request.getGroup().getMembers();
        for (StudentInGroup student : groupStudents) {
            student.setGroupName(request.getGroup().getName());
        }
        return studentGroupingRepository.findByClassIdAndSubclassCode(request.getClassId(), request.getSubclassCode())
            .flatMap(grouping -> {
                // Collect existing student codes from the grouping
                Set<String> oldStudentCodes = grouping.getGroups().stream()
                    .flatMap(group -> group.getMembers().stream())
                    .map(StudentInGroup::getStudentCode)
                    .collect(Collectors.toSet());
    
                // Filter members that are not in oldStudentCodes (foreign students)
                List<StudentInGroup> foreignStudents = request.getGroup().getMembers().stream()
                    .filter(student -> !oldStudentCodes.contains(student.getStudentCode()))
                    .collect(Collectors.toList());
    
                // If there are foreign students, add them to the external list
                return (foreignStudents.isEmpty()
                    ? Mono.just(grouping) // No foreign students to add
                    : studentListService.addForeignStudents(new AddForeignStudentsRequest(
                        grouping.getClassId(),
                        grouping.getSubclassCode(),
                        foreignStudents
                    )).thenReturn(grouping)) // Add foreign students and proceed
                    .flatMap(updatedGrouping -> {
                        // Add the new group to the grouping and save
                        updatedGrouping.getGroups().add(request.getGroup());
                        return studentGroupingRepository.save(updatedGrouping);
                    });
            })
            .map(studentGroupingMapper::toGroupingResponse);
    }
    

    private Mono<Boolean> checkDuplicate(StudentGroupingCreationRequest request) {
        return studentGroupingRepository.findByClassIdAndSubclassCode(request.getClassId(), request.getSubclassCode())
            .hasElement();
    }
}
