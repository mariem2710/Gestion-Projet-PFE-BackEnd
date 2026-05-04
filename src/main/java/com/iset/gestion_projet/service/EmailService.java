package com.iset.gestion_projet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.mail.enabled:true}")
    private boolean mailEnabled;

    public void sendAccountAcceptedEmail(String to, String nom,
                                         String prenom, String password) {
        if (!mailEnabled) return;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom("zouaghimariem91@gmail.com");
        message.setSubject("Bienvenue sur notre plateforme !");
        message.setText(
                "Bonjour " + prenom + " " + nom + ",\n\n" +
                        "Merci d'avoir rejoint notre plateforme.\n\n" +
                        "Vos identifiants de connexion :\n" +
                        "  Login        : " + to + "\n" +
                        "  Mot de passe : " + password + "\n\n" +
                        "Nous vous conseillons de changer votre mot de passe " +
                        "après votre première connexion.\n\n" +
                        "Cordialement,\nL'équipe de la plateforme"
        );
        mailSender.send(message);
    }

    public void sendAccountRefusedEmail(String to, String prenom, String nom) {
        if (!mailEnabled) return;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom("zouaghimariem91@gmail.com");
        message.setSubject("Votre demande de compte");
        message.setText(
                "Bonjour " + prenom + " " + nom + ",\n\n" +
                        "Nous avons bien examiné votre demande de création de compte.\n" +
                        "Malheureusement, celle-ci n'a pas pu être acceptée.\n\n" +
                        "Pour toute question, contactez l'administrateur.\n\n" +
                        "Cordialement,\nL'équipe de la plateforme"
        );
        mailSender.send(message);
    }
}