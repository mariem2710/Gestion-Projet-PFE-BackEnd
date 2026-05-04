package com.iset.gestion_projet.service;

import com.iset.gestion_projet.Request.UserRequest;
import com.iset.gestion_projet.entity.Role;
import com.iset.gestion_projet.entity.StatutCompte;
import com.iset.gestion_projet.entity.User;
import com.iset.gestion_projet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;

    // ─────────────────────────────────────────────
    // USER MÉTIER : demande de création de compte
    // Statut = EN_ATTENTE, pas de mot de passe
    // ─────────────────────────────────────────────
    public User demanderCompte(UserRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Cet email est déjà utilisé.");
        }

        User user = User.builder()
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .email(request.getEmail())
                .role(request.getRole() != null ? request.getRole() : Role.METIER)
                .statut(StatutCompte.EN_ATTENTE)
                .build();

        return userRepository.save(user);
    }

    // ─────────────────────────────────────────────
    // ADMIN : accepter un compte + saisir le mdp
    // → envoie email automatiquement
    // ─────────────────────────────────────────────
    public User accepterCompte(Long id, String password) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé."));

        if (user.getStatut() != StatutCompte.EN_ATTENTE) {
            throw new RuntimeException("Ce compte a déjà été traité.");
        }

        user.setPassword(password); // encoder en prod avec BCrypt
        user.setStatut(StatutCompte.ACCEPTE);
        userRepository.save(user);

        // Envoi email de bienvenue
        emailService.sendAccountAcceptedEmail(
                user.getEmail(),
                user.getNom(),
                user.getPrenom(),
                password
        );

        return user;
    }

    // ─────────────────────────────────────────────
    // ADMIN : refuser un compte
    // ─────────────────────────────────────────────
    public User refuserCompte(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé."));

        if (user.getStatut() != StatutCompte.EN_ATTENTE) {
            throw new RuntimeException("Ce compte a déjà été traité.");
        }

        user.setStatut(StatutCompte.REFUSE);
        userRepository.save(user);

        emailService.sendAccountRefusedEmail(
                user.getEmail(),
                user.getPrenom(),
                user.getNom()
        );

        return user;
    }

    // ─────────────────────────────────────────────
    // ADMIN : lister les demandes EN_ATTENTE
    // ─────────────────────────────────────────────
    public List<User> getDemandesEnAttente() {
        return userRepository.findByStatut(StatutCompte.EN_ATTENTE);
    }

    // ─────────────────────────────────────────────
    // CRUD standard
    // ─────────────────────────────────────────────
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé."));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(Long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé."));

        if (!user.getEmail().equals(request.getEmail())) {
            if (userRepository.findByEmail(request.getEmail()).isPresent()) {
                throw new RuntimeException("Cet email est déjà utilisé.");
            }
        }

        user.setNom(request.getNom());
        user.setPrenom(request.getPrenom());
        user.setEmail(request.getEmail());

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(request.getPassword());
        }
        user.setRole(request.getRole());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Utilisateur non trouvé.");
        }
        userRepository.deleteById(id);
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email introuvable."));

        if (user.getStatut() != StatutCompte.ACCEPTE) {
            throw new RuntimeException("Compte non encore activé ou refusé.");
        }
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Mot de passe incorrect.");
        }
        return user;
    }
}