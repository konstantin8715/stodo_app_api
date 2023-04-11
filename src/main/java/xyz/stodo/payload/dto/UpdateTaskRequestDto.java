package xyz.stodo.payload.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
public class UpdateTaskRequestDto {
    private String title;

    @JsonFormat(pattern = "yyyy-mm-dd")
    private LocalDate deadlineDate;
}
