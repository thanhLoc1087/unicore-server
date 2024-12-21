package com.unicore.grade_service.service;

import org.springframework.stereotype.Service;

import com.unicore.grade_service.dto.request.ReviewFormUpdateScoreRequest;
import com.unicore.grade_service.dto.request.ReviewFormCreationRequest;
import com.unicore.grade_service.entity.GuideReviewForm;
import com.unicore.grade_service.entity.ReviewForm;
import com.unicore.grade_service.exception.DataNotFoundException;
import com.unicore.grade_service.mapper.ReviewFormMapper;
import com.unicore.grade_service.repository.ReviewFormRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ReviewFormService {
    private final ReviewFormRepository repository;
    private final ReviewFormMapper mapper;

    // lưu
    public Mono<ReviewForm> createForm(ReviewFormCreationRequest request) {
        ReviewForm form = ReviewForm.builder()
            
            .build();
        
        return Mono.just(form);
    }

    // chấm điểm
    public Mono<ReviewForm> updateFormScore(String id, ReviewFormUpdateScoreRequest request) {
        return repository.findById(id)
            .map(response -> {
                mapper.updateForm(response, request);
                if (response instanceof GuideReviewForm form) {
                    form.setAbbrTable(request.isAbbrTable());
                    form.setAbstractEn(request.isAbstractEn());
                    form.setAbstractVn(request.isAbstractVn());
                    form.setTribute(request.isTribute());
                    form.setIndex(request.isIndex());
                }
                return response;
            })
            .flatMap(repository::save)
            .switchIfEmpty(Mono.error(new DataNotFoundException()));
    }

    // lọc theo project, theo ky, nam, gv, chuc vu
}
