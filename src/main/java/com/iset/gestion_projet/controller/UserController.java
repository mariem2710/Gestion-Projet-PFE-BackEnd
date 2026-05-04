package com.iset.gestion_projet.controller;

import com.iset.gestion_projet.DTOS.LoginResponse;
import com.iset.gestion_projet.Request.UserRequest;
import com.iset.gestion_projet.entity.User;
import com.iset.gestion_projet.service.JwtService;
import com.iset.gestion_projet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtService  jwtService;

    // ── USER MÉTIER : soumettre une demande ──────────
    @PostMapping("/demande")
    public ResponseEntity<User> demanderCompte(
            @RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.demanderCompte(request));
    }

    // ── ADMIN : demandes en attente ──────────────────
    @GetMapping("/demandes/en-attente")
    public ResponseEntity<List<User>> getDemandesEnAttente() {
        return ResponseEntity.ok(userService.getDemandesEnAttente());
    }

    // ── ADMIN : accepter + mot de passe ─────────────
    @PutMapping("/{id}/accepter")
    public ResponseEntity<User> accepterCompte(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        String password = body.get("password");
        if (password == null || password.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(userService.accepterCompte(id, password));
    }

    // ── ADMIN : refuser ──────────────────────────────
    @PutMapping("/{id}/refuser")
    public ResponseEntity<User> refuserCompte(@PathVariable Long id) {
        return ResponseEntity.ok(userService.refuserCompte(id));
    }

    // ── CRUD ─────────────────────────────────────────
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id,
            @RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.updateUser(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // ── LOGIN → JWT ───────────────────────────────────
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody UserRequest request) {

        User user = userService.login(
                request.getEmail(),
                request.getPassword()
        );

        String token = jwtService.generateToken(user.getEmail());

        return ResponseEntity.ok(new LoginResponse(
                token,
                user.getEmail(),
                user.getRole().name()
        ));
    }
}