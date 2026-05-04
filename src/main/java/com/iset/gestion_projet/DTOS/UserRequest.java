package com.iset.gestion_projet.DTOS;



import lombok.*;

import javax.management.relation.Role;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {

    private String nom;
    private String prenom;
    private String email;
    private String password; // utilisé seulement par l'admin
    private Role role;
}