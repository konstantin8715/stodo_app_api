package xyz.stodo.payload.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TitleRequestDto {
    @NotBlank(message = "title should not be empty")
    private String title;
}
