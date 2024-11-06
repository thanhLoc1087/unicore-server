package com.unicore.classroom_service.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unicore.classroom_service.dto.request.ClassroomBulkCreationRequest;
import com.unicore.classroom_service.dto.request.ClassroomCreationRequest;
import com.unicore.classroom_service.dto.request.StudentListCreationRequest;
import com.unicore.classroom_service.dto.response.ClassroomResponse;
import com.unicore.classroom_service.entity.Classroom;
import com.unicore.classroom_service.entity.Subclass;
import com.unicore.classroom_service.exception.AppException;
import com.unicore.classroom_service.exception.ErrorCode;
import com.unicore.classroom_service.mapper.ClassroomMapper;
import com.unicore.classroom_service.repository.ClassroomRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClassroomService {
    private final ClassroomRepository classroomRepository;
    private final ClassroomMapper classroomMapper;

    public Mono<ClassroomResponse> createClassroom(Classroom classroom) {
        return Mono.just(classroom)
            .flatMap(newClass -> checkDuplicate(newClass.getCode(), newClass.getSemester(), newClass.getYear()))
            .flatMap(result -> Boolean.TRUE.equals(result) ? 
                Mono.error(new AppException(ErrorCode.DUPLICATE)) :
                saveClassroom(classroom)
            );
    }

    private Mono<ClassroomResponse> saveClassroom(Classroom classroom) {
        return Mono.just(classroom)
            .flatMap(classroomRepository::save)
            .map(classroomMapper::toClassroomResponse)
            .onErrorResume(throwable -> {
                if (throwable instanceof DataAccessException) {
                    log.error("R2DBC data invalidation error: {}", throwable.getCause());
                    // Return an error response or rethrow as a custom exception
                    return Mono.error(new AppException(ErrorCode.DUPLICATE));
                }
                return Mono.error(throwable); // Propagate other exceptions
            })
            .doOnSuccess(response -> {
                // goi kafka tao lop, event
            });
    }

    @Transactional
    public Flux<ClassroomResponse> createClassrooms(ClassroomBulkCreationRequest request) {
        if (request.getClasses().isEmpty()) return Flux.empty();
        List<Classroom> classes = new ArrayList<>();
        List<ClassroomCreationRequest> classRequests = request.getClasses();
        String currentClassCode = classRequests.get(0).getCode();
        Set<Subclass> subclasses = new HashSet<>();
        for (var i = 0; i < classRequests.size(); i++) {
            ClassroomCreationRequest currentSubclass = classRequests.get(i);
            subclasses.add(classroomMapper.toSubclass(currentSubclass));
            if (i + 1 < classRequests.size()) {
                ClassroomCreationRequest nextSubclass = classRequests.get(i + 1);
                if (!nextSubclass.getCode().startsWith(currentClassCode)) {
                    classes.add(buildClassroom(currentClassCode, subclasses, currentSubclass, request.getOrganizationId()));
                    subclasses = new HashSet<>();
                    currentClassCode = nextSubclass.getCode();
                }
            } else {
                classes.add(buildClassroom(currentClassCode, subclasses, currentSubclass, request.getOrganizationId()));
            }
        }
        return Flux.fromIterable(classes)
            .flatMap(this::createClassroom);
    }
    
    private Classroom buildClassroom(String code, Set<Subclass> subclasses, ClassroomCreationRequest classRequest, String organizationId) {
        return Classroom.builder()
            .code(code)
            .subclasses(List.copyOf(subclasses))
            .subjectCode(classRequest.getSubjectCode())
            .orgManaged(classRequest.isOrgManaged())
            .organizationId(organizationId)
            .year(classRequest.getYear())
            .semester(classRequest.getSemester())
            .build();
    }

    public Mono<ClassroomResponse> updateClassSize(StudentListCreationRequest request) {
        return classroomRepository.findById(request.getClassId())
            .map(classroom -> {
                for (Subclass subclass : classroom.getSubclasses()) {
                    if (subclass.getCode().equals(request.getSubclassCode())) {
                        subclass.setCurrentSize(request.getStudentCodes().size());
                        break;
                    }
                }
                return classroomMapper.toClassroomResponse(classroom);
            });
    }

    public Mono<ClassroomResponse> getClassroomById(String id) {
        return classroomRepository.findById(id)
            .map(classroomMapper::toClassroomResponse);
    }

    public Mono<ClassroomResponse> getClassroomByCode(String code) {
        return classroomRepository.findByCode(code)
            .map(classroomMapper::toClassroomResponse);
    }

    public Flux<ClassroomResponse> getClassrooms() {
        return classroomRepository.findAll()
            .map(classroomMapper::toClassroomResponse);
    }

    public Flux<ClassroomResponse> getClassroomsByOrganizationId(String organizationId) {
        return classroomRepository.findByOrganizationId(organizationId)
            .map(classroomMapper::toClassroomResponse);
    }

    public Mono<Void> deleteById(String id) {
        return classroomRepository.deleteById(id);
    }

    public Mono<Void> deleteByIds(List<String> ids) {
        return classroomRepository.deleteAllById(ids);
    }

    private Mono<Boolean> checkDuplicate(String code, int semester, int year) {
        return classroomRepository.findByCodeAndSemesterAndYear(code, semester, year)
            .hasElement();
    }
}
