package com.rest.example;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TaskDTOs {

    // Request DTO with strict field rules
    public record CreateTaskRequest(
            @NotBlank(message = "Task title cannot be blank")
            @Size(min = 3, max = 100, message = "Task title must be between 3 and 100 characters")
            String title
    ) {}

    // Response DTO
    public record TaskResponse(
            Long id,
            String title,
            boolean completed
    ) {
        public static TaskResponse fromEntity(Task task) {
            return new TaskResponse(task.getId(), task.getTitle(), task.isCompleted());
        }
    }
}