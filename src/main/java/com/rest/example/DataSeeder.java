package com.rest.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private final TaskRepository taskRepository;

    public DataSeeder(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    public void run(String... args) throws Exception {
        System.out.println("Total tasks in database: " + taskRepository.count());
        if(taskRepository.count() == 0){
            List<Task> initialTasks = new ArrayList<>();
            for(int i = 1; i <= 100; i++) {
                boolean isCompleted = (i % 2 == 0);
                Task task = new Task("Task " + i, isCompleted);
                initialTasks.add(task);
            }
            taskRepository.saveAll(initialTasks);
            System.out.println("Successfully seeded " + initialTasks.size() + " tasks into the database.");
        }
        System.out.println("Total tasks in database after seeding: " + taskRepository.count());
    }

}
