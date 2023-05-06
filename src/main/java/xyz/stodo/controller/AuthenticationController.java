package xyz.stodo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import xyz.stodo.payload.*;
import xyz.stodo.security.JWTTokenProvider;
import xyz.stodo.security.SecurityConstants;
import xyz.stodo.service.MailSender;
import xyz.stodo.service.ResetPasswordService;
import xyz.stodo.service.UserService;
import xyz.stodo.validation.RequestErrorValidation;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthenticationController {
    @Autowired
    private JWTTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RequestErrorValidation requestErrorValidation;

    @Autowired
    private UserService userService;

    @Autowired
    private ResetPasswordService resetPasswordService;

    @Autowired
    private MailSender mailSender;

    @PostMapping("/signin")
    public ResponseEntity<Object> authenticateUser(@Valid @RequestBody LoginRequest loginRequest,
                                                   BindingResult bindingResult) {
        ResponseEntity<Object> errors = requestErrorValidation.getErrors(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = SecurityConstants.TOKEN_PREFIX + jwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTTokenResponse(true, jwt));
    }


    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody SignUpRequest signupRequest,
                                               BindingResult bindingResult) {
        ResponseEntity<Object> errors = requestErrorValidation.getErrors(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        String message = userService.createUser(signupRequest);

        return ResponseEntity.ok(new MessageResponse(message));
    }

    @GetMapping("/verifyRegistration")
    public ResponseEntity<MessageResponse> verifyRegistration(@RequestParam("token") String token) {
        return ResponseEntity.ok(new MessageResponse(userService.validateVerificationToken(token)));
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<Object> forgotPassword(@Valid @RequestBody EmailRequest emailRequest,
                                                 BindingResult bindingResult) {
        ResponseEntity<Object> errors = requestErrorValidation.getErrors(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        String message = resetPasswordService.sendResetPasswordCode(emailRequest);

        return ResponseEntity.ok(new MessageResponse(message));
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<Object> resetPassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest,
                                                BindingResult bindingResult) {
        ResponseEntity<Object> errors = requestErrorValidation.getErrors(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        String message = resetPasswordService.resetPassword(changePasswordRequest);

        return ResponseEntity.ok(new MessageResponse(message));
    }
}
