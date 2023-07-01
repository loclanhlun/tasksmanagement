package com.hbloc.taskmanagement.repositories;

import com.hbloc.taskmanagement.entity.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<StatusEntity, Integer> {
}
