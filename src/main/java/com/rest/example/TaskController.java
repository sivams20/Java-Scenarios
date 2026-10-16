package com.rest.example;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

//    @GetMapping
//    public List<TaskDTOs.TaskResponse> getAllTasks() {
//        return taskService.getAllTasks();
//    }

    @GetMapping
    public Page<TaskDTOs.TaskResponse> getTasks(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return taskService.getPaginatedTasks(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTOs.TaskResponse createTask(@Valid @RequestBody TaskDTOs.CreateTaskRequest request) {
        return taskService.createTask(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTOs.TaskResponse> getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}