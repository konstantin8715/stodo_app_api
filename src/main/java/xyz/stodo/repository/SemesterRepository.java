package xyz.stodo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.stodo.entity.Semester;
import xyz.stodo.entity.User;

import java.util.List;

public interface SemesterRepository extends JpaRepository<Semester, Long> {
    List<Semester> findAllByUser(User user);
}
