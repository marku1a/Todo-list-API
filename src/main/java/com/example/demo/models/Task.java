package com.example.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Task {
	@Id
	@GeneratedValue
	private Long id;
	private String task;
	private boolean completed;	
	private LocalDateTime taskCreated;
	private LocalDateTime taskUpdated;
	
	public Task() {
    }
	
	public Task(String task, boolean completed) {
		this.task = task;
		this.completed = completed;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
	public boolean getCompleted() {
		return completed;
	}
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	public LocalDateTime getTaskCreated() {
		return taskCreated;
	}
	public void setTaskCreated(LocalDateTime taskCreated) {
		this.taskCreated = taskCreated;
	}
	
	public LocalDateTime getTaskUpdated() {
		return taskUpdated;
	}
	public void setTaskUpdated(LocalDateTime taskUpdated) {
		this.taskUpdated = taskUpdated;
	}
	
	
	
}
