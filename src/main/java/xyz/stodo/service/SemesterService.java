package xyz.stodo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.stodo.entity.Semester;
import xyz.stodo.entity.User;
import xyz.stodo.exception.SemesterNotFoundException;
import xyz.stodo.payload.dto.SemesterRequestDto;
import xyz.stodo.payload.dto.SemesterResponseDto;
import xyz.stodo.repository.SemesterRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class SemesterService {
    // TODO: 4/7/23 Зарефакторить 
    @Autowired
    private SemesterRepository semesterRepository;

    public SemesterResponseDto createSemester(SemesterRequestDto semesterDto, User user) {
        Semester semester = new Semester();
        semester.setTitle(semesterDto.getTitle());
        semester.setUser(user);

        Semester savedSemester = semesterRepository.save(semester);

        SemesterResponseDto semesterResponseDto = new SemesterResponseDto();
        semesterResponseDto.setId(semester.getId());
        semesterResponseDto.setTitle(semester.getTitle());
        semesterResponseDto.setCreatedAt(semester.getCreatedAt());

        return semesterResponseDto;
    }

    public List<SemesterResponseDto> getAllSemestersForUser(User user) {
        List<Semester> semesters = semesterRepository.findAllByUserOrderByCreatedAt(user);
        List<SemesterResponseDto> semesterResponseDtoList =
                new ArrayList<>(semesters.size());

        for (Semester semester:
             semesters) {
            SemesterResponseDto semesterResponseDto = new SemesterResponseDto();
            semesterResponseDto.setId(semester.getId());
            semesterResponseDto.setTitle(semester.getTitle());
            semesterResponseDto.setCreatedAt(semester.getCreatedAt());

            semesterResponseDtoList.add(semesterResponseDto);
        }

        return semesterResponseDtoList;
    }


    public SemesterResponseDto updateSemester(Long id,
                                              SemesterRequestDto semesterDto,
                                              User user) {
        Semester semester = getSemesterByIdAndUser(id, user);

        semester.setTitle(semesterDto.getTitle());

        semesterRepository.save(semester);

        SemesterResponseDto semesterResponseDto = new SemesterResponseDto();
        semesterResponseDto.setId(semester.getId());
        semesterResponseDto.setTitle(semester.getTitle());
        semesterResponseDto.setCreatedAt(semester.getCreatedAt());

        return semesterResponseDto;
    }

    public SemesterResponseDto deleteSemester(Long id, User user) {
        Semester semester = getSemesterByIdAndUser(id, user);

        semesterRepository.delete(semester);

        SemesterResponseDto semesterResponseDto = new SemesterResponseDto();
        semesterResponseDto.setId(semester.getId());
        semesterResponseDto.setTitle(semester.getTitle());
        semesterResponseDto.setCreatedAt(semester.getCreatedAt());

        return semesterResponseDto;
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
