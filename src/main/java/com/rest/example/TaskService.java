package com.rest.example;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<TaskDTOs.TaskResponse> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(TaskDTOs.TaskResponse::fromEntity)
                .toList();
    }

    @Transactional
    public TaskDTOs.TaskResponse createTask(TaskDTOs.CreateTaskRequest request) {
        Task entity = new Task(request.title(), false);
        Task savedEntity = taskRepository.save(entity);
        return TaskDTOs.TaskResponse.fromEntity(savedEntity);
    }

    public Optional<TaskDTOs.TaskResponse> getTaskById(Long id) {
        return taskRepository.findById(id)
                .map(TaskDTOs.TaskResponse::fromEntity);
    }

    public Page<TaskDTOs.TaskResponse> getPaginatedTasks(Pageable pageable){
        return taskRepository.findAll(pageable).map(TaskDTOs.TaskResponse::fromEntity);
    }

}