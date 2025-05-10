package Telemedcine.cwa.telemedcine.config;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import Telemedcine.cwa.telemedcine.model.Role;
import Telemedcine.cwa.telemedcine.model.User;

public class UserDetailsImpl implements UserDetails {

    private final User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (user.getRole() == null) {
            throw new IllegalStateException("User role is not set.");
        }
        // Ajout de "ROLE_" devant le nom du rôle pour respecter la convention Spring Security
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
    }
    

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // Méthodes personnalisées
    public Long getId() {
        return user.getId();
    }

    public String getNom() {
        return user.getNom();
    }

    public String getEmail() {
        return user.getEmail();
    }

    public Role getRole() {
        return user.getRole();
    }

    public User getUser() {
        return user;
    }
}
