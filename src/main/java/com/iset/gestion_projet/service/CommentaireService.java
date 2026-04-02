package com.iset.gestion_projet.service;



import com.iset.gestion_projet.entity.Commentaire;
import com.iset.gestion_projet.entity.Ticket;
import com.iset.gestion_projet.repository.CommentaireRepository;
import com.iset.gestion_projet.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentaireService {

    private final CommentaireRepository commentaireRepository;
    private final TicketRepository ticketRepository;

    public Commentaire addComment(Long ticketId, Commentaire c) {

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        c.setTicket(ticket);

        return commentaireRepository.save(c);
    }

    public List<Commentaire> getAll() {
        return commentaireRepository.findAll();
    }
}