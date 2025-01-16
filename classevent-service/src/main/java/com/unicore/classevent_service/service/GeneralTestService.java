package com.unicore.classevent_service.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.unicore.classevent_service.dto.request.BulkUpdateTestRequest;
import com.unicore.classevent_service.dto.request.CentralizedTestRequest;
import com.unicore.classevent_service.dto.request.GeneralTestBulkCreationRequest;
import com.unicore.classevent_service.dto.request.GeneralTestUpdateRequest;
import com.unicore.classevent_service.dto.request.GetByClassRequest;
import com.unicore.classevent_service.dto.request.GetClassBySemesterAndYearRequest;
import com.unicore.classevent_service.dto.request.GetTestForBulkUpdateParams;
import com.unicore.classevent_service.dto.response.ApiResponse;
import com.unicore.classevent_service.dto.response.BaseEventResponse;
import com.unicore.classevent_service.dto.response.ClassroomResponse;
import com.unicore.classevent_service.dto.response.GeneralTestResponse;
import com.unicore.classevent_service.dto.response.UpdateClassImportStatusRequest;
import com.unicore.classevent_service.entity.GeneralTest;
import com.unicore.classevent_service.entity.Project;
import com.unicore.classevent_service.entity.Subclass;
import com.unicore.classevent_service.enums.EventType;
import com.unicore.classevent_service.enums.ExamFormat;
import com.unicore.classevent_service.enums.WeightType;
import com.unicore.classevent_service.exception.DataNotFoundException;
import com.unicore.classevent_service.mapper.GeneralTestMapper;
import com.unicore.classevent_service.repository.BaseEventRepository;
import com.unicore.classevent_service.repository.httpclient.ClassroomClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeneralTestService {
    private final BaseEventRepository repository;
    private final GeneralTestMapper mapper;

    private final ProjectService projectService;

    private final ClassroomClient classroomClient;

    /// Tạo bulk
    public Flux<BaseEventResponse> createBulk(GeneralTestBulkCreationRequest request) {
        return Flux.fromIterable(request.getRequests())
            .flatMap(item -> {
                if (item.getFormat().isProject()) {
                    log.info("Project create");
                    Project project = Project.builder()    
                        .classId(item.getClassId())
                        .subclassCode(item.getSubclassCode())
                        .name("Đồ án")
                        .description("Đồ án")
                        .inGroup(true)
                        .weightType(item.getWeightType())
                        .weight(item.getWeight())
                        .fixedWeight(false)
                        .allowGradeReview(true)
                        .autocreated(true)
                        .reviewTimes(3)
                        .allowTopicSuggestion(ExamFormat.DO_AN_TOT_NGHIEP.equals(item.getFormat()))
                        .build();
                    return projectService.saveProject(project);
                } else {
                    log.info("TEst create");
                    String testName = "Thi tập trung ";
                    if (item.getWeightType() == WeightType.FINAL_TERM) {
                        testName += "Cuối kỳ";
                    } else if (item.getWeightType() == WeightType.MIDTERM) {
                        testName += "Giữa kỳ";
                    } else {
                        testName += "Thực hành";
                    }
                    GeneralTest test = GeneralTest.builder()
                        .classId(item.getClassId())
                        .name(testName)
                        .subclassCode(item.getSubclassCode())
                        .weightType(item.getWeightType())
                        .weight(item.getWeight())
                        .fixedWeight(false)
                        .createdBy("LOC")
                        .createdDate(Date.from(Instant.now()))
                        .allowGradeReview(true)
                        .autocreated(true)
                        .inGroup(false)
                        .reviewTimes(3)
                        .build();
                    
                    return repository.save(test);
                }
            })
            .map(response -> {
                if (response instanceof GeneralTest test)
                    return mapper.toResponse(test);
                else 
                    return (BaseEventResponse) response;
            });
    }

    /// Edit
    public Mono<GeneralTestResponse> editTest(String id, GeneralTestUpdateRequest request) {
        return repository.findById(id)
            .map(test -> {
                if (test instanceof GeneralTest generalTest) {
                    generalTest.setPlace(request.getPlace());
                    generalTest.setDate(request.getTime());
                }
                test.setName(request.getName());
                test.setDescription(request.getDescription());
                test.setAllowGradeReview(request.isAllowGradeReview());
                test.setReviewTimes(request.getReviewTimes());
                test.setModifiedBy("LOC");
                test.setModifiedDate(Date.from(Instant.now()));
                return test;
            })
            .flatMap(repository::save)
            .map(test -> mapper.toResponse((GeneralTest) test))
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
        }
        
    /// Lấy theo lớp
    public Flux<GeneralTestResponse> getByClass(GetByClassRequest request) {
        log.info(request.toString());
        return repository.findAllByClassIdAndSubclassCodeAndType(request.getClassId(), request.getSubclassCode(), EventType.TEST)
        .map(test -> mapper.toResponse((GeneralTest) test))
        .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }

    public Mono<GeneralTestResponse> getById(String id) {
        log.info("Get test with id: " + id);
        return repository.findById(id)
            .map(test -> mapper.toResponse((GeneralTest) test))
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }

    public Flux<GeneralTestResponse> updateBulk(CentralizedTestRequest request) {
        return Flux.fromIterable(request.getSchedules())
            .flatMap(schedule -> 
                classroomClient.getClassroomByCode(
                    new GetClassBySemesterAndYearRequest(schedule.getClassCode(), schedule.getSemester(), schedule.getYear())
                )
            ).flatMap(response -> {
                log.info("YUH 0" + response.toString());
                ClassroomResponse classroom = response.getData();
                UpdateClassImportStatusRequest updateRequest = new UpdateClassImportStatusRequest(
                    classroom.getCode(),
                    classroom.getSemester(),
                    classroom.getYear(),
                    request.isMidterm(),
                    !request.isMidterm(),
                    false
                );
                for (Subclass subclass : classroom.getSubclasses()) {
                    if (subclass.getType().isMainClass()) {
                        updateRequest.setCouncilImported(subclass.isCouncilImported());
                        if (request.isMidterm()) {
                            updateRequest.setFinalImported(subclass.isFinalImported());
                        } else {
                            updateRequest.setMidtermImported(subclass.isMidtermImported());
                        }
                        break;
                    }
                }
                return classroomClient.updateClassroomImportStatus(updateRequest);
            })
            .map(ApiResponse::getData)
            .collectList()
            .map(classes -> {
                log.info("YUH 1" + classes.toString());
                List<GetTestForBulkUpdateParams> temp = new ArrayList<>();
                for (ClassroomResponse classroom : classes) {
                    temp.add(new GetTestForBulkUpdateParams(
                        classroom.getId(), classroom.getCode(), request.isMidterm() ? WeightType.MIDTERM : WeightType.FINAL_TERM
                    ));
                }
                return temp;
            })
            .flatMapMany(Flux::fromIterable)
            .flatMap(param -> repository.findAllByClassIdAndSubclassCodeAndWeightTypeAndAutocreatedTrue(
                param.getClassId(), param.getSubclassCode(), param.getWeightType()))
            .collectList()
            .map(tests -> {
                log.info("YUH 2" + tests.toString());
                for (int i = 0; i < tests.size(); i++) {
                    BulkUpdateTestRequest singleRequest = request.getSchedules().get(i);
                    if (tests.get(i) instanceof GeneralTest generalTest) {
                        generalTest.setDate(singleRequest.getDate());
                        generalTest.setPlace(singleRequest.getRoom());
                        generalTest.setDescription(singleRequest.genDescription());
                        generalTest.setModifiedDate(Date.from(Instant.now()));
                        generalTest.setModifiedBy("LOC");
                    }
                }
                return tests;
            })
            .flatMapMany(Flux::fromIterable)
            .flatMap(repository::save)
            .map(test -> mapper.toResponse((GeneralTest) test))
            .doOnError(e-> log.error("GeneralTest - updateBulk: Error.", e));
    }
}
