package xyz.stodo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import xyz.stodo.repository.UserRepository;

import java.security.Principal;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    public User getUserByPrincipal(Principal principal) {
        return (User) userDetailsService.loadUserByUsername(principal.getName());
    }
}
