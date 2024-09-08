package com.fiap.ecommerce.login.controller;
import com.fiap.ecommerce.login.dto.LoginRequestDTO;
import com.fiap.ecommerce.login.dto.RegisterRequestDTO;
import com.fiap.ecommerce.login.dto.ResponseDTO;
import com.fiap.ecommerce.login.model.Role;
import com.fiap.ecommerce.login.model.User;
import com.fiap.ecommerce.login.repository.UserRepository;
import com.fiap.ecommerce.login.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO body){
        User user = this.repository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not found"));
        if(passwordEncoder.matches(body.password(), user.getPassword())) {
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDTO(user.getUsername(), token));
        }
        return ResponseEntity.badRequest().build();
    }


    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body){
        Optional<User> user = this.repository.findByEmail(body.email());

        if(user.isEmpty()) {
            User newUser = new User();
            newUser.setPassword(passwordEncoder.encode(body.password()));
            newUser.setEmail(body.email());
            newUser.setUsername(body.name());
            newUser.setRole(Role.USER);
            this.repository.save(newUser);

            String token = this.tokenService.generateToken(newUser);
            return ResponseEntity.ok(new ResponseDTO(newUser.getEmail(), token));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String token) {
        String userEmail = tokenService.validateToken(token.replace("Bearer ", "").trim());
        if (userEmail != null) {
            return ResponseEntity.ok(userEmail); // Retorna o email como identificação do usuário
        }
        return ResponseEntity.status(401).body("Token inválido ou expirado");
    }
}
