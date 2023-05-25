package xyz.stodo.payload.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TaskResponseDto {
    private Long id;
    private String title;
    private boolean isDone;
    private LocalDateTime createdAt;
    private LocalDateTime deadlineDate;
}
