package com.example.demo.controllers;

import com.example.demo.models.Task;

import com.example.demo.services.TaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/api/v1/tasks")
public class TaskController {
	
	@Autowired
	private TaskService taskService;
	@GetMapping("/")
	public ResponseEntity<List<Task>> getAllTasks() {
		return ResponseEntity.ok(taskService.getAllTask());
	}
	@GetMapping("/{id}")
	public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
	    Task task = taskService.findTaskById(id);
	    if (task != null) {
	        return ResponseEntity.ok(task);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	@GetMapping("/completed")
	public ResponseEntity<List<Task>> getAllCompletedTasks() {
		return ResponseEntity.ok(taskService.findAllCompletedTask());
	}
	@GetMapping("/incomplete")
	public ResponseEntity<List<Task>> getAllInCompleteTasks() {
		return ResponseEntity.ok(taskService.findAllInCompleteTask());
	}
	@PostMapping("/")
	public ResponseEntity<Task> createTask(@RequestBody Task task) {
		return ResponseEntity.ok(taskService.createNewTask(task));
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteTask(@PathVariable Long id) {
		if (taskService.deleteTask(id)) {
	        return ResponseEntity.ok(true);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	@PutMapping("/{id}")
	public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task) {
		task.setId(id);
		return ResponseEntity.ok(taskService.updateTask(task));
	}
}
