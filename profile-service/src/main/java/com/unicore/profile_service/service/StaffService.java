package com.unicore.profile_service.service;

import org.springframework.dao.DataAccessException;
import org.springframework.r2dbc.BadSqlGrammarException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.unicore.profile_service.exception.AppException;
import com.unicore.profile_service.exception.ErrorCode;
import com.unicore.profile_service.dto.request.StaffBulkCreationRequest;
import com.unicore.profile_service.dto.request.StaffCreationRequest;
import com.unicore.profile_service.dto.response.StaffResponse;
import com.unicore.profile_service.mapper.StaffMapper;
import com.unicore.profile_service.repository.StaffRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class StaffService {
    private final StaffRepository staffRepository;
    private final StaffMapper staffMapper;

    public Mono<StaffResponse> createStaff(StaffCreationRequest request) {
        return Mono.just(request)
            .map(staffMapper::toStaff)
            .flatMap(staffRepository::save)
            .map(staffMapper::toStaffResponse)
            .onErrorResume(throwable -> {
                if (throwable instanceof BadSqlGrammarException || throwable instanceof DataAccessException) {
                    log.error("R2DBC data invalidation error: {}", throwable.getCause());
                    // Return an error response or rethrow as a custom exception
                    return Mono.error(new AppException(ErrorCode.DUPLICATE));
                }
                return Mono.error(throwable); // Propagate other exceptions
            })
            .doOnSuccess(response -> {
                // goi kafka tao Account
            });
    }

    @Transactional
    public Flux<StaffResponse> createStaffs(StaffBulkCreationRequest request) {
        return Flux.fromIterable(request.getStaff())
            .map(staff -> {
                staff.setOrganizationId(request.getOrganizationId());
                return staff;
            })
            .flatMap(this::createStaff);
    }

    public Mono<StaffResponse> getStaffById(String id) {
        return staffRepository.findById(id)
            .map(staffMapper::toStaffResponse);
    }

    public Mono<StaffResponse> getStaffByCode(String code) {
        return staffRepository.findByCode(code)
            .map(staffMapper::toStaffResponse);
    }

    public Flux<StaffResponse> getStaffs() {
        return staffRepository.findAll()
            .map(staffMapper::toStaffResponse);
    }

    public Flux<StaffResponse> getStaffsByOrganizationId(String organizationId) {
        return staffRepository.findByOrganizationId(organizationId)
            .map(staffMapper::toStaffResponse);
    }

    public Mono<Void> deleteById(String id) {
        return staffRepository.deleteById(id);
    }

    public Mono<Void> deleteByIds(List<String> ids) {
        return staffRepository.deleteAllById(ids);
    }
}
