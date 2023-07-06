package com.hbloc.taskmanagement.repositories;

import com.hbloc.taskmanagement.entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<PermissionEntity, Integer> {
    Optional<PermissionEntity> findByCode(String code);
}
