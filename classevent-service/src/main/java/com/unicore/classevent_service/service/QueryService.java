package com.unicore.classevent_service.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.unicore.classevent_service.dto.request.GetByClassRequest;
import com.unicore.classevent_service.dto.request.GetByDateRequest;
import com.unicore.classevent_service.dto.request.QueryChooseOptionRequest;
import com.unicore.classevent_service.dto.request.QueryCreationRequest;
import com.unicore.classevent_service.dto.request.QueryUpdateRequest;
import com.unicore.classevent_service.dto.response.QueryResponse;
import com.unicore.classevent_service.entity.QueryOption;
import com.unicore.classevent_service.exception.DataNotFoundException;
import com.unicore.classevent_service.mapper.QueryMapper;
import com.unicore.classevent_service.repository.QueryRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class QueryService {
    private final QueryRepository queryRepository;
    private final QueryMapper queryMapper;


    public Mono<QueryResponse> createQuery(QueryCreationRequest request) {
        return Mono.just(request)
            .map(queryMapper::toQuery)
            .flatMap(queryRepository::save)
            .map(queryMapper::toQueryResponse);
    }

    public Mono<QueryResponse> getQuery(String id) {
        return queryRepository.findById(id)
            .map(queryMapper::toQueryResponse)
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }

    public Flux<QueryResponse> getClassQueries(GetByClassRequest request) {
        return queryRepository.findByClassIdAndSubclassCode(request.getClassId(), request.getSubclassCode())
            .map(queryMapper::toQueryResponse)
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }

    public Flux<QueryResponse> findActiveQueries(@Valid GetByDateRequest request) {
        LocalDateTime startDateTime = Optional.ofNullable(request.getDate())
                                            .orElse(LocalDate.now().atStartOfDay());
        LocalDateTime endDateTime = Optional.ofNullable(request.getDate())
                                            .orElse(LocalDate.now().atTime(LocalTime.MAX));

        // Perform the query and map results
        return queryRepository.findActiveQueries(
                    request.getClassId(),
                    request.getSubclassCode(),
                    startDateTime,
                    endDateTime)
                .map(queryMapper::toQueryResponse)
                .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }

    public Mono<QueryResponse> updateQuery(String id, QueryUpdateRequest request) {
        return queryRepository.findById(id)
            .map(report -> queryMapper.toQuery(report, request))
            .flatMap(queryRepository::save)
            .map(queryMapper::toQueryResponse)
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }

    public Mono<QueryResponse> chooseOption(QueryChooseOptionRequest request) {
        return queryRepository.findById(request.getQueryId())
            .flatMap(query -> {
                QueryOption matchedOption = query.getOptions().stream()
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
                    return queryRepository.save(query);
                } else {
                    return Mono.error(new DataNotFoundException());
                }
            })
            .map(queryMapper::toQueryResponse);
    }
}
