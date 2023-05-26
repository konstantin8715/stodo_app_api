package xyz.stodo.factory;

import org.springframework.stereotype.Component;
import xyz.stodo.entity.Semester;
import xyz.stodo.entity.Subject;
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

    public SimpleResponseDto makeSimpleResponseDto(Subject subject) {
        SimpleResponseDto subjectResponseDto = new SimpleResponseDto();
        subjectResponseDto.setId(subject.getId());
        subjectResponseDto.setTitle(subject.getTitle());
        subjectResponseDto.setCreatedAt(subject.getCreatedAt());

        return subjectResponseDto;
    }
}
