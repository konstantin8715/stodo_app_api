package xyz.stodo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.stodo.payload.JWTTokenResponse;
import xyz.stodo.payload.LoginRequest;
import xyz.stodo.payload.MessageResponse;
import xyz.stodo.payload.SignUpRequest;
import xyz.stodo.security.JWTTokenProvider;
import xyz.stodo.security.SecurityConstants;
import xyz.stodo.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    private JWTTokenProvider jwtTokenProvider;
    @Autowired
    private AuthenticationManager authenticationManager;
//    @Autowired
//    private ResponseErrorValidation responseErrorValidation;
    @Autowired
    private UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<Object> authenticateUser(@Valid @RequestBody LoginRequest loginRequest,
                                                   BindingResult bindingResult) {
//        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
//        if (!ObjectUtils.isEmpty(errors)) return errors;

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
//        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
//        if (!ObjectUtils.isEmpty(errors)) return errors;

        userService.createUser(signupRequest);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
