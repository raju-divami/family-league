package com.familyleague.security;

import com.familyleague.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class UserPrincipal implements UserDetails {

    private final Long id;
    private final String email;
    private final String password;
    private final String status;
    private final Collection<? extends GrantedAuthority> authorities;

    private UserPrincipal(Long id, String email, String password, String status,
                          Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.status = status;
        this.authorities = authorities;
    }

    public static UserPrincipal from(User user, Set<String> roleCodes) {
        Set<GrantedAuthority> authorities = roleCodes.stream()
                .map(code -> new SimpleGrantedAuthority("ROLE_" + code))
                .collect(Collectors.toSet());
        return new UserPrincipal(user.getId(), user.getEmail(), user.getPasswordHash(),
                user.getStatus(), authorities);
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return !"SUSPENDED".equals(status); }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return "ACTIVE".equals(status); }
}
