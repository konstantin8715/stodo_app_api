package xyz.stodo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import xyz.stodo.entity.User;
import xyz.stodo.payload.dto.TitleRequestDto;
import xyz.stodo.service.SubjectService;
import xyz.stodo.service.UserService;
import xyz.stodo.validation.RequestErrorValidation;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/subject")
public class SubjectController {
    @Autowired
    private RequestErrorValidation requestErrorValidation;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private UserService userService;

    @PostMapping("/{semesterId}")
    public ResponseEntity<Object> createSubject(@Valid @RequestBody TitleRequestDto titleRequestDto,
                                                @PathVariable String semesterId,
                                                BindingResult bindingResult,
                                                Principal principal) {
        ResponseEntity<Object> errors = requestErrorValidation.getErrors(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        User user = userService.getUserByPrincipal(principal);

        return ResponseEntity.ok(subjectService.createSubject(titleRequestDto, Long.parseLong(semesterId), user));
    }

    @DeleteMapping("/{subjectId}")
    public ResponseEntity<Object> deleteSubject(@PathVariable String subjectId, Principal principal) {
        User user = userService.getUserByPrincipal(principal);

        return ResponseEntity.ok(subjectService.deleteSubject(subjectId, user));
    }
}