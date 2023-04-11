package xyz.stodo.payload.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
public class CreateTaskRequestDto {
    @NotBlank(message = "title should not be empty")
    private String title;

    @NotBlank(message = "deadline date should not be empty")
    @JsonFormat(pattern = "yyyy-mm-dd")
    private LocalDate deadlineDate;
}
