package com.unicore.classevent_service.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.unicore.classevent_service.dto.request.ClassGroupingScheduleRequest;
import com.unicore.classevent_service.dto.request.GroupingScheduleRequest;
import com.unicore.classevent_service.dto.request.UpdateClassGroupingRequest;
import com.unicore.classevent_service.dto.response.GroupingResponse;
import com.unicore.classevent_service.entity.GroupingSchedule;
import com.unicore.classevent_service.exception.DataNotFoundException;
import com.unicore.classevent_service.exception.InvalidRequestException;
import com.unicore.classevent_service.mapper.GroupingScheduleMapper;
import com.unicore.classevent_service.repository.BaseEventRepository;
import com.unicore.classevent_service.repository.GroupingScheduleRepository;
import com.unicore.classevent_service.repository.httpclient.ClassroomClient;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GroupingService {
    private final GroupingScheduleRepository scheduleRepository;
    private final BaseEventRepository eventRepository;
    private final GroupingScheduleMapper scheduleMapper;
    private final ClassroomClient classroomClient;

    private Mono<GroupingResponse> createGroupingSchedule(GroupingSchedule grouping) {
        return Mono.just(grouping)
            .flatMap(scheduleRepository::save)
            .map(scheduleMapper::toResponse);
    }

    public Mono<GroupingResponse> createClassGroupingSchedule(ClassGroupingScheduleRequest request) {
        return Mono.just(request)
            .map(groupingRequest -> {
                GroupingSchedule grouping = scheduleMapper.classGroupingToGroupingSchedule(request);
                grouping.setId(UUID.randomUUID().toString());
                grouping.setDefault(true);
                return grouping;
            })
            .flatMap(grouping -> classroomClient.updateClassroom(
                new UpdateClassGroupingRequest(
                    request.getClassId(), 
                    request.getSubclassCode(), 
                    grouping.getId()
                )).map(classroom -> grouping)
            )
            .flatMap(this::createGroupingSchedule);
    }

    public Mono<GroupingResponse> createEventGroupingSchedule(String eventId, GroupingScheduleRequest request) {
        GroupingSchedule grouping = scheduleMapper.toGroupingSchedule(request);
        grouping.setId(UUID.randomUUID().toString());
        return eventRepository.findById(eventId)
            .flatMap(event -> {
                if (!event.getGroupingId().isEmpty()) {
                    return Mono.error(new InvalidRequestException("This event already has grouping"));
                }
                event.setGroupingId(grouping.getId());
                grouping.setClassId(event.getClassId());
                grouping.setSubclassCode(event.getSubclassCode());
                return eventRepository.save(event);
            })
            .flatMap(event -> createGroupingSchedule(grouping));
    }

    public Mono<GroupingResponse> updateGroupingSchedule(String id, GroupingScheduleRequest request) {
        return scheduleRepository.findById(id)
            .map(response -> {
                scheduleMapper.updateSchedule(response, request);
                return response;
            })
            .flatMap(scheduleRepository::save)
            .map(scheduleMapper::toResponse)
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }

    public Mono<GroupingResponse> getGroupingById(String id) {
        return scheduleRepository.findById(id)
            .map(scheduleMapper::toResponse)
            .flatMap(response -> {
                // lay danh sach nhom
                response.setGroups(null);
                return Mono.just(response);
            })
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }

    // ddang ky nhom
    

}
