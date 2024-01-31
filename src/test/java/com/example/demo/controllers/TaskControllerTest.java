package com.example.demo.controllers;

import com.example.demo.models.Task;
import com.example.demo.services.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest(controllers = TaskController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TaskService taskService;
    @Autowired
    private ObjectMapper objectMapper;
    private Task task, task1;

    @BeforeEach
    void init() {
        task = Task.builder()
                .taskName("New task")
                .completed(false)
                .build();
        task1 = Task.builder()
                .taskName("A new task")
                .completed(true)
                .build();

    }
    @Test
    void createTask_shouldReturnCreatedTask() throws Exception {
        given(taskService.createNewTask(ArgumentMatchers.any()))
                .willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(post("/api/v1/tasks/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.taskName").value("New task"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.completed").value(false));
        verify(taskService, times(1)).createNewTask(argThat(argument -> {
            argument.setTaskCreated(LocalDateTime.now());
            return true;
        }));
    }

    @Test
    void getAllTask_shouldReturnListOfTasks() throws Exception {
        List<Task> expected = List.of(task, task1);
        when(taskService.getAllTask()).thenReturn(expected);

        ResultActions response = mockMvc.perform(get("/api/v1/tasks/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(expected)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].taskName").value("New task"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].completed").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].taskName").value("A new task"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].completed").value(true));
    }
    @Test
    void getTaskById_shouldReturnTask() throws Exception {
        when(taskService.findTaskById(1L)).thenReturn(task);

        ResultActions response = mockMvc.perform(get("/api/v1/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.taskName").value("New task"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.completed").value(false));

    }
    @Test
    void updateTask_shouldReturnUpdatedTask() throws Exception {
            Task updatedTask = Task.builder()
                        .taskName("Updated version")
                        .completed(true)
                        .build();
        updatedTask.setTaskUpdated(LocalDateTime.now());

        when(taskService.updateTask(any(Task.class))).thenReturn(updatedTask);


        ResultActions response = mockMvc.perform(put("/api/v1/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
        response.andExpect(MockMvcResultMatchers.jsonPath("$.taskName").value("Updated version"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.completed").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.taskUpdated").isNotEmpty());

    }
    @Test
    void deleteTask_shouldReturnTaskDeleted() throws Exception {
        when(taskService.deleteTask(1L)).thenReturn(true);

        ResultActions response = mockMvc.perform(delete("/api/v1/tasks/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    void getAllCompletedTasks_shouldReturnCompletedTasks() throws Exception {
        Task task2 = Task.builder()
                .taskName("A new task 2")
                .completed(true)
                .build();
        List<Task> completed = List.of(task1, task2);
        when(taskService.findAllCompletedTask()).thenReturn(completed);

        ResultActions response = mockMvc.perform(get("/api/v1/tasks/completed")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].taskName").value("A new task"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].completed").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].taskName").value("A new task 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].completed").value(true));
    }
    @Test
    void getAllIncompleteTasks_shouldReturnIncompleteTasks() throws Exception {
        Task task2 = Task.builder()
                .taskName("A new task 2")
                .completed(false)
                .build();
        List<Task> incomplete = List.of(task, task2);
        when(taskService.findAllIncompleteTask()).thenReturn(incomplete);

        ResultActions response = mockMvc.perform(get("/api/v1/tasks/incomplete")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].taskName").value("New task"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].completed").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].taskName").value("A new task 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].completed").value(false));
    }
}








