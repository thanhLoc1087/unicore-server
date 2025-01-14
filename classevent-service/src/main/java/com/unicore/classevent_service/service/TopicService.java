package com.unicore.classevent_service.service;

import org.springframework.stereotype.Service;

import com.unicore.classevent_service.dto.request.InternStudentListImportRequest;
import com.unicore.classevent_service.dto.request.InternStudentRequest;
import com.unicore.classevent_service.entity.InternTopic;
import com.unicore.classevent_service.mapper.TopicMapper;
import com.unicore.classevent_service.repository.TopicRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TopicService {
    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;

    // import
    public Flux<InternTopic> importInternTopics(InternStudentListImportRequest request) {
        return Flux.fromIterable(request.getStudents())
            .map(internRequest -> {
                InternTopic internTopic = topicMapper.toInternTopic(internRequest);
                internTopic.setClassId(request.getClassId());
                internTopic.genId();
                return internTopic;
            })
            .flatMap(topicRepository::save);
    }

    // chinh sua
    public Mono<InternTopic> updateInternTopic(String topicId, InternStudentRequest request) {
        return topicRepository.findById(topicId)
            .map(response -> {
                if (response instanceof InternTopic topic) {
                    return topicMapper.updateInternTopic(topic, request);
                }
                return null;
            })
            .flatMap(topicRepository::save);
    }

    // import ds gvpb

    // import hoi dong ttdn

    // import hoi dong kltn
}
