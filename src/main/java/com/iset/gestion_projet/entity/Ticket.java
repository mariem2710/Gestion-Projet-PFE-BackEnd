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

    private LocalDate dateCreation;

    private LocalDate dateSouhaite;

    private LocalDate dateMiseAJour;

    // 1 Ticket → plusieurs commentaires
    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Commentaire> commentaires;

    // 1 Ticket → plusieurs sous-tickets
    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SousTicket> sousTickets;
}