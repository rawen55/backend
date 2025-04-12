package Telemedcine.cwa.telemedcine.contoller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Telemedcine.cwa.telemedcine.config.JwtTokenUtil;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        // Authentifier l'utilisateur
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        
        // Charger les détails de l'utilisateur
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        
        // Générer et retourner le token JWT avec les rôles
        return jwtTokenUtil.generateToken(userDetails.getUsername());
    }
}
