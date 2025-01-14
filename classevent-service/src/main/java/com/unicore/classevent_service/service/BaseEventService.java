package com.unicore.classevent_service.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.unicore.classevent_service.dto.request.GetEventCreationVariablesRequest;
import com.unicore.classevent_service.dto.response.CreateEventVariables;
import com.unicore.classevent_service.entity.BaseEvent;
import com.unicore.classevent_service.entity.Subclass;
import com.unicore.classevent_service.entity.SubclassRemainingWeight;
import com.unicore.classevent_service.entity.WeightVariables;
import com.unicore.classevent_service.enums.ClassType;
import com.unicore.classevent_service.enums.WeightType;
import com.unicore.classevent_service.repository.BaseEventRepository;
import com.unicore.classevent_service.repository.httpclient.ClassroomClient;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BaseEventService {
    private final BaseEventRepository baseEventRepository;
    private final ClassroomClient classroomClient;

    public Mono<CreateEventVariables> getCreateEventVariables(GetEventCreationVariablesRequest request) {
        return classroomClient.getClassById(request.getClassId())
            .map(classroom -> {
                CreateEventVariables result = new CreateEventVariables();
                result.setClassId(classroom.getId());
                for (Subclass subclass : classroom.getSubclasses()) {
                    if (subclass.getTeacherCodes().contains(request.getTeacherCode())) {
                        SubclassRemainingWeight weight = new SubclassRemainingWeight(subclass.getCode(), subclass.getType(), new ArrayList<>());
                        if ((subclass.getType() == ClassType.HINH_THUC_1 || subclass.getType() == ClassType.HINH_THUC_2) 
                            && classroom.getSubject().getPracticalWeight() > 0) {
                            weight.getWeights().add(new WeightVariables(WeightType.PRACTICAL, 100f));
                        } else {
                            if (classroom.getSubject().getCourseworkWeight() > 0) {
                                weight.getWeights().add(new WeightVariables(WeightType.COURSEWORK, 100f));
                            }
                            if (classroom.getSubject().getMidtermWeight() > 0) {
                                weight.getWeights().add(new WeightVariables(WeightType.MIDTERM, 100f));
                            }
                            if (classroom.getSubject().getFinalWeight() > 0) {
                                weight.getWeights().add(new WeightVariables(WeightType.FINAL_TERM, 100f));
                            }
                        }
                        result.getWeights().add(weight);
                    }
                }
                return result;
            })
            .flatMap(result ->  Flux.fromIterable(result.getWeights()) 
                .flatMap(weight -> baseEventRepository
                    .findAllByClassIdAndSubclassCode(result.getClassId(), weight.getSubclassCode())
                        .collectList()
                        .map(events -> {
                            for (SubclassRemainingWeight weightType : result.getWeights()) {
                                if (weightType.getSubclassCode().equals(weight.getSubclassCode())) {
                                    for (WeightVariables weightVar : weightType.getWeights()) {
                                        for (BaseEvent event : events) {
                                            if (weightVar.getType() == event.getWeightType() && event.isFixedWeight()) {
                                                weightVar.setRemaining(weightVar.getRemaining() - event.getWeight());
                                            }
                                        }
                                    }
                                    break;
                                }
                            }
                            return events;
                        })
                )
                .collectList()
                .map(list -> result)
            );
    }
}
