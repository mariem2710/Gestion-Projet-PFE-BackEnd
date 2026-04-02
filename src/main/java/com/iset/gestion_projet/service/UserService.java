package com.iset.gestion_projet.service;

import com.iset.gestion_projet.Request.UserRequest;
import com.iset.gestion_projet.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.iset.gestion_projet.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // ✅ CREATE
    public User createUser(UserRequest request) {

        // Vérifier email unique
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .email(request.getEmail())
                .password(request.getPassword()) // plus tard: encoder
                .role(request.getRole())
                .build();

        return userRepository.save(user);
    }

    // ✅ GET BY ID
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    // ✅ GET ALL
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // ✅ UPDATE
    public User updateUser(Long id, UserRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        // Vérifier email unique si modifié
        if (!user.getEmail().equals(request.getEmail())) {
            if (userRepository.findByEmail(request.getEmail()).isPresent()) {
                throw new RuntimeException("Email already exists");
            }
        }

        user.setNom(request.getNom());
        user.setPrenom(request.getPrenom());
        user.setEmail(request.getEmail());

        // Ne change password que si fourni
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(request.getPassword());
        }

        user.setRole(request.getRole());

        return userRepository.save(user);
    }

    // ✅ DELETE
    public void deleteUser(Long id) {

        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }

        userRepository.deleteById(id);
    }
    // ✅ LOGIN
    public User login(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email not found"));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        return user;
    }
}