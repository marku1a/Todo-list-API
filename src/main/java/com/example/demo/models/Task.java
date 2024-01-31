package com.example.demo.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Task {
    @Id
    @GeneratedValue
    private Long id;
    private String taskName;
    private Boolean completed;
    private LocalDateTime taskCreated;
    private LocalDateTime taskUpdated;

    private Task() {
    }

    public Task(String task, Boolean completed) {
        this.taskName = task;
        this.completed = completed;
    }



    public static TaskBuilder builder() {
        return new TaskBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskName() {
        return this.taskName;
    }

    public void setTaskName(String task) {
        this.taskName = task;
    }

    public Boolean getCompleted() {
        return this.completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public LocalDateTime getTaskCreated() {
        return this.taskCreated;
    }

    public void setTaskCreated(LocalDateTime taskCreated) {
        this.taskCreated = taskCreated;
    }

    public LocalDateTime getTaskUpdated() {
        return this.taskUpdated;
    }

    public void setTaskUpdated(LocalDateTime taskUpdated) {
        this.taskUpdated = taskUpdated;
    }

    public static class TaskBuilder {

        private String taskName;
        private Boolean completed;


        TaskBuilder() {
        }

        public TaskBuilder taskName(String task) {
            this.taskName = task;
            return this;
        }

        public TaskBuilder completed(Boolean completed) {
            this.completed = completed;
            return this;
        }

        public Task build() {
            return new Task(this.taskName, this.completed);
        }


    }
}
