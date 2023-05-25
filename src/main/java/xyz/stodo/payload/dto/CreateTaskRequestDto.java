package xyz.stodo.payload.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class CreateTaskRequestDto {
    @NotBlank(message = "title should not be empty")
    private String title;

    private LocalDateTime deadlineDate;
}
