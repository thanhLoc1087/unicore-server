package com.unicore.classevent_service.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.unicore.classevent_service.dto.request.GetByClassRequest;
import com.unicore.classevent_service.dto.request.GetByDateRequest;
import com.unicore.classevent_service.dto.request.QueryChooseOptionRequest;
import com.unicore.classevent_service.dto.request.ReportCreationRequest;
import com.unicore.classevent_service.dto.request.ReportUpdateRequest;
import com.unicore.classevent_service.dto.response.ReportResponse;
import com.unicore.classevent_service.entity.QueryOption;
import com.unicore.classevent_service.entity.Report;
import com.unicore.classevent_service.exception.DataNotFoundException;
import com.unicore.classevent_service.mapper.ReportMapper;
import com.unicore.classevent_service.repository.ReportRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final ReportMapper reportMapper;
    
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
            .map(reportMapper::toReportResponse)
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }

    public Flux<ReportResponse> getClassReports(GetByClassRequest request) {
        return reportRepository.findAllByClassIdAndSubclassCode(request.getClassId(), request.getSubclassCode())
            .map(reportMapper::toReportResponse)
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }

    public Flux<ReportResponse> getProjectReports(String projectId) {
        return reportRepository.findAllByProjectId(projectId)
            .map(reportMapper::toReportResponse)
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }

    public Flux<ReportResponse> findActiveReports(@Valid GetByDateRequest request) {
        LocalDateTime startDateTime = Optional.ofNullable(request.getDate())
                                            .orElse(LocalDate.now().atStartOfDay());
        LocalDateTime endDateTime = Optional.ofNullable(request.getDate())
                                            .orElse(LocalDate.now().atTime(LocalTime.MAX));

        // Perform the query and map results
        return reportRepository.findActiveReports(
                    request.getClassId(),
                    request.getSubclassCode(),
                    startDateTime,
                    endDateTime)
                .map(reportMapper::toReportResponse)
                .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }

    public Mono<ReportResponse> updateReport(String id, ReportUpdateRequest request) {
        return reportRepository.findById(id)
            .map(report -> reportMapper.toReport(report, request))
            .flatMap(this::saveReport)
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }
    
    public Mono<ReportResponse> chooseOption(QueryChooseOptionRequest request) {
        return reportRepository.findById(request.getReportId())
            .flatMap(report -> {
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
            })
            .map(reportMapper::toReportResponse);
    }
}
