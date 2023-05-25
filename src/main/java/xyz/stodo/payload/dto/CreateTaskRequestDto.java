package xyz.stodo.payload.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CreateTaskRequestDto {
    @NotBlank(message = "title should not be empty")
    private String title;

    private LocalDateTime deadlineDate;
}
