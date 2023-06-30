package com.hbloc.taskmanagement.repositories;

import com.hbloc.taskmanagement.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
}
