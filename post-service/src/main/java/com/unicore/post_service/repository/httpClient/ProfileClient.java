package com.unicore.post_service.repository.httpClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.unicore.post_service.dto.response.ApiResponse;
import com.unicore.post_service.dto.response.ProfileResponse;

@FeignClient(name="profile-service", url="${app.services.profile.url}")
public interface ProfileClient {
    @GetMapping("/internal/users/{userId}")
    ApiResponse<ProfileResponse> getProfile(@PathVariable String userId);
}
