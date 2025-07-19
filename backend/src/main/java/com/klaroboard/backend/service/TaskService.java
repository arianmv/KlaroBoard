package com.klaroboard.backend.service;

import com.klaroboard.backend.dto.TaskRequestDTO;
import com.klaroboard.backend.dto.TaskResponseDTO;
import com.klaroboard.backend.dto.UserResponseDTO;
import com.klaroboard.backend.model.Task;
import com.klaroboard.backend.model.User;
import com.klaroboard.backend.repository.TaskRepository;
import com.klaroboard.backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<TaskResponseDTO> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public TaskResponseDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));
        return convertToResponseDTO(task);
    }

    public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO) {
        Task task = new Task();
        task.setTitle(taskRequestDTO.getTitle());
        task.setDescription(taskRequestDTO.getDescription());
        task.setStatus(taskRequestDTO.getStatus());

        if (taskRequestDTO.getAssigneeId() != null) {
            User assignee = userRepository.findById(taskRequestDTO.getAssigneeId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + taskRequestDTO.getAssigneeId()));
            task.setAssignee(assignee);
        }

        Task savedTask = taskRepository.save(task);
        return convertToResponseDTO(savedTask);
    }

    public TaskResponseDTO updateTask(Long id, TaskRequestDTO taskRequestDTO) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));

        existingTask.setTitle(taskRequestDTO.getTitle());
        existingTask.setDescription(taskRequestDTO.getDescription());
        existingTask.setStatus(taskRequestDTO.getStatus());

        if (taskRequestDTO.getAssigneeId() != null) {
            User assignee = userRepository.findById(taskRequestDTO.getAssigneeId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + taskRequestDTO.getAssigneeId()));
            existingTask.setAssignee(assignee);
        } else {
            existingTask.setAssignee(null);
        }

        Task updatedTask = taskRepository.save(existingTask);
        return convertToResponseDTO(updatedTask);
    }

    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new EntityNotFoundException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }

    // Helper method to convert a Task entity to a TaskResponseDTO
    private TaskResponseDTO convertToResponseDTO(Task task) {
        TaskResponseDTO dto = new TaskResponseDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus());

        if (task.getAssignee() != null) {
            UserResponseDTO userDTO = new UserResponseDTO();
            userDTO.setId(task.getAssignee().getId());
            userDTO.setName(task.getAssignee().getName());
            userDTO.setEmail(task.getAssignee().getEmail());
            dto.setAssignee(userDTO);
        }

        return dto;
    }
}