package com.unicore.classevent_service.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.unicore.classevent_service.dto.GroupRequest;
import com.unicore.classevent_service.dto.request.ClassGroupingScheduleRequest;
import com.unicore.classevent_service.dto.request.GroupingScheduleRequest;
import com.unicore.classevent_service.dto.request.UpdateClassGroupingRequest;
import com.unicore.classevent_service.dto.response.GroupingResponse;
import com.unicore.classevent_service.entity.Group;
import com.unicore.classevent_service.entity.GroupingSchedule;
import com.unicore.classevent_service.exception.DataNotFoundException;
import com.unicore.classevent_service.exception.InvalidRequestException;
import com.unicore.classevent_service.mapper.GroupMapper;
import com.unicore.classevent_service.mapper.GroupingScheduleMapper;
import com.unicore.classevent_service.repository.BaseEventRepository;
import com.unicore.classevent_service.repository.GroupRepository;
import com.unicore.classevent_service.repository.GroupingScheduleRepository;
import com.unicore.classevent_service.repository.httpclient.ClassroomClient;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GroupingService {
    private final GroupingScheduleRepository scheduleRepository;
    private final BaseEventRepository eventRepository;
    private final GroupingScheduleMapper scheduleMapper;
    private final ClassroomClient classroomClient;

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;

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
            .flatMap(response -> groupRepository.findByGroupingId(response.getId())
                    .collectList()
                    .map(groups -> {
                        response.setGroups(groups);
                        return (response);
                    })
            ).switchIfEmpty(Mono.error(new DataNotFoundException()));
    }

    // ddang ky nhom
    public Mono<GroupingResponse> createGroup(GroupRequest request) {
        return getGroupingById(request.getGroupingId())
            .flatMap(schedule -> {
                if (schedule.getEndRegisterDate().isBefore(LocalDateTime.now())) {
                    return Mono.error(new InvalidRequestException("Overdue."));
                }
                if (request.getMembers().size() < schedule.getMinSize() ||
                    request.getMembers().size() > schedule.getMaxSize()
                ) {
                    return Mono.error(new InvalidRequestException(
                        "Invalid group size. Min: " + schedule.getMinSize() + ", Max: " + schedule.getMaxSize()));
                }

                int lastIndex = 0;
                if (!schedule.getGroups().isEmpty()) {
                    lastIndex = schedule.getGroups().getLast().getIndex();
                }
                int index = lastIndex + 1;

                return Mono.just(request)
                    .map(newGroup -> {
                        Group group = groupMapper.toGroup(request);
                        group.setIndex(index);
                        group.setLeaderCode(group.getMembers().getFirst().getStudentCode());
                        return group;
                    })
                    .flatMap(groupRepository::save)
                    .map(savedGroup -> {
                        schedule.getGroups().add(savedGroup);
                        return schedule;
                    });
            })
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }

    public Mono<Group> internalCreateGroup(GroupRequest request) {
        return getGroupingById(request.getGroupingId())
            .flatMap(schedule -> {
                if (schedule.getEndRegisterDate().isBefore(LocalDateTime.now())) {
                    return Mono.error(new InvalidRequestException("Overdue."));
                }

                int lastIndex = 0;
                if (!schedule.getGroups().isEmpty()) {
                    lastIndex = schedule.getGroups().getLast().getIndex();
                }
                int index = lastIndex + 1;

                return Mono.just(request)
                    .map(newGroup -> {
                        Group group = groupMapper.toGroup(request);
                        group.setIndex(index);
                        return group;
                    })
                    .flatMap(groupRepository::save);
            })
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }

    // cap nhat nhom
    public Mono<GroupingResponse> updateGroup(String groupId, GroupRequest request) {
        return groupRepository.findById(groupId)
            .map(group -> groupMapper.updateGroup(group, request))
            .flatMap(groupRepository::save)
            .flatMap(updatedGroup -> {
                return getGroupingById(updatedGroup.getGroupingId());
            });
    }

    // xoa nhom
    public Mono<GroupingResponse> deleteGroup(String groupId) {
        return groupRepository.findById(groupId)
            .flatMap(group -> {
                String scheduleId = group.getGroupingId();
                return groupRepository.delete(group)
                    .then(Mono.just(scheduleId));
            })
            .flatMap(this::getGroupingById)
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }

    // xoa nhieu nhom
    public Flux<GroupingResponse> deleteGroups(List<String> groupIds) {
    return Flux.fromIterable(groupIds)
        .flatMap(groupId -> groupRepository.findById(groupId)
            .flatMap(group -> {
                String scheduleId = group.getGroupingId();
                return groupRepository.delete(group)
                    .then(Mono.just(scheduleId));
            })
            .switchIfEmpty(Mono.error(new DataNotFoundException()))
        )
        .flatMap(this::getGroupingById);
    }
}
