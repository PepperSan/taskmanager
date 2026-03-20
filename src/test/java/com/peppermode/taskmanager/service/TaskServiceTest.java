package com.peppermode.taskmanager.service;

import com.peppermode.taskmanager.entity.Task;
import com.peppermode.taskmanager.entity.TaskStatus;
import com.peppermode.taskmanager.entity.User;
import com.peppermode.taskmanager.exception.ResourceNotFoundException;
import com.peppermode.taskmanager.repository.TaskRepository;
import com.peppermode.taskmanager.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void shouldReturnTaskWhenFound() {
        Long taskId = 1L;

        Task task = new Task();
        task.setId(taskId);
        task.setTitle("Complete homework");
        task.setDescription("Finish math homework");
        task.setDeadline(LocalDate.of(2026, 3, 20));
        task.setStatus(TaskStatus.PENDING);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        Task result = taskService.getById(taskId);

        assertNotNull(result);
        assertEquals("Complete homework", result.getTitle());
        assertEquals("Finish math homework", result.getDescription());
        assertEquals(TaskStatus.PENDING, result.getStatus());

        verify(taskRepository, times(1)).findById(taskId);
    }

    @Test
    void shouldThrowExceptionWhenTaskNotFound() {
        Long taskId = 999L;

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> taskService.getById(taskId)
        );

        assertEquals("Task not found with id: 999", exception.getMessage());
        verify(taskRepository, times(1)).findById(taskId);
    }

    @Test
    void shouldCreateTaskWhenUserExists() {
        Long userId = 1L;

        User user = new User();
        user.setId(userId);

        Task task = new Task();
        task.setTitle("Learn Spring");
        task.setDescription("Finish controllers and services");
        task.setDeadline(LocalDate.of(2026, 3, 30));
        task.setStatus(TaskStatus.PENDING);
        task.setUser(user);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(taskRepository.save(task)).thenReturn(task);

        Task result = taskService.create(task);

        assertNotNull(result);
        assertEquals("Learn Spring", result.getTitle());
        assertEquals(user, result.getUser());

        verify(userRepository, times(1)).findById(userId);
        verify(taskRepository, times(1)).save(task);
    }
}