package com.example.demo.repositories;

import com.example.demo.models.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ActiveProfiles("test")
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void save_shouldReturnSavedTask(){
        Task task = Task.builder()
                .taskName("first test")
                .completed(true)
                .build();

        Task savedTask = taskRepository.save(task);

        assertThat(savedTask).isNotNull();
        assertThat(savedTask.getId()).isPositive();
    }
    @Test
    void findAll_shouldReturnMoreThenOneTask() {
        Task task = Task.builder()
                .taskName("numero uno")
                .completed(true).build();
        Task task2 = Task.builder()
                .taskName("numero due")
                .completed(false).build();

        taskRepository.save(task);
        taskRepository.save(task2);
        List<Task> taskList = taskRepository.findAll();

        assertThat(taskList).isNotNull()
                .hasSize(2);
    }
    @Test
    void getById_shouldReturnTask() {
        Task task = Task.builder()
                .taskName("numero uno")
                .completed(true).build();
        taskRepository.save(task);
        Task taskic = taskRepository.getById(task.getId());
        assertThat(taskic).isNotNull();
    }
    @Test
    void findByCompletedTrue_shouldReturnTwoCompletedTasks() {
        List<Task> tasks = List.of(
                Task.builder().taskName("numero uno").completed(true).build(),
                Task.builder().taskName("numbero due").completed(false).build(),
                Task.builder().taskName("numbero tre").completed(true).build()
        );
        tasks.forEach(taskRepository::save);
        List<Task> completed = taskRepository.findByCompletedTrue();

        assertThat(completed).hasSize(2)
                .allMatch(task->task.getCompleted().equals(true));
    }
    @Test
    void findByCompletedFalse_shouldReturnOneIncompleteTask() {
        List<Task> tasks = List.of(
                Task.builder().taskName("numero uno").completed(true).build(),
                Task.builder().taskName("numbero due").completed(false).build(),
                Task.builder().taskName("numbero tre").completed(true).build()
        );
        tasks.forEach(taskRepository::save);
        List<Task> completed = taskRepository.findByCompletedFalse();

        assertThat(completed).hasSize(1)
                .allMatch(task->task.getCompleted().equals(false));
    }
    @Test
    void updateTask_shouldReturnUpdatedTask() {
        Task task = Task.builder()
                .taskName("numero uno")
                .completed(true).build();
        taskRepository.save(task);
        Task current = taskRepository.getById(task.getId());

        current.setTaskName("updated uno");
        current.setCompleted(false);
        Task updated = taskRepository.save(current);

        assertThat(updated.getCompleted()).isFalse();
        assertThat(updated.getTaskName()).isEqualTo("updated uno");
    }
    @Test
    void deleteTask_shouldReturnTaskIsEmpty() {
        Task task = Task.builder()
                .taskName("numero uno")
                .completed(true).build();

        taskRepository.save(task);
        taskRepository.delete(task);

        Optional<Task> taskReturn = taskRepository.findById(task.getId());

        assertThat(taskReturn).isEmpty();
    }

}
