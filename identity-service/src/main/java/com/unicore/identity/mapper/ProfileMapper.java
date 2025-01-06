package com.unicore.identity.mapper;

import org.mapstruct.Mapper;

import com.unicore.identity.dto.request.ProfileCreationRequest;
import com.unicore.identity.dto.request.UserCreationRequest;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileCreationRequest toProfileCreationRequest(UserCreationRequest request);
}
