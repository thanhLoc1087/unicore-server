package com.unicore.identity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.unicore.identity.dto.request.RoleRequest;
import com.unicore.identity.dto.response.RoleResponse;
import com.unicore.identity.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
