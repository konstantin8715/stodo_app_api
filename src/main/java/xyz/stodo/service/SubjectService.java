package xyz.stodo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.stodo.entity.Semester;
import xyz.stodo.entity.Subject;
import xyz.stodo.entity.User;
import xyz.stodo.exception.SemesterNotFoundException;
import xyz.stodo.payload.dto.SimpleResponseDto;
import xyz.stodo.payload.dto.TitleRequestDto;
import xyz.stodo.repository.SubjectRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectService {
    // TODO: 4/9/23 Зарефакторить
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

    public SimpleResponseDto deleteSubject(Long semesterId, Long subjectId, User user) {
        Semester semester = semesterService.getSemesterByIdAndUser(semesterId, user);
        Subject subject = getSubjectByIdAndSemester(subjectId, semester);
        
        subjectRepository.delete(subject);

        SimpleResponseDto subjectResponseDto = new SimpleResponseDto();
        subjectResponseDto.setId(subject.getId());
        subjectResponseDto.setTitle(subject.getTitle());
        subjectResponseDto.setCreatedAt(subject.getCreatedAt());

        return subjectResponseDto;
    }

    public Subject getSubjectByIdAndSemester(Long id, Semester semester) {
        return subjectRepository
                .findByIdAndSemester(id, semester)
                .orElseThrow(() ->
                        new SemesterNotFoundException(
                                "Subject cannot be found for semester: " + semester.getId())
                );
    }

    public SimpleResponseDto updateSubject(Long semesterId, Long subjectId,
                         TitleRequestDto titleRequestDto, User user) {
        Semester semester = semesterService.getSemesterByIdAndUser(semesterId, user);
        Subject subject = getSubjectByIdAndSemester(subjectId, semester);
        subject.setTitle(titleRequestDto.getTitle());

        subjectRepository.save(subject);

        SimpleResponseDto subjectResponseDto = new SimpleResponseDto();
        subjectResponseDto.setId(subject.getId());
        subjectResponseDto.setTitle(subject.getTitle());
        subjectResponseDto.setCreatedAt(subject.getCreatedAt());

        return subjectResponseDto;
    }

    public List<SimpleResponseDto> getAllSubjectsForSemester(Long semesterId, User user) {
        Semester semester = semesterService.getSemesterByIdAndUser(semesterId, user);
        List<Subject> subjects = subjectRepository.findAllBySemesterOrderByCreatedAt(semester);
        List<SimpleResponseDto> simpleResponseDtoList = new ArrayList<>();

        for (Subject subject:
             subjects) {
            SimpleResponseDto simpleResponseDto = new SimpleResponseDto();
            simpleResponseDto.setId(subject.getId());
            simpleResponseDto.setTitle(subject.getTitle());
            simpleResponseDto.setCreatedAt(subject.getCreatedAt());

            simpleResponseDtoList.add(simpleResponseDto);
        }

        return simpleResponseDtoList;
    }
}
