package xyz.stodo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import xyz.stodo.entity.User;
import xyz.stodo.payload.dto.TaskRequestDto;
import xyz.stodo.service.UserService;
import xyz.stodo.service.TaskService;
import xyz.stodo.validation.RequestErrorValidation;

import javax.validation.Valid;
import java.security.Principal;

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
    public ResponseEntity<Object> createTask(@Valid @RequestBody TaskRequestDto taskRequestDto,
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
}
