package com.klaroboard.backend.repository;

import com.klaroboard.backend.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // This repository will manage our Task entity.
    // We get all the standard database operations for tasks now.
}