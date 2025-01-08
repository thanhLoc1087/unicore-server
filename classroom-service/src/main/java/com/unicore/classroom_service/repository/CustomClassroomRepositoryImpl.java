package com.unicore.classroom_service.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.unicore.classroom_service.dto.request.ClassFilterRequest;
import com.unicore.classroom_service.entity.Classroom;

import reactor.core.publisher.Flux;

public class CustomClassroomRepositoryImpl implements CustomClassroomRepository {
    private final ReactiveMongoTemplate mongoTemplate;

    public CustomClassroomRepositoryImpl(ReactiveMongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Flux<Classroom> findByFilters(ClassFilterRequest filterRequest) {
        Query query = new Query();

        if (filterRequest.getSemester() > 0) {
            query.addCriteria(Criteria.where("semester").is(filterRequest.getSemester()));
        }
        if (filterRequest.getYear() > 0) {
            query.addCriteria(Criteria.where("year").is(filterRequest.getYear()));
        }
        if (filterRequest.getSubjectCode() != null && !filterRequest.getSubjectCode().isEmpty()) {
            query.addCriteria(Criteria.where("subjectCode").is(filterRequest.getSubjectCode()));
        }
        if (filterRequest.getTeacherCode() != null && !filterRequest.getTeacherCode().isEmpty()) {
            query.addCriteria(Criteria.where("subclasses.teacherCodes").is(filterRequest.getTeacherCode()));
        }

        query.with(Sort.by(Sort.Direction.ASC, "code")); // Example sorting, can be adjusted

        return mongoTemplate.find(query, Classroom.class);
    }
}
