package xyz.stodo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.stodo.entity.Semester;

public interface SemesterRepository extends JpaRepository<Semester, Long> {
}
