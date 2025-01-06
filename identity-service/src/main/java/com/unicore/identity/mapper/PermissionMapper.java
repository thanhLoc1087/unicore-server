package com.unicore.identity.mapper;

import org.mapstruct.Mapper;

import com.unicore.identity.dto.request.PermissionRequest;
import com.unicore.identity.dto.response.PermissionResponse;
import com.unicore.identity.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
