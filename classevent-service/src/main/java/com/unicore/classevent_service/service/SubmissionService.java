package com.unicore.classevent_service.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.unicore.classevent_service.dto.request.GetByClassRequest;
import com.unicore.classevent_service.dto.request.GetClassGradeRequest;
import com.unicore.classevent_service.dto.request.GetGroupingRequest;
import com.unicore.classevent_service.dto.request.GetStudentClassGradeRequest;
import com.unicore.classevent_service.dto.request.SubmissionCreationRequest;
import com.unicore.classevent_service.dto.request.SubmissionFeedbackRequest;
import com.unicore.classevent_service.dto.response.EventSubmissionResponse;
import com.unicore.classevent_service.dto.response.StudentClassGradeDetailByType;
import com.unicore.classevent_service.dto.response.StudentClassGradeResponse;
import com.unicore.classevent_service.dto.response.StudentGradeDetail;
import com.unicore.classevent_service.dto.response.StudentResponse;
import com.unicore.classevent_service.dto.response.SubmissionResponse;
import com.unicore.classevent_service.entity.BaseEvent;
import com.unicore.classevent_service.entity.Group;
import com.unicore.classevent_service.entity.StudentInSubmission;
import com.unicore.classevent_service.entity.Submission;
import com.unicore.classevent_service.enums.WeightType;
import com.unicore.classevent_service.exception.DataNotFoundException;
import com.unicore.classevent_service.mapper.SubmissionMapper;
import com.unicore.classevent_service.repository.BaseEventRepository;
import com.unicore.classevent_service.repository.SubmissionRepository;
import com.unicore.classevent_service.repository.httpclient.ProfileClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubmissionService {
    private final SubmissionRepository repository;
    private final SubmissionMapper mapper;

    private final ProfileClient profileClient;

    private final BaseEventRepository baseEventRepository;

    private final EventGroupingService eventGroupingService;

    // Nộp bài
    public Mono<SubmissionResponse> createSubmission(SubmissionCreationRequest request) {
        Submission submission = mapper.toSubmission(request);
        return Mono.just(request)
            .flatMap(creationRequest -> 
                baseEventRepository.findById(creationRequest.getEventId())
            )
            .flatMap((BaseEvent event) -> {
                submission.setInGroup(event.isInGroup());
                submission.setCreatedDate(LocalDateTime.now());
                submission.setSubmitters(List.of(
                    StudentInSubmission.fromStudentInGroup(request.getSubmitter())
                ));
                if (event.isInGroup()) {
                    return eventGroupingService.getGrouping(
                        new GetGroupingRequest(request.getEventId(), request.getClassId(), request.getSubclassCode()))
                        .flatMap(eventGrouping -> {
                            for (Group group : eventGrouping.getGroups()) {
                                if (group.hasMember(request.getSubmitter().getStudentCode())) {
                                    submission.setSubmitters(
                                        group.getMembers().stream().map(StudentInSubmission::fromStudentInGroup).toList()
                                    );
                                }
                            }
                            if (submission.getSubmitters().isEmpty()) {
                                submission.setSubmitters(List.of(
                                    StudentInSubmission.fromStudentInGroup(request.getSubmitter())
                                ));
                            }
                            submission.setInGroup(event.isInGroup());
                            submission.setCreatedDate(LocalDateTime.now());
                            return Mono.just(submission);
                        });
                }
                submission.setSubmitTimeStatus(submission.calculateSubmissionTime(event.getEndDate()));
                return Mono.just(submission);
            })
            .flatMap(repository::save)
            .map(mapper::toSubmissionResponse);
    }

    // Chấm điểm
    public Mono<SubmissionResponse> addFeedback(String id, SubmissionFeedbackRequest request) {
        return repository.findById(id)
            .map(submission -> {
                submission.setFeedback(request.getFeedback());
                submission.setGrade(request.getGrade());
                if (!request.getMemberGrades().isEmpty()) {
                    for (StudentInSubmission member : submission.getSubmitters()) {
                        member.setGrade(
                            request.getMemberGrades().getOrDefault(member.getStudentCode(), request.getGrade())
                        );
                    }
                }
                submission.setFeedbackDate(LocalDateTime.now());
                return submission;
            })
            .flatMap(repository::save)
            .map(mapper::toSubmissionResponse)
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }

    public Flux<SubmissionResponse> getSubmissionsByEventId(String eventId) {
        return repository.findAllByEventId(eventId)
            .map(mapper::toSubmissionResponse);
    }

    public Mono<SubmissionResponse> getSubmissionById(String id) {
        return repository.findById(id)
            .map(mapper::toSubmissionResponse)
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }

    // Lấy điểm cả lớp
    public Flux<StudentClassGradeResponse> getStudentClassGrade(GetClassGradeRequest request) {
        return baseEventRepository.findAllByClassId(request.getClassId())
            .collectList()
            .flatMapMany(events -> 
                profileClient.getClassStudents(new GetByClassRequest(request.getClassId(), null))
                    .flatMapMany(classStudents -> {
                        log.info(classStudents.toString());
                        List<StudentResponse> students = new ArrayList<>();
                        if (classStudents.getData().getStudents() != null) {
                            students.addAll(classStudents.getData().getStudents());
                        }
                        if (classStudents.getData().getForeignStudents() != null) {
                            students.addAll(classStudents.getData().getForeignStudents());
                        }
                        return Flux.fromIterable(students);
                    })
                    .flatMap(student -> getStudentEventSubmissions(events, student.getCode(), student))
                    .map(gradeDetail -> {
                        log.info(gradeDetail.toString());
                        StudentClassGradeResponse studentGrade = new StudentClassGradeResponse();
                        studentGrade.setStudentCode(gradeDetail.getStudentCode());
                        studentGrade.setStudentName(gradeDetail.getStudentName());
                        studentGrade.setCoursework(
                            calculateGrade(gradeDetail.getCoursework())
                        );
                        studentGrade.setPractical(
                            calculateGrade(gradeDetail.getPracticals())
                        );
                        studentGrade.setMidterm(
                            calculateGrade(gradeDetail.getMidterms())
                        );
                        studentGrade.setFinalGrade(
                            calculateGrade(gradeDetail.getFinals())
                        );
                        log.info(studentGrade.toString());
                        return studentGrade;
                    })
            );
    }
    
    // Lấy chi tiết điểm 1 hs 1 lớp
    public Mono<StudentGradeDetail> getStudentClassGradeDetail(GetStudentClassGradeRequest request) {
        if (request.getWeightType() != null) {
            return baseEventRepository.findAllByClassIdAndWeightType(request.getClassId(), request.getWeightType())
                .collectList()
                .flatMap(events -> 
                    profileClient.getStudentByCode(request.getStudentCode())
                        .flatMap(student -> getStudentEventSubmissions(events, request.getStudentCode(), student))
                );
        }
        return baseEventRepository.findAllByClassId(request.getClassId())
            .collectList()
            .flatMap(events -> 
                profileClient.getStudentByCode(request.getStudentCode())
                    .flatMap(student -> getStudentEventSubmissions(events, request.getStudentCode(), student))
            );
    }

    private Mono<StudentGradeDetail> getStudentEventSubmissions(
        List<BaseEvent> events, 
        String studentCode,
        StudentResponse student
    ) {
        return Flux.fromIterable(events)
            .flatMap(event ->
                    repository.findAllByEventIdAndSubmittersStudentCode(event.getId(), student.getCode())
                        .map(submission -> new EventSubmissionResponse(studentCode, event, submission))
                        .switchIfEmpty(Mono.just(new EventSubmissionResponse(studentCode, event, null)))
                )
            .collectList()
            .map(submissions -> {
                log.info(submissions.toString());
                StudentGradeDetail details = new StudentGradeDetail();
                details.setStudentCode(student.getCode());
                details.setStudentName(student.getName());
                for (EventSubmissionResponse submission : submissions) {
                    switch (submission.getEvent().getWeightType()) {
                        case WeightType.COURSEWORK:
                            details.getCoursework().add(
                                StudentClassGradeDetailByType.fromEventSubmission(submission)  
                            );
                            break;
                        case WeightType.PRACTICAL:
                            details.getPracticals().add(
                                StudentClassGradeDetailByType.fromEventSubmission(submission)  
                            );
                            break;
                        case WeightType.MIDTERM:
                            details.getMidterms().add(
                                StudentClassGradeDetailByType.fromEventSubmission(submission)  
                            );
                            break;
                        case WeightType.FINAL_TERM:
                            details.getFinals().add(
                                StudentClassGradeDetailByType.fromEventSubmission(submission)  
                            );
                            break;
                        default:
                            break;
                    }
                }
                return details;
            });
    }

    // Tính điểm 
    private Float calculateGrade(List<StudentClassGradeDetailByType> grades) {
        List<StudentClassGradeDetailByType> flexibleWeights = new ArrayList<>();
        Float result = 0f;
        Float totalWeight = 100f;
        for (StudentClassGradeDetailByType grade : grades) {
            if (grade.isFixed()) {
                result += grade.getGrade() * grade.getWeight() / 100f;
                totalWeight -= grade.getWeight();
            } else {
                flexibleWeights.add(grade);
            }
        }
        if (!flexibleWeights.isEmpty() && totalWeight > 0) {
            Float weightForEachFlexible = totalWeight / flexibleWeights.size();
            for (StudentClassGradeDetailByType grade : flexibleWeights) {
                result += grade.getGrade() * weightForEachFlexible / 100f;
            }
        }
        return result;
    }
}
