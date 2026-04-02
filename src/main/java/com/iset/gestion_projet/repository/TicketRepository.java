package com.iset.gestion_projet.repository;
import com.iset.gestion_projet.entity.Statut;
import com.iset.gestion_projet.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByStatut(Statut statut);
}
