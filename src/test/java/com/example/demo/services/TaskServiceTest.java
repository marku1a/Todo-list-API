package com.example.demo.services;


import com.example.demo.models.Task;
import com.example.demo.repositories.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;
    @InjectMocks
    private TaskService taskService;

    @Test
    void createNewTask_shouldReturnCreatedTask() {
       Task task = Task.builder()
               .taskName("First task")
               .completed(true).build();

        when(taskRepository.save(Mockito.any(Task.class))).thenReturn(task);
        Task savedTask = taskService.createNewTask(task);

        assertThat(savedTask).isNotNull();
        assertThat(savedTask.getTaskName()).isEqualTo(task.getTaskName());
        assertThat(savedTask.getCompleted()).isEqualTo(task.getCompleted());
        assertThat(savedTask.getTaskCreated()).isNotNull();
        assertThat(savedTask.getTaskUpdated()).isNull();
    }
    @Test
    void getAllTask_shouldReturnAllTasks() {
        Task task1 =  Task.builder()
                .taskName("First task")
                .completed(false).build();
        Task task2 =  Task.builder()
                .taskName("Second task")
                .completed(false).build();
        List<Task> expected = List.of(task1, task2);
        when(taskRepository.findAll()).thenReturn(expected);

        List<Task> actual = taskService.getAllTask();

        assertThat(actual).usingRecursiveAssertion().isEqualTo(expected);
    }
    @Test
    void findTaskById_shouldReturnTask() {
        Task task = Task.builder()
                .taskName("First task")
                .completed(false).build();
        when(taskRepository.getById(1L)).thenReturn(task);

        Task taskReturned = taskService.findTaskById(1L);

        assertThat(taskReturned).isEqualTo(task);
    }
    @Test
    void findAllCompletedTask_shouldReturnTwoCompletedTasks() {
        Task task1 =  Task.builder()
                .taskName("First task")
                .completed(true).build();
        Task task2 =  Task.builder()
                .taskName("Second task")
                .completed(true).build();
        List<Task> expected = List.of(task1, task2);
        when(taskRepository.findByCompletedTrue()).thenReturn(expected);

        List<Task> returned = taskService.findAllCompletedTask();

        assertThat(returned).hasSize(2)
                .allMatch(Task::getCompleted);
    }
    @Test
    void findAllIncompleteTask_shouldReturnTwoIncompleteTasks() {
        Task task1 =  Task.builder()
                .taskName("First task")
                .completed(false).build();
        Task task2 =  Task.builder()
                .taskName("Second task")
                .completed(false).build();
        List<Task> expected = List.of(task1, task2);
        when(taskRepository.findByCompletedFalse()).thenReturn(expected);

        List<Task> returned = taskService.findAllIncompleteTask();

        assertThat(returned).hasSize(2)
                .noneMatch(Task::getCompleted);
    }
    @Test
    void deleteTask_shouldReturnTrue() {
        when(taskRepository.existsById(1L)).thenReturn(true);

        boolean result = taskService.deleteTask(1L);

        assertThat(result).isTrue();
        verify(taskRepository).deleteById(1L);
    }
    @Test
    void deleteTask_shouldReturnFalseForNonExistentTask() {
        when(taskRepository.existsById(1L)).thenReturn(false);

        boolean result = taskService.deleteTask(1L);

        assertThat(result).isFalse();
        verify(taskRepository, never()).deleteById(1L);
    }
    @Test
    void updateTask_shouldReturnUpdatedTask() {
        Task exTask =  Task.builder()
                .taskName("First task")
                .completed(false).build();
        Task updatedTask =  Task.builder()
                .taskName("Updated task")
                .completed(false).build();
        when(taskRepository.getById(exTask.getId())).thenReturn(exTask);
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);

        Task result = taskService.updateTask(updatedTask);

        assertThat(result.getTaskName()).isEqualTo(updatedTask.getTaskName());
    }
}
