package com.klaroboard.backend.dto;

import com.klaroboard.backend.model.TaskStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskRequestDTO {
    private String title;
    private String description;
    private TaskStatus status;
    private Long assigneeId; // We only need the ID of the user to assign the task
}
