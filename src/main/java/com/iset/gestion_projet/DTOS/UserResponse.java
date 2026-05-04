package com.iset.gestion_projet.DTOS;


import com.iset.gestion_projet.entity.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private Role role;

}
