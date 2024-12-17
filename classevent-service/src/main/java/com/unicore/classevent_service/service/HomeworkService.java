package com.unicore.classevent_service.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.unicore.classevent_service.dto.request.GetByClassRequest;
import com.unicore.classevent_service.dto.request.GetByDateRequest;
import com.unicore.classevent_service.dto.request.HomeworkCreationRequest;
import com.unicore.classevent_service.dto.request.HomeworkUpdateRequest;
import com.unicore.classevent_service.dto.response.HomeworkResponse;
import com.unicore.classevent_service.entity.Homework;
import com.unicore.classevent_service.enums.EventType;
import com.unicore.classevent_service.exception.DataNotFoundException;
import com.unicore.classevent_service.mapper.HomeworkMapper;
import com.unicore.classevent_service.repository.BaseEventRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class HomeworkService {
    private final BaseEventRepository repository;
    private final HomeworkMapper homeworkMapper;

    public Flux<HomeworkResponse> createHomework(HomeworkCreationRequest request) {
        return Flux.fromIterable(request.getSubclassCodes())
            .map(subclassCodes -> {
                Homework homework = homeworkMapper.toHomework(request);
                homework.setSubclassCode(subclassCodes);
                homework.setCreatedBy("Loc");
                homework.setCreatedDate(Date.from(Instant.now()));
                return homework;
            })
            .flatMap(this::saveHomework);
    }

    private Mono<HomeworkResponse> saveHomework(Homework homework) {
        return Mono.just(homework)
            .flatMap(repository::save)
            .map(homeworkMapper::toHomeworkResponse);
    }

    /// Các hàm get bên dưới cần gọi qua bên submission xem hoàn thành chưa

    public Mono<HomeworkResponse> getHomework(String id) {
        return repository.findById(id)
            .map(homework -> homeworkMapper.toHomeworkResponse((Homework) homework))
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
        }
        
    public Flux<HomeworkResponse> getClassHomework(GetByClassRequest request) {
        return repository.findAllByClassIdAndSubclassCodeAndType(request.getClassId(), request.getSubclassCode(), EventType.HOMEWORK)
            .map(homework -> homeworkMapper.toHomeworkResponse((Homework) homework))
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
        }
        
    public Flux<HomeworkResponse> getProjectHomework(String projectId) {
        return repository.findAllByProjectIdAndType(projectId, EventType.HOMEWORK)
            .map(homework -> homeworkMapper.toHomeworkResponse((Homework) homework))
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }

    public Flux<HomeworkResponse> findActiveHomework(@Valid GetByDateRequest request) {
        LocalDateTime startDateTime = Optional.ofNullable(request.getDate())
                                            .orElse(LocalDate.now().atStartOfDay());
        LocalDateTime endDateTime = Optional.ofNullable(request.getDate())
                                            .orElse(LocalDate.now().atTime(LocalTime.MAX));

        // Perform the query and map results
        return repository.findActiveEvents(
                    request.getClassId(),
                    request.getSubclassCode(),
                    startDateTime,
                    endDateTime,
                    EventType.HOMEWORK)
            .map(homework -> homeworkMapper.toHomeworkResponse((Homework) homework))
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }
    
    public Mono<HomeworkResponse> updateHomework(String id, HomeworkUpdateRequest request) {
        return repository.findById(id)
            .map(homework -> {
                Homework entity = homeworkMapper.toHomework((Homework) homework, request);
                
                entity.setCreatedBy("Loc Update");
                entity.setCreatedDate(Date.from(Instant.now()));

                return entity;
            })
            .flatMap(repository::save)
            .map(homeworkMapper::toHomeworkResponse)
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }
}
