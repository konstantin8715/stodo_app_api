package xyz.stodo.payload.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateTaskRequestDto {
    private String title;

//    @JsonFormat(pattern = "yyyy-mm-dd")
    private LocalDateTime deadlineDate;
}
