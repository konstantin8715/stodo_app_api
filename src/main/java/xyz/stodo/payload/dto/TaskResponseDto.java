package xyz.stodo.payload.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TaskResponseDto {
    private Long id;
    private String title;
    private boolean isDone;
    private LocalDateTime createdAt;
    private LocalDateTime deadlineDate;
}
