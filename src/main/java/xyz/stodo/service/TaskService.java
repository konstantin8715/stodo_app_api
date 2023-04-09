package xyz.stodo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.stodo.entity.Semester;
import xyz.stodo.entity.Subject;
import xyz.stodo.entity.Task;
import xyz.stodo.entity.User;
import xyz.stodo.payload.dto.TaskRequestDto;
import xyz.stodo.payload.dto.TaskResponseDto;
import xyz.stodo.repository.TaskRepository;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private SemesterService semesterService;

    @Autowired
    private SubjectService subjectService;

    public TaskResponseDto createTask(Long semesterId, Long subjectId,
                                      TaskRequestDto taskRequestDto, User user) {
        Semester semester = semesterService.getSemesterByIdAndUser(semesterId, user);
        Subject subject = subjectService.getSubjectByIdAndSemester(subjectId, semester);

        Task task = new Task();
        task.setTitle(taskRequestDto.getTitle());
        task.setDeadlineDate(taskRequestDto.getDeadlineDate());
        task.setSubject(subject);

        taskRepository.save(task);

        TaskResponseDto taskResponseDto = new TaskResponseDto();
        taskResponseDto.setId(task.getId());
        taskResponseDto.setDone(task.isDone());
        taskResponseDto.setDeadlineDate(task.getDeadlineDate());

        return taskResponseDto;
    }
}
