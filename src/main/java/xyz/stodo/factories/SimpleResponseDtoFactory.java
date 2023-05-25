package xyz.stodo.factories;

import org.springframework.stereotype.Component;
import xyz.stodo.entity.Semester;
import xyz.stodo.payload.dto.SimpleResponseDto;

@Component
public class SimpleResponseDtoFactory {

    public SimpleResponseDto makeSimpleResponseDto(Semester semester) {
        SimpleResponseDto semesterResponseDto = new SimpleResponseDto();
        semesterResponseDto.setId(semester.getId());
        semesterResponseDto.setTitle(semester.getTitle());
        semesterResponseDto.setCreatedAt(semester.getCreatedAt());

        return semesterResponseDto;
    }

}
