package Telemedcine.cwa.telemedcine.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @SuppressWarnings("removal")
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
          // Pas de session ni de CSRF pour API REST
          .csrf(csrf -> csrf.disable())
          .cors().and() 
          .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
          
            .authorizeHttpRequests(auth -> auth
          //  .requestMatchers(HttpMethod.POST, "/api/rendezvous/create").hasRole("PATIENT")//
            .requestMatchers(HttpMethod.GET, "/api/user/profile").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/user/me").permitAll()
           .requestMatchers("/api/auth/**").permitAll()
           .requestMatchers("/api/rendezvous/medecin/*/stats").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/user/{id}/photo/upload").authenticated()
            .requestMatchers("/uploads/**").permitAll()
            .requestMatchers("/api/chatbot/**").permitAll() 
            .requestMatchers("/api/user/me").authenticated()
            .requestMatchers("/api/rendezvous/medecin/me").permitAll()
            .requestMatchers("/api/rendezvous/medecin/**").permitAll()

            .requestMatchers(HttpMethod.GET, "/api/user/*/photo").permitAll()
            .requestMatchers("/api/rendezvous/**").permitAll()
            .requestMatchers(HttpMethod.DELETE, "/api/rendezvous/{id}").authenticated()
            .requestMatchers("/api/rendezvous/create").permitAll()
            .requestMatchers("/api/auth/**").permitAll()
            .requestMatchers("/api/rendezvous/stats/weekly").permitAll() // Restreindre l'accès à ce rôle
            .requestMatchers("/api/medecin").permitAll()
            .requestMatchers("/api/medecin/**").permitAll()
            .requestMatchers("/api/admin/**").permitAll()
            .requestMatchers("/api/patient/{id]/preconsultation}").permitAll()

            )
            
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            http.addFilterBefore(jwtAuthenticationFilter,
            org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    @Autowired
private UserDetailsService userDetailsService;

@Autowired
private PasswordEncoder passwordEncoder;
 @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(""); // Supprime le préfixe ROLE_
    }
@Bean

public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder);
    return authProvider;
}
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
    configuration.setAllowCredentials(true); 
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
