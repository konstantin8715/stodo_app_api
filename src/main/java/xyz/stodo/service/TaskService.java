package xyz.stodo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.stodo.entity.Semester;
import xyz.stodo.entity.Subject;
import xyz.stodo.entity.Task;
import xyz.stodo.entity.User;
import xyz.stodo.exception.SemesterNotFoundException;
import xyz.stodo.factories.TaskResponseDtoFactory;
import xyz.stodo.payload.dto.CreateTaskRequestDto;
import xyz.stodo.payload.dto.TaskResponseDto;
import xyz.stodo.payload.dto.UpdateTaskRequestDto;
import xyz.stodo.repository.TaskRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private SemesterService semesterService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private TaskResponseDtoFactory taskResponseDtoFactory;

    public TaskResponseDto createTask(Long semesterId, Long subjectId,
                                      CreateTaskRequestDto createTaskRequestDto, User user) {
        Semester semester = semesterService.getSemesterByIdAndUser(semesterId, user);
        Subject subject = subjectService.getSubjectByIdAndSemester(subjectId, semester);

        Task task = new Task();
        task.setTitle(createTaskRequestDto.getTitle());
        task.setDeadlineDate(createTaskRequestDto.getDeadlineDate());
        task.setSubject(subject);
        taskRepository.save(task);

        return taskResponseDtoFactory.makeTaskResponseDto(task);
    }

    public List<TaskResponseDto> getAllTasksForSemesterAndSubject(Long semesterId, Long subjectId, User user) {
        Semester semester = semesterService.getSemesterByIdAndUser(semesterId, user);
        Subject subject = subjectService.getSubjectByIdAndSemester(subjectId, semester);
        List<Task> tasks = taskRepository.findAllBySubjectOrderByCreatedAt(subject);
        List<TaskResponseDto> taskResponseDtoList = new ArrayList<>();

        tasks.forEach(task -> {
            taskResponseDtoList.add(taskResponseDtoFactory.makeTaskResponseDto(task));
        });

        return taskResponseDtoList;
    }

    public List<TaskResponseDto> getAllTasksForUser(User user) {
        List<Semester> userSemesters = user.getSemesters().stream().toList();
        List<Subject> userSubjects = new ArrayList<>();
        List<Task> userTasks = new ArrayList<>();

        userSemesters.forEach(semester -> {
            userSubjects.addAll(semester.getSubjects());
        });

        userSubjects.forEach(subject -> {
            userTasks.addAll(subject.getTasks());
        });

        List<TaskResponseDto> taskResponseDtoList = new ArrayList<>();

        userTasks.forEach(task -> {
            taskResponseDtoList.add(taskResponseDtoFactory.makeTaskResponseDto(task));
        });

        return taskResponseDtoList;
    }

    public TaskResponseDto deleteTask(Long semesterId, Long subjectId, Long taskId, User user) {
        Semester semester = semesterService.getSemesterByIdAndUser(semesterId, user);
        Subject subject = subjectService.getSubjectByIdAndSemester(subjectId, semester);
        Task task = getTaskByIdAndSubject(taskId, subject);
        taskRepository.delete(task);

        return taskResponseDtoFactory.makeTaskResponseDto(task);
    }

    public Task getTaskByIdAndSubject(Long id, Subject subject) {
        return taskRepository
                .findByIdAndSubject(id, subject)
                .orElseThrow(() ->
                        new SemesterNotFoundException(
                                "Task cannot be found for subject: " + subject.getId())
                );
    }

    public TaskResponseDto changeTaskStatus(Long semesterId, Long subjectId, Long taskId, User user) {
        Semester semester = semesterService.getSemesterByIdAndUser(semesterId, user);
        Subject subject = subjectService.getSubjectByIdAndSemester(subjectId, semester);
        Task task = getTaskByIdAndSubject(taskId, subject);
        task.setDone(!task.isDone());
        taskRepository.save(task);

        return taskResponseDtoFactory.makeTaskResponseDto(task);
    }

    public TaskResponseDto updateTask(Long semesterId, Long subjectId, Long taskId,
                                      UpdateTaskRequestDto updateTaskRequestDto, User user) {
        Semester semester = semesterService.getSemesterByIdAndUser(semesterId, user);
        Subject subject = subjectService.getSubjectByIdAndSemester(subjectId, semester);
        Task task = getTaskByIdAndSubject(taskId, subject);

        if (updateTaskRequestDto.getTitle() != null) task.setTitle(updateTaskRequestDto.getTitle());
        if (updateTaskRequestDto.getDeadlineDate() != null)
            task.setDeadlineDate(updateTaskRequestDto.getDeadlineDate());

        taskRepository.save(task);

        return taskResponseDtoFactory.makeTaskResponseDto(task);
    }
}
