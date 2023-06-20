package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.stereotype.Repository;
import com.example.demo.models.Task;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
	public Task findByTask(String task);
	public List<Task> findByCompletedTrue();
	public List<Task> findByCompletedFalse();
	public Task getById(Long id);
}
