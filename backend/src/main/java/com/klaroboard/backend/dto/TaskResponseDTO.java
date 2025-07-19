package com.klaroboard.backend.dto;

import com.klaroboard.backend.model.TaskStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskResponseDTO {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private UserResponseDTO assignee; // Embed the UserResponseDTO for assignee details
}
