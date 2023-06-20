package com.example.demo.services;

import com.example.demo.models.Task;


import com.example.demo.repositories.TaskRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {
	@Autowired
	private TaskRepository taskRepository;
	
	//Read: all tasks, by id, by completed, by incomplete
	public List<Task> getAllTask() {
		return taskRepository.findAll();
	}
	
	public Task findTaskById(Long id) {
		return taskRepository.getById(id);
	}
	public List<Task> findAllCompletedTask() {
		return taskRepository.findByCompletedTrue();
	}
	public List<Task> findAllInCompleteTask() {
		return taskRepository.findByCompletedFalse();
	}
	
	public Task createNewTask(Task task) {
		task.setTaskCreated(LocalDateTime.now());
		return taskRepository.save(task);
	}
	public boolean deleteTask(Long id) {
	    if (taskRepository.existsById(id)) {
	        taskRepository.deleteById(id);
	        return true;
	    } else {
	        return false;
	    }
	}
	public Task updateTask(Task task) {
		Task exTask = taskRepository.getById(task.getId());
		task.setTaskCreated(exTask.getTaskCreated());
		task.setTaskUpdated(LocalDateTime.now());
		return taskRepository.save(task);
	}
	
}
