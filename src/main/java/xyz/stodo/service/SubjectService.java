package xyz.stodo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.stodo.entity.Semester;
import xyz.stodo.entity.Subject;
import xyz.stodo.entity.User;
import xyz.stodo.payload.dto.SimpleResponseDto;
import xyz.stodo.payload.dto.TitleRequestDto;
import xyz.stodo.repository.SubjectRepository;

@Service
public class SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private SemesterService semesterService;

    public SimpleResponseDto createSubject(TitleRequestDto titleRequestDto, Long semesterId, User user) {
        Semester semester = semesterService.getSemesterByIdAndUser(semesterId, user);

        Subject subject = new Subject();
        subject.setSemester(semester);
        subject.setTitle(titleRequestDto.getTitle());

        subjectRepository.save(subject);

        SimpleResponseDto subjectResponseDto = new SimpleResponseDto();
        subjectResponseDto.setId(subject.getId());
        subjectResponseDto.setTitle(subject.getTitle());
        subjectResponseDto.setCreatedAt(subject.getCreatedAt());

        return subjectResponseDto;
    }

    public SimpleResponseDto deleteSubject(String subjectId, User user) {
        return null;
    }
}
