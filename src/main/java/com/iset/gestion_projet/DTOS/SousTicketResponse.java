package com.iset.gestion_projet.DTOS;



import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SousTicketResponse {

    private Long id;
    private String titre;
    private String description;
    private Long ticketId; // référence au ticket parent
}