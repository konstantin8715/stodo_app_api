package xyz.stodo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import xyz.stodo.entity.ResetPasswordCode;
import xyz.stodo.entity.User;
import xyz.stodo.exception.IncorrectEmailException;
import xyz.stodo.exception.IncorrectResetPasswordCodeException;
import xyz.stodo.payload.ResetPasswordRequest;
import xyz.stodo.payload.EmailRequest;
import xyz.stodo.repository.ResetPasswordCodeRepository;
import xyz.stodo.repository.UserRepository;

import java.util.Calendar;
import java.util.Optional;

@Service
public class ResetPasswordService {
    @Autowired
    private ResetPasswordCodeRepository resetPasswordCodeRepository;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public String sendResetPasswordCode(EmailRequest emailRequest) {
        Optional<User> userOptional = userRepository.findByEmail(emailRequest.getEmail());

        if (userOptional.isEmpty()) {
            throw new IncorrectEmailException("There is no user with this email address. Please register.");
        }

        User user = userOptional.get();

        int randomNumber = 1000 + (int) (Math.random() * 10000);
        String code = Integer.toString(randomNumber);
        saveResetPasswordCodeForUser(code, user);
        mailSender.send(user.getEmail(), "Your reset password code", code);

        return "Reset password code send to your email";
    }

    public void saveResetPasswordCodeForUser(String code, User user) {
        ResetPasswordCode resetPasswordCode = new ResetPasswordCode(user, code);

        resetPasswordCodeRepository.save(resetPasswordCode);
    }

    public String resetPassword(ResetPasswordRequest resetPasswordRequest) {
        Optional<User> userOptional = userRepository.findByEmail(resetPasswordRequest.getEmail());

        if (userOptional.isEmpty()) {
            throw new IncorrectEmailException("There is no user with this email address. Please register.");
        }

        if (!validateResetPasswordCode(resetPasswordRequest.getCode(),
                resetPasswordRequest.getEmail())) {
            throw new IncorrectResetPasswordCodeException("Incorrect reset password code or reset password code is expired.");
        }

        User user = userOptional.get();

        user.setPassword(passwordEncoder.encode(resetPasswordRequest.getPassword()));
        userRepository.save(user);

        return "Password change successfully";
    }

    public boolean validateResetPasswordCode(String code, String email) {
        Optional<ResetPasswordCode> resetPasswordCodeOptional
                = resetPasswordCodeRepository.findByCode(code);

        if (resetPasswordCodeOptional.isEmpty()) return false;

        ResetPasswordCode resetPasswordCode = resetPasswordCodeOptional.get();

        if (!resetPasswordCode.getUser().getEmail().equals(email)) return false;

        Calendar cal = Calendar.getInstance();

        if ((resetPasswordCode.getExpirationTime().getTime()
                - cal.getTime().getTime()) <= 0) {
            resetPasswordCodeRepository.delete(resetPasswordCode);
            return false;
        }

        return true;
    }
}
