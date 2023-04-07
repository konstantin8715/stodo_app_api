package xyz.stodo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.stodo.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
