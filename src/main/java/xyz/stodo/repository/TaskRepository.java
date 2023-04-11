package xyz.stodo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.stodo.entity.Subject;
import xyz.stodo.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByIdAndSubject(Long id, Subject subject);
    List<Task> findAllBySubjectOrderByCreatedAt(Subject subject);
}
