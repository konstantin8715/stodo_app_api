package xyz.stodo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import xyz.stodo.entity.User;
import xyz.stodo.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User with " + email + " is not found"));
    }

    public User loadUserById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User with id=" + id + " is not found"));
    }
}
