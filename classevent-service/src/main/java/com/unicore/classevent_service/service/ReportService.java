package com.unicore.classevent_service.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.unicore.classevent_service.dto.request.BulkUpdateTestRequest;
import com.unicore.classevent_service.dto.request.CentralizedTestRequest;
import com.unicore.classevent_service.dto.request.GetByClassRequest;
import com.unicore.classevent_service.dto.request.GetByDateRequest;
import com.unicore.classevent_service.dto.request.GetClassBySemesterAndYearRequest;
import com.unicore.classevent_service.dto.request.GetTestForBulkUpdateParams;
import com.unicore.classevent_service.dto.request.QueryChooseOptionRequest;
import com.unicore.classevent_service.dto.request.ReportCreationRequest;
import com.unicore.classevent_service.dto.request.ReportUpdateRequest;
import com.unicore.classevent_service.dto.response.ApiResponse;
import com.unicore.classevent_service.dto.response.ClassroomResponse;
import com.unicore.classevent_service.dto.response.ReportResponse;
import com.unicore.classevent_service.entity.BaseEvent;
import com.unicore.classevent_service.entity.Project;
import com.unicore.classevent_service.entity.QueryOption;
import com.unicore.classevent_service.entity.Report;
import com.unicore.classevent_service.enums.EventType;
import com.unicore.classevent_service.enums.SubmissionOption;
import com.unicore.classevent_service.enums.WeightType;
import com.unicore.classevent_service.exception.DataNotFoundException;
import com.unicore.classevent_service.mapper.ReportMapper;
import com.unicore.classevent_service.repository.BaseEventRepository;
import com.unicore.classevent_service.repository.httpclient.ClassroomClient;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportService {
    private final BaseEventRepository reportRepository;
    private final ReportMapper reportMapper;

    private final BaseEventRepository projectRepository;

    private final ClassroomClient classroomClient;
    
    public Flux<ReportResponse> createReport(ReportCreationRequest request) {
        return Flux.fromIterable(request.getSubclassCodes())
            .map(subclassCode -> {
                Report report = reportMapper.toReport(request);
                report.setSubclassCode(subclassCode);
                report.setCreatedBy("Loc");
                report.setCreatedDate(Date.from(Instant.now()));
                return report;
            })
            .flatMap(this::saveReport);
    }

    private Mono<ReportResponse> saveReport(Report report) {
        return Mono.just(report)
            .map(entity -> {
                entity.setCreatedBy("Loc Update");
                entity.setCreatedDate(Date.from(Instant.now()));
                
                List<QueryOption> options = entity.getQuery().getOptions();
                for (int i = 0; i < options.size(); i++) {
                    options.get(i).setId("" + (1 + i));
                }
                return entity;
            })
            .flatMap(reportRepository::save)
            .map(reportMapper::toReportResponse);
    }

    /// Các hàm get bên dưới cần gọi qua bên submission xem hoàn thành chưa

    public Mono<ReportResponse> getReport(String id) {
        return reportRepository.findById(id)
            .map(report -> reportMapper.toReportResponse((Report) report))
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }

    public Flux<ReportResponse> getClassReports(GetByClassRequest request) {
        return reportRepository.findAllReportsByClassIdAndSubclassCode(
                request.getClassId(), 
                request.getSubclassCode()
            )
            .map(report -> reportMapper.toReportResponse((Report) report))
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }

    public Flux<ReportResponse> getProjectReports(String projectId) {
        return reportRepository.findAllByProjectIdAndType(projectId, EventType.REPORT)
            .map(report -> reportMapper.toReportResponse((Report) report))
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }

    public Flux<ReportResponse> findActiveReports(@Valid GetByDateRequest request) {
        LocalDateTime startDateTime = Optional.ofNullable(request.getDate())
                                            .orElse(LocalDate.now().atStartOfDay());
        LocalDateTime endDateTime = Optional.ofNullable(request.getDate())
                                            .orElse(LocalDate.now().atTime(LocalTime.MAX));

        // Perform the query and map results
        return reportRepository.findActiveEvents(
                    request.getClassId(),
                    request.getSubclassCode(),
                    startDateTime,
                    endDateTime,
                    EventType.REPORT)
                .map(report -> reportMapper.toReportResponse((Report) report))
                .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }

    public Mono<ReportResponse> updateReport(String id, ReportUpdateRequest request) {
        return reportRepository.findById(id)
            .map(report -> reportMapper.toReport((Report) report, request))
            .flatMap(this::saveReport)
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }
    
    public Mono<ReportResponse> chooseOption(String id, QueryChooseOptionRequest request) {
        return reportRepository.findById(id)
            .flatMap(response -> {
                if (response instanceof Report report) {
                    QueryOption matchedOption = report.getQuery().getOptions().stream()
                        .filter(option -> option.getId().equals(request.getOptionId()))
                        .findFirst()
                        .orElse(null);
    
                    if (matchedOption != null) {
                        if (matchedOption.getSelectors() == null) {
                            matchedOption.setSelectors(new ArrayList<>());
                        }
                        if (Boolean.TRUE.equals(request.getAdding())) {
                            matchedOption.getSelectors().add(request.getSelector());
                        } else {
                            matchedOption.getSelectors().stream()
                                .filter(selector -> !selector.equals(request.getSelector()));
                        }
                        return reportRepository.save(report);
                    } else {
                        return Mono.error(new DataNotFoundException());
                    }
                }
                return Mono.error(new DataNotFoundException());
            })
            .map(reportMapper::toReportResponse);
    }

    public Mono<ReportResponse> updateGrades(String reportId, Map<String, Float> grades) {
        return reportRepository.findById(reportId)
            .map(response -> {
                if (response instanceof Report report) {
                    report.getGrades().putAll(grades);
                }
                return response;
            })
            .flatMap(reportRepository::save)
            .map(report -> reportMapper.toReportResponse((Report) report))
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }
    
    public Flux<ReportResponse> updateBulk(CentralizedTestRequest request) {
        return Flux.fromIterable(request.getSchedules())
            .flatMap(schedule -> 
                classroomClient.getClassroomByCode(
                    new GetClassBySemesterAndYearRequest(schedule.getClassCode(), schedule.getSemester(), schedule.getYear())
                )
            ).map(ApiResponse::getData)
            .collectList()
            .map(classes -> {
                List<GetTestForBulkUpdateParams> temp = new ArrayList<>();
                for (ClassroomResponse classroom : classes) {
                    temp.add(new GetTestForBulkUpdateParams(
                        classroom.getId(), classroom.getCode(), request.isMidterm() ? WeightType.MIDTERM : WeightType.FINAL_TERM
                    ));
                }
                return temp;
            })
            .flatMapMany(Flux::fromIterable)
            .flatMap(param -> projectRepository.findAllByClassIdAndSubclassCodeAndWeightTypeAndAutocreatedTrue(
                param.getClassId(), param.getSubclassCode(), param.getWeightType()))
            .collectList()
            .map(events -> {
                List<Project> projects = new ArrayList<>();
                for (BaseEvent event : events) {
                    if (event instanceof Project project)
                        projects.add(project);
                }
                List<Report> reportsToSave = new ArrayList<>();
                for (int i = 0; i < projects.size(); i++) {
                    BulkUpdateTestRequest singleRequest = request.getSchedules().get(i);
                    Project project = projects.get(i);
                    Report report = Report.builder()
                        .projectId(project.getId())
                        .classId(project.getClassId())
                        .subclassCode(project.getSubclassCode())
                        .name("Báo cáo " + (request.isMidterm() ? "Giữa kỳ" : "Cuối kỳ"))
                        .inGroup(project.isInGroup())
                        .weight(project.getWeight())
                        .weightType(project.getWeightType())
                        .fixedWeight(false)
                        .publishDate(LocalDateTime.now())
                        .date(singleRequest.getDate())
                        .closeSubmissionDate(singleRequest.getDate())
                        .remindGradingDate(singleRequest.getDate())
                        .submissionOption(SubmissionOption.FILE)
                        .room(singleRequest.getRoom())
                        .description(singleRequest.genDescription())
                        .allowGradeReview(true)
                        .build();
                    reportsToSave.add(report);
                }
                return reportsToSave;
            })
            .flatMapMany(Flux::fromIterable)
            .flatMap(reportRepository::save)
            .map(reportMapper::toReportResponse)
            .doOnError(e-> log.error("GeneralTest - updateBulk: Error.", e));
    }
}
