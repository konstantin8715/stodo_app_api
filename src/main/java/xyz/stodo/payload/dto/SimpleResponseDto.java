package xyz.stodo.payload.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SimpleResponseDto {
    private Long id;
    private String title;
    private LocalDateTime createdAt;
}
