package xyz.stodo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import xyz.stodo.entity.User;
import xyz.stodo.exception.UserExistException;
import xyz.stodo.payload.SignUpRequest;
import xyz.stodo.repository.UserRepository;

import java.security.Principal;

@Service
public class UserService {
    // TODO: 4/7/23 Добавить логгирование
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    private CustomUserDetailsService userDetailsService;
    
    public User getUserByPrincipal(Principal principal) {
        return (User) userDetailsService.loadUserByUsername(principal.getName());
    }

    public void createUser(SignUpRequest signupRequest) {
        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

        try {
            userRepository.save(user);
        }
        catch (Exception e) {
            throw new UserExistException("The user " + user.getEmail() + " already exist." +
                    " Please check credentials");
        }
    }
}
