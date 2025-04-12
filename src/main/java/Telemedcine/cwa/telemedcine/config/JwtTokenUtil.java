package Telemedcine.cwa.telemedcine.config;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil {

    @SuppressWarnings("unused")
    private final UserDetailsService userDetailsService;
    @SuppressWarnings("unused")
    private final PasswordEncoder passwordEncoder;

    public JwtTokenUtil(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    public String generateToken(String username) {
        throw new UnsupportedOperationException("Unimplemented method 'generateToken'");
    }

    public boolean validateToken(String token) {
      
        throw new UnsupportedOperationException("Unimplemented method 'validateToken'");
    }

    public String extractUsername(String token) {
        throw new UnsupportedOperationException("Unimplemented method 'extractUsername'");
    }

   
}
