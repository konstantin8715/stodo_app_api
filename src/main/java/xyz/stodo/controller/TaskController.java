package xyz.stodo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import xyz.stodo.entity.User;
import xyz.stodo.payload.dto.CreateTaskRequestDto;
import xyz.stodo.payload.dto.TaskResponseDto;
import xyz.stodo.payload.dto.UpdateTaskRequestDto;
import xyz.stodo.service.UserService;
import xyz.stodo.service.TaskService;
import xyz.stodo.validation.RequestErrorValidation;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private RequestErrorValidation requestErrorValidation;

    @Autowired
    private UserService userService;

    @PostMapping("/{semesterId}/{subjectId}")
    public ResponseEntity<Object> createTask(@Valid @RequestBody CreateTaskRequestDto taskRequestDto,
                                             BindingResult bindingResult,
                                             @PathVariable String semesterId,
                                             @PathVariable String subjectId,
                                             Principal principal) {
        ResponseEntity<Object> errors = requestErrorValidation.getErrors(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        User user = userService.getUserByPrincipal(principal);

        return ResponseEntity.ok(taskService.createTask(Long.parseLong(semesterId),
                Long.parseLong(subjectId), taskRequestDto, user));
    }

    @GetMapping("/{semesterId}/{subjectId}")
    public ResponseEntity<List<TaskResponseDto>> getAllTasks(@PathVariable String semesterId,
                                                             @PathVariable String subjectId,
                                                             Principal principal) {
        User user = userService.getUserByPrincipal(principal);

        return ResponseEntity.ok(taskService.getAllTasksForSubject(Long.parseLong(semesterId),
                Long.parseLong(subjectId), user));
    }

    @DeleteMapping("/{semesterId}/{subjectId}/{taskId}")
    public ResponseEntity<TaskResponseDto> deleteTask(@PathVariable String semesterId,
                                                      @PathVariable String subjectId,
                                                      @PathVariable String taskId,
                                                      Principal principal) {
        User user = userService.getUserByPrincipal(principal);

        return ResponseEntity.ok(taskService.deleteTask(Long.parseLong(semesterId),
                Long.parseLong(subjectId), Long.parseLong(taskId), user));
    }

    @PatchMapping("/{semesterId}/{subjectId}/{taskId}")
    public ResponseEntity<TaskResponseDto> changeTaskStatus(@PathVariable String semesterId,
                                                            @PathVariable String subjectId,
                                                            @PathVariable String taskId,
                                                            Principal principal) {
        User user = userService.getUserByPrincipal(principal);

        return ResponseEntity.ok(taskService.changeTaskStatus(Long.parseLong(semesterId),
                Long.parseLong(subjectId), Long.parseLong(taskId), user));
    }

    @PatchMapping("/{semesterId}/{subjectId}/{taskId}/u")
    public ResponseEntity<Object> updateTask(@PathVariable String semesterId,
                                             @PathVariable String subjectId,
                                             @PathVariable String taskId,
                                             @Valid @RequestBody UpdateTaskRequestDto updateTaskRequestDto,
                                             BindingResult bindingResult,
                                             Principal principal) {
        ResponseEntity<Object> errors = requestErrorValidation.getErrors(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        User user = userService.getUserByPrincipal(principal);

        return ResponseEntity.ok(taskService.updateTask(Long.parseLong(semesterId),
                Long.parseLong(subjectId), Long.parseLong(taskId), updateTaskRequestDto, user));
    }
}
