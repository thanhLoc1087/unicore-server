package com.unicore.identity.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unicore.identity.dto.request.PermissionRequest;
import com.unicore.identity.dto.response.PermissionResponse;
import com.unicore.identity.entity.Permission;
import com.unicore.identity.mapper.PermissionMapper;
import com.unicore.identity.repository.PermissionRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {
    PermissionRepository permissionRepositoty;
    PermissionMapper permissionMapper;

    public PermissionResponse create(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);

        permission = permissionRepositoty.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    public List<PermissionResponse> getAll() {
        var permissions = permissionRepositoty.findAll();
        return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
    }

    public void delete(String permissionName) {
        permissionRepositoty.deleteById(permissionName);
    }
}
