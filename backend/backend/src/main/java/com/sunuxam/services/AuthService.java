package com.sunuxam.services;
import com.sunuxam.config.JwtService;
import com.sunuxam.dtos.AuthResponse;
import com.sunuxam.dtos.LoginRequest;
import com.sunuxam.dtos.RegisterRequest;
import com.sunuxam.entities.User;
import com.sunuxam.enums.Role;
import com.sunuxam.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

    @Service
    @RequiredArgsConstructor
    public class AuthService {

        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;

        public AuthResponse register(RegisterRequest request) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new RuntimeException("Cet email est déjà utilisé.");
            }

            var user = User.builder()
                    .nom(request.getNom())
                    .prenom(request.getPrenom())
                    .email(request.getEmail())
                    .telephone(request.getTelephone())
                    .motDePasse(passwordEncoder.encode(request.getMotDePasse()))
                    .role(Role.ROLE_CANDIDAT)
                    .build();

            userRepository.save(user);

            var jwtToken = jwtService.generateToken(user);
            return AuthResponse.builder()
                    .token(jwtToken)
                    .email(user.getEmail())
                    .role(user.getRole().name())
                    .id(user.getId())
                    .build();
        }

        public AuthResponse login(LoginRequest request) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getMotDePasse())
            );

            var user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé."));

            var jwtToken = jwtService.generateToken(user);
            return AuthResponse.builder()
                    .token(jwtToken)
                    .email(user.getEmail())
                    .role(user.getRole().name())
                    .id(user.getId())
                    .build();
        }

}
