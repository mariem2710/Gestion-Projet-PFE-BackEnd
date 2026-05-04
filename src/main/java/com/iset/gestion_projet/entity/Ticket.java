package com.iset.gestion_projet.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;

    private String description;

    @Enumerated(EnumType.STRING)
    private Statut statut;

    @Enumerated(EnumType.STRING)
    private Priorite priorite;

    private LocalDate dateCreation;   // ← auto à la création
    private LocalDate dateSouhaite;
    private LocalDate dateMiseAJour;  // ← auto à chaque update

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Commentaire> commentaires;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SousTicket> sousTickets;

    // ─────────────────────────────────────────────
    // Appelé automatiquement par JPA avant INSERT
    // ─────────────────────────────────────────────
    @PrePersist
    public void prePersist() {
        this.dateCreation  = LocalDate.now();
        this.dateMiseAJour = LocalDate.now();
        if (this.statut == null) {
            this.statut = Statut.A_faire;
        }
    }

    // ─────────────────────────────────────────────
    // Appelé automatiquement par JPA avant UPDATE
    // ─────────────────────────────────────────────
    @PreUpdate
    public void preUpdate() {
        this.dateMiseAJour = LocalDate.now();
    }
}