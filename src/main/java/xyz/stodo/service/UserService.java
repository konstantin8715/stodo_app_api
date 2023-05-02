package xyz.stodo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import xyz.stodo.entity.User;
import xyz.stodo.entity.VerificationToken;
import xyz.stodo.exception.IncorrectEmailException;
import xyz.stodo.exception.UserExistException;
import xyz.stodo.payload.GetPasswordRequest;
import xyz.stodo.payload.SignUpRequest;
import xyz.stodo.repository.UserRepository;
import xyz.stodo.repository.VerificationTokenRepository;

import java.security.Principal;
import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    // TODO: 4/7/23 Добавить логгирование
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

//        try {
            userRepository.save(user);
            String token = UUID.randomUUID().toString();
            saveVerificationTokenForUser(token, user);
            String url = "http://localhost:8080/api/auth/verifyRegistration?token=" + token;
            mailSender.send(signupRequest.getEmail(), "Verify registration", url);
            return "User registered successfully! Please check your email!";
//        }
//        catch (Exception e) {
////            throw new UserExistException("The user " + user.getEmail() + " already exist." +
////                    " Please check your email for the verification token");
//        }
    }

    public String getPassword(GetPasswordRequest getPasswordRequest) {
        Optional<User> userOptional = userRepository.findByEmail(getPasswordRequest.getEmail());

        if (userOptional.isEmpty())
            throw new IncorrectEmailException("There is no user with this email address. Please register.");

        User user = userOptional.get();
        mailSender.send(user.getEmail(), "Your password", passwordEncoder.encode(user.getPassword()));

        return "Password has been sent by email " + user.getEmail();
    }

    public void saveVerificationTokenForUser(String token, User user) {
        VerificationToken verificationToken = new VerificationToken(user, token);

        verificationTokenRepository.save(verificationToken);
    }

    public boolean validateVerificationToken(String token) {
        Optional<VerificationToken> verificationTokenOptional
                = verificationTokenRepository.findByToken(token);

        if (verificationTokenOptional.isEmpty()) return false;

        VerificationToken verificationToken = verificationTokenOptional.get();

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();

        if ((verificationToken.getExpirationTime().getTime()
                - cal.getTime().getTime()) <= 0) {
            verificationTokenRepository.delete(verificationToken);
            return false;
        }

        user.setEnabled(true);
        userRepository.save(user);

        return true;
    }
}
