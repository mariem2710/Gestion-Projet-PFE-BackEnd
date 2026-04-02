package com.iset.gestion_projet.repository;



import com.iset.gestion_projet.entity.Commentaire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentaireRepository extends JpaRepository<Commentaire, Long> {
}