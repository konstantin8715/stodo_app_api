package xyz.stodo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import xyz.stodo.entity.User;
import xyz.stodo.payload.dto.SemesterRequestDto;
import xyz.stodo.payload.dto.SemesterResponseDto;
import xyz.stodo.service.SemesterService;
import xyz.stodo.service.UserService;
import xyz.stodo.validation.RequestErrorValidation;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/semester")
public class SemesterController {
    @Autowired
    private SemesterService semesterService;

    @Autowired
    private RequestErrorValidation requestErrorValidation;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Object> createSemester(@Valid @RequestBody SemesterRequestDto semesterDto,
                                                 BindingResult bindingResult,
                                                 Principal principal) {
        ResponseEntity<Object> errors = requestErrorValidation.getErrors(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        User user = userService.getUserByPrincipal(principal);

        return ResponseEntity.ok(semesterService.createSemester(semesterDto, user));
    }

    @GetMapping
    public ResponseEntity<List<SemesterResponseDto>> getAllSemestersForUser(Principal principal) {
        User user = userService.getUserByPrincipal(principal);

        return ResponseEntity.ok(semesterService.getAllSemestersForUser(user));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateSemester(@PathVariable String id,
                                                 @Valid @RequestBody SemesterRequestDto semesterRequestDto,
                                                 BindingResult bindingResult,
                                                 Principal principal) {
        ResponseEntity<Object> errors = requestErrorValidation.getErrors(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        User user = userService.getUserByPrincipal(principal);

        return ResponseEntity.ok(semesterService.updateSemester(Long.parseLong(id), semesterRequestDto, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSemester(@PathVariable String id, Principal principal) {
        User user = userService.getUserByPrincipal(principal);

        return ResponseEntity.ok(semesterService.deleteSemester(Long.parseLong(id), user));
    }
}