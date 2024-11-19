package com.unicore.classevent_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unicore.classevent_service.dto.request.EventGroupingAddGroupRequest;
import com.unicore.classevent_service.dto.request.EventGroupingCreationRequest;
import com.unicore.classevent_service.dto.request.EventGroupingUpdateRequest;
import com.unicore.classevent_service.dto.response.EventGroupingResponse;
import com.unicore.classevent_service.entity.EventGrouping;
import com.unicore.classevent_service.entity.StudentInGroup;
import com.unicore.classevent_service.enums.EventType;
import com.unicore.classevent_service.exception.DataNotFoundException;
import com.unicore.classevent_service.exception.InvalidRequestException;
import com.unicore.classevent_service.mapper.EventGroupingMapper;
import com.unicore.classevent_service.repository.EventGroupingRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class EventGroupingService {

    private final EventGroupingRepository repository;
    private final EventGroupingMapper mapper;
    
    public Mono<EventGroupingResponse> getGrouping(String eventId) {
        return repository.findByEventId(eventId)
            .map(mapper::toEventGroupingResponse)
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }

    public Mono<EventGroupingResponse> createEventGrouping(EventGroupingCreationRequest request, EventType type) {
        return checkDuplicate(request.getEventId())
            .flatMap(result -> Boolean.TRUE.equals(result) ? 
                Mono.error(new InvalidRequestException()) :
                Mono.just(request)
                    .map(grouping -> {
                        EventGrouping eventGrouping = mapper.toEventGrouping(request);
                        eventGrouping.setEventType(type);
                        return eventGrouping;
                    })
                    .flatMap(repository::save)
                    .map(mapper::toEventGroupingResponse)
            );
    }

    public Mono<EventGroupingResponse> updateEventGrouping(String id, EventGroupingUpdateRequest request) {
        return repository.findById(id)
            .map(eventGrouping -> mapper.toEventGrouping(eventGrouping, request))
            .flatMap(repository::save)
            .map(mapper::toEventGroupingResponse);
    }

    public Mono<EventGroupingResponse> addGroupToEventGrouping(EventGroupingAddGroupRequest request) {
        List<StudentInGroup> groupStudents = request.getGroup().getMembers();
        for (StudentInGroup student : groupStudents) {
            student.setGroupName(request.getGroup().getName());
        }
        return repository.findByEventId(request.getEventId())
            .flatMap(grouping -> {
                grouping.getGroups().add(request.getGroup());
                return repository.save(grouping);
            })
            .map(mapper::toEventGroupingResponse);
    }
    

    private Mono<Boolean> checkDuplicate(String eventId) {
        return repository.findByEventId(eventId)
            .hasElement();
    }
}
