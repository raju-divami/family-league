package com.familyleague.security;

import com.familyleague.entity.User;
import com.familyleague.exception.ResourceNotFoundException;
import com.familyleague.repository.UserRepository;
import com.familyleague.repository.UserRoleRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    public CustomUserDetailsService(UserRepository userRepository,
                                    UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmailAndDeletedFalse(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
        return buildPrincipal(user);
    }

    public UserDetails loadUserById(Long id) {
        User user = userRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));
        return buildPrincipal(user);
    }

    private UserPrincipal buildPrincipal(User user) {
        Set<String> roleCodes = userRoleRepository.findRoleCodesByUserId(user.getId());
        return UserPrincipal.from(user, roleCodes);
    }
}
