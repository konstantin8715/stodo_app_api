package xyz.stodo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import xyz.stodo.entity.Semester;
import xyz.stodo.entity.Subject;
import xyz.stodo.entity.Task;
import xyz.stodo.entity.User;
import xyz.stodo.exception.SemesterNotFoundException;
import xyz.stodo.payload.dto.CreateTaskRequestDto;
import xyz.stodo.payload.dto.TaskResponseDto;
import xyz.stodo.payload.dto.UpdateTaskRequestDto;
import xyz.stodo.repository.TaskRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    // TODO: 4/11/23 Зарефакторить 
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private SemesterService semesterService;

    @Autowired
    private SubjectService subjectService;

    public TaskResponseDto createTask(Long semesterId, Long subjectId,
                                      CreateTaskRequestDto createTaskRequestDto, User user) {
        Semester semester = semesterService.getSemesterByIdAndUser(semesterId, user);
        Subject subject = subjectService.getSubjectByIdAndSemester(subjectId, semester);

        Task task = new Task();
        task.setTitle(createTaskRequestDto.getTitle());
        task.setDeadlineDate(createTaskRequestDto.getDeadlineDate());
        task.setSubject(subject);

        taskRepository.save(task);

        TaskResponseDto taskResponseDto = new TaskResponseDto();
        taskResponseDto.setId(task.getId());
        taskResponseDto.setTitle(task.getTitle());
        taskResponseDto.setDone(task.isDone());
        taskResponseDto.setCreatedAt(task.getCreatedAt());
        taskResponseDto.setDeadlineDate(task.getDeadlineDate());

        return taskResponseDto;
    }

    public List<TaskResponseDto> getAllTasksForSubject(Long semesterId, Long subjectId, User user) {
        Semester semester = semesterService.getSemesterByIdAndUser(semesterId, user);
        Subject subject = subjectService.getSubjectByIdAndSemester(subjectId, semester);

        List<Task> tasks = taskRepository.findAllBySubjectOrderByCreatedAt(subject);
        List<TaskResponseDto> taskResponseDtoList = new ArrayList<>();

        for (Task task:
                tasks) {
            TaskResponseDto taskResponseDto = new TaskResponseDto();
            taskResponseDto.setId(task.getId());
            taskResponseDto.setTitle(task.getTitle());
            taskResponseDto.setDone(task.isDone());
            taskResponseDto.setCreatedAt(task.getCreatedAt());
            taskResponseDto.setDeadlineDate(task.getDeadlineDate());

            taskResponseDtoList.add(taskResponseDto);
        }

        return taskResponseDtoList;
    }

    public TaskResponseDto deleteTask(Long semesterId, Long subjectId, Long taskId, User user) {
        Semester semester = semesterService.getSemesterByIdAndUser(semesterId, user);
        Subject subject = subjectService.getSubjectByIdAndSemester(subjectId, semester);
        Task task = getTaskByIdAndSubject(taskId, subject);

        taskRepository.delete(task);

        TaskResponseDto taskResponseDto = new TaskResponseDto();
        taskResponseDto.setId(task.getId());
        taskResponseDto.setTitle(task.getTitle());
        taskResponseDto.setDone(task.isDone());
        taskResponseDto.setCreatedAt(task.getCreatedAt());
        taskResponseDto.setDeadlineDate(task.getDeadlineDate());

        return taskResponseDto;
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

        TaskResponseDto taskResponseDto = new TaskResponseDto();
        taskResponseDto.setId(task.getId());
        taskResponseDto.setTitle(task.getTitle());
        taskResponseDto.setDone(task.isDone());
        taskResponseDto.setCreatedAt(task.getCreatedAt());
        taskResponseDto.setDeadlineDate(task.getDeadlineDate());

        return taskResponseDto;
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

        TaskResponseDto taskResponseDto = new TaskResponseDto();
        taskResponseDto.setId(task.getId());
        taskResponseDto.setTitle(task.getTitle());
        taskResponseDto.setDone(task.isDone());
        taskResponseDto.setCreatedAt(task.getCreatedAt());
        taskResponseDto.setDeadlineDate(task.getDeadlineDate());

        return taskResponseDto;
    }
}
