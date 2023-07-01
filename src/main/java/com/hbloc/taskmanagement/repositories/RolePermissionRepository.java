package com.hbloc.taskmanagement.repositories;

import com.hbloc.taskmanagement.entity.RolePermissionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePermissionRepository extends JpaRepository<RolePermissionsEntity, Integer> {
}
