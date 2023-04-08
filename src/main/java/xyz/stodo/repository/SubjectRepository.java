package xyz.stodo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.stodo.entity.Semester;
import xyz.stodo.entity.Subject;

import java.util.List;
import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Optional<Subject> findByIdAndSemester(Long id, Semester semester);

    List<Subject> findAllBySemesterOrderByCreatedAt(Semester semester);
}
