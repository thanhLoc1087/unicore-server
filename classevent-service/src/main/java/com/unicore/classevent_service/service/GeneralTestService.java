package com.unicore.classevent_service.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.aggregation.ArrayOperators.In;
import org.springframework.stereotype.Service;

import com.unicore.classevent_service.dto.request.BulkUpdateTestRequest;
import com.unicore.classevent_service.dto.request.CentralizedTestRequest;
import com.unicore.classevent_service.dto.request.GeneralTestBulkCreationRequest;
import com.unicore.classevent_service.dto.request.GeneralTestUpdateRequest;
import com.unicore.classevent_service.dto.request.GetByClassRequest;
import com.unicore.classevent_service.dto.request.GetClassBySemesterAndYearRequest;
import com.unicore.classevent_service.dto.request.GetTestForBulkUpdateParams;
import com.unicore.classevent_service.dto.response.BaseEventResponse;
import com.unicore.classevent_service.dto.response.ClassroomResponse;
import com.unicore.classevent_service.dto.response.GeneralTestResponse;
import com.unicore.classevent_service.entity.GeneralTest;
import com.unicore.classevent_service.entity.Project;
import com.unicore.classevent_service.enums.ExamFormat;
import com.unicore.classevent_service.enums.WeightType;
import com.unicore.classevent_service.exception.DataNotFoundException;
import com.unicore.classevent_service.mapper.GeneralTestMapper;
import com.unicore.classevent_service.repository.GeneralTestRepository;
import com.unicore.classevent_service.repository.httpclient.ClassroomClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeneralTestService {
    private final GeneralTestRepository repository;
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
                    GeneralTest test = GeneralTest.builder()
                        .classId(item.getClassId())
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
                test.setPlace(request.getPlace());
                test.setDate(request.getTime());
                test.setName(request.getName());
                test.setDescription(request.getDescription());
                test.setAllowGradeReview(request.isAllowGradeReview());
                test.setReviewTimes(request.getReviewTimes());
                test.setModifiedBy("LOC");
                test.setModifiedDate(Date.from(Instant.now()));
                return test;
            })
            .flatMap(repository::save)
            .map(mapper::toResponse)
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }

    /// Lấy theo lớp
    public Flux<GeneralTestResponse> getByClass(GetByClassRequest request) {
        return repository.findAllByClassIdAndSubclassCode(request.getClassId(), request.getSubclassCode())
            .map(mapper::toResponse)
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }

    public Mono<GeneralTestResponse> getById(String id) {
        log.info("Get test with id: " + id);
        return repository.findById(id)
            .map(mapper::toResponse)
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }

    public Flux<GeneralTestResponse> updateBulk(CentralizedTestRequest request) {
        return Flux.fromIterable(request.getSchedules())
            .flatMap(schedule -> 
                classroomClient.getClassroomByCode(
                    new GetClassBySemesterAndYearRequest(schedule.getClassCode(), schedule.getSemester(), schedule.getYear())
                )
            ).map(response -> {
                log.info("YUH 0" + response.toString());
                return response.getData();
            })
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
                    tests.get(i).setDate(singleRequest.getDate());
                    tests.get(i).setPlace(singleRequest.getRoom());
                    tests.get(i).setDescription(singleRequest.genDescription());
                    tests.get(i).setModifiedDate(Date.from(Instant.now()));
                    tests.get(i).setModifiedBy("LOC");
                }
                return tests;
            })
            .flatMapMany(Flux::fromIterable)
            .flatMap(repository::save)
            .map(mapper::toResponse)
            .doOnError(e-> log.error("GeneralTest - updateBulk: Error.", e));
    }
}
