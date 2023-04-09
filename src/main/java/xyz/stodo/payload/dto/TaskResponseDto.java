package xyz.stodo.payload.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDate;

@Data
public class TaskResponseDto {
    private Long id;
    private String title;
    private boolean isDone;
    private LocalDate deadlineDate;
}
