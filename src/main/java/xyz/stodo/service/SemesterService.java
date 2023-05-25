package xyz.stodo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.stodo.entity.Semester;
import xyz.stodo.entity.User;
import xyz.stodo.exception.SemesterNotFoundException;
import xyz.stodo.factories.SimpleResponseDtoFactory;
import xyz.stodo.payload.dto.TitleRequestDto;
import xyz.stodo.payload.dto.SimpleResponseDto;
import xyz.stodo.repository.SemesterRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class SemesterService {
    @Autowired
    private SemesterRepository semesterRepository;

    @Autowired
    private SimpleResponseDtoFactory simpleResponseDtoFactory;

    public SimpleResponseDto createSemester(TitleRequestDto semesterDto, User user) {
        Semester semester = new Semester();
        semester.setTitle(semesterDto.getTitle());
        semester.setUser(user);
        semesterRepository.save(semester);

        return simpleResponseDtoFactory.makeSimpleResponseDto(semester);
    }

    public List<SimpleResponseDto> getAllSemestersForUser(User user) {
        List<Semester> semesters = semesterRepository.findAllByUserOrderByCreatedAt(user);
        List<SimpleResponseDto> semesterResponseDtoList =
                new ArrayList<>(semesters.size());

        semesters.forEach(semester -> {
            semesterResponseDtoList.add(simpleResponseDtoFactory.makeSimpleResponseDto(semester));
        });

        return semesterResponseDtoList;
    }


    public SimpleResponseDto updateSemester(Long id,
                                            TitleRequestDto semesterDto,
                                            User user) {
        Semester semester = getSemesterByIdAndUser(id, user);
        semester.setTitle(semesterDto.getTitle());
        semesterRepository.save(semester);

        return simpleResponseDtoFactory.makeSimpleResponseDto(semester);
    }

    public SimpleResponseDto deleteSemester(Long id, User user) {
        Semester semester = getSemesterByIdAndUser(id, user);
        semesterRepository.delete(semester);

        return simpleResponseDtoFactory.makeSimpleResponseDto(semester);
    }

    public Semester getSemesterByIdAndUser(Long id, User user) {
        return semesterRepository
                .findByIdAndUser(id, user)
                .orElseThrow(() ->
                        new SemesterNotFoundException(
                                "Semester cannot be found for username: " + user.getEmail())
                );
    }
}
