package com.iset.gestion_projet.service;



import com.iset.gestion_projet.entity.Statut;
import com.iset.gestion_projet.entity.Ticket;
import com.iset.gestion_projet.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    public Ticket createTicket(Ticket ticket) {
        ticket.setDateCreation(LocalDate.now());
        return ticketRepository.save(ticket);
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Ticket getById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
    }

    public Ticket updateTicket(Long id, Ticket updated) {
        Ticket t = getById(id);

        t.setTitre(updated.getTitre());
        t.setDescription(updated.getDescription());
        t.setStatut(updated.getStatut());
        t.setPriorite(updated.getPriorite());
        t.setDateSouhaite(updated.getDateSouhaite());
        t.setDateMiseAJour(LocalDate.now());

        return ticketRepository.save(t);
    }

    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }
    public List<Ticket> getByStatut(Statut statut) {
        return ticketRepository.findByStatut(statut);
    }
}
