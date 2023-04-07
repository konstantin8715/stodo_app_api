package xyz.stodo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.stodo.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
