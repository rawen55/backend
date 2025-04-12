package Telemedcine.cwa.telemedcine.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import Telemedcine.cwa.telemedcine.service.CustomUserDetailsService;

@Configuration
public class WebSecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    // Injection de dépendances via le constructeur
    public WebSecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return customUserDetailsService; // Utilisation du service injecté
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Sécurisation des mots de passe
    }
}
