package com.peppermode.taskmanager.service;

import com.peppermode.taskmanager.entity.Task;
import com.peppermode.taskmanager.entity.TaskStatus;
import com.peppermode.taskmanager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public Task getById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
    }

    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    public Task create(Task task) {
        return taskRepository.save(task);
    }

    public Task update(Long id, Task updatedTask) {
        Task existingTask = getById(id);

        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setDeadline(updatedTask.getDeadline());
        existingTask.setStatus(updatedTask.getStatus());
        existingTask.setUser(updatedTask.getUser());

        return taskRepository.save(existingTask);
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    public List<Task> getByUserId(Long userId) {
        return taskRepository.findByUserId(userId);
    }

    public List<Task> getByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status);
    }
}