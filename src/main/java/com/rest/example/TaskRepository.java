package com.rest.example;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // Derived Query Methods: Spring automatically parses the method name and generates SQL!
    // Equivalent SQL: SELECT * FROM tasks WHERE completed = ?;
    List<Task> findByCompleted(boolean completed);

    // Equivalent SQL: SELECT * FROM tasks WHERE title LIKE %?%;
    List<Task> findByTitleContainingIgnoreCase(String keyword);

}