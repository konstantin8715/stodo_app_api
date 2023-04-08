package xyz.stodo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.stodo.entity.Semester;
import xyz.stodo.entity.User;

import java.util.List;
import java.util.Optional;

public interface SemesterRepository extends JpaRepository<Semester, Long> {
    List<Semester> findAllByUserOrderByCreatedAt(User user);

    Optional<Semester> findByIdAndUser(Long id, User user);
}
