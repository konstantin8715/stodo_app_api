package xyz.stodo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private boolean isDone;
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-mm-dd")
    private LocalDate deadlineDate;
    @ManyToOne(fetch = FetchType.LAZY)
    private Subject subject;
    public LocalDateTime createdAt;

    @PrePersist
    private void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
