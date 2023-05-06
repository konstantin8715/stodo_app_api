package xyz.stodo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import xyz.stodo.entity.User;
import xyz.stodo.entity.VerificationToken;
import xyz.stodo.exception.*;
import xyz.stodo.payload.SignUpRequest;
import xyz.stodo.repository.UserRepository;
import xyz.stodo.repository.VerificationTokenRepository;

import java.security.Principal;
import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

@Service
public class RegistrationService {
    // TODO: 4/7/23 Добавить логгирование
    // TODO: 06.05.2023 Зарефакторить
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private MailSender mailSender;
    
    public User getUserByPrincipal(Principal principal) {
        return (User) userDetailsService.loadUserByUsername(principal.getName());
    }

    public String createUser(SignUpRequest signupRequest) {
        Optional<User> userOptional = userRepository.findByEmail(signupRequest.getEmail());

        if (userOptional.isPresent()) {
            if (userOptional.get().isEnabled()) {
                return "The user " + signupRequest.getEmail() + " already exist and enabled, please login";
            }
            String token = UUID.randomUUID().toString();
            saveVerificationTokenForUser(token, userOptional.get());
            String url = "http://localhost:8080/api/auth/verifyRegistration?token=" + token;
            mailSender.send(signupRequest.getEmail(), "Verify registration", url);
            return "The user " + signupRequest.getEmail() + " already exist." +
                    " Please check your email for the verification token";
        }

        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setEnabled(false);

        userRepository.save(user);
        String token = UUID.randomUUID().toString();
        saveVerificationTokenForUser(token, user);
        String url = "http://localhost:8080/api/auth/verifyRegistration?token=" + token;
        mailSender.send(signupRequest.getEmail(), "Verify registration", url);
        return "User registered successfully! Please check your email!";
    }

    public void saveVerificationTokenForUser(String token, User user) {
        VerificationToken verificationToken = new VerificationToken(user, token);

        verificationTokenRepository.save(verificationToken);
    }

    public String validateVerificationToken(String token) {
        Optional<VerificationToken> verificationTokenOptional
                = verificationTokenRepository.findByToken(token);

        if (verificationTokenOptional.isEmpty()) {
            throw new InvalidTokenException("Invalid token");
        }

        VerificationToken verificationToken = verificationTokenOptional.get();

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();

        if ((verificationToken.getExpirationTime().getTime()
                - cal.getTime().getTime()) <= 0) {
            verificationTokenRepository.delete(verificationToken);
            throw new ExpiredTokenException("Token is expired");
        }

        user.setEnabled(true);
        userRepository.save(user);

        return "User verification successfully!";
    }
}
