package com.iset.gestion_projet.service;

import com.iset.gestion_projet.entity.Statut;
import com.iset.gestion_projet.entity.Ticket;
import com.iset.gestion_projet.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.iset.gestion_projet.DTOS.TicketRequest;
import com.iset.gestion_projet.DTOS.TicketResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    // ✅ CREATE
    public TicketResponse createTicket(TicketRequest request) {
        Ticket ticket = Ticket.builder()
                .titre(request.getTitre())
                .description(request.getDescription())
                .statut(request.getStatut())
                .priorite(request.getPriorite())
                .dateSouhaite(request.getDateSouhaite())
                .dateCreation(LocalDate.now())
                .build();
        return mapToResponse(ticketRepository.save(ticket));
    }

    // ✅ GET ALL
    public List<TicketResponse> getAllTickets() {
        return ticketRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ✅ GET BY ID
    public TicketResponse getById(Long id) {
        return mapToResponse(findTicket(id));
    }

    // ✅ UPDATE — seulement si statut = TODO
    public TicketResponse updateTicket(Long id, TicketRequest request) {

        Ticket t = findTicket(id);

        // 🔒 Vérification : seulement les tickets TODO peuvent être modifiés
        if (t.getStatut() != Statut.A_faire) {
            throw new RuntimeException("Ce ticket ne peut pas être modifié car son statut est : " + t.getStatut());
        }

        t.setTitre(request.getTitre());
        t.setDescription(request.getDescription());
        t.setStatut(request.getStatut());
        t.setPriorite(request.getPriorite());
        t.setDateSouhaite(request.getDateSouhaite());
        t.setDateMiseAJour(LocalDate.now());

        return mapToResponse(ticketRepository.save(t));
    }

    // ✅ DELETE
    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }

    // ✅ FILTER BY STATUS
    public List<TicketResponse> getByStatut(Statut statut) {
        return ticketRepository.findByStatut(statut)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // 🔁 MAPPING
    private TicketResponse mapToResponse(Ticket ticket) {
        return TicketResponse.builder()
                .id(ticket.getId())
                .titre(ticket.getTitre())
                .description(ticket.getDescription())
                .statut(ticket.getStatut())
                .priorite(ticket.getPriorite())
                .dateCreation(ticket.getDateCreation())
                .dateSouhaite(ticket.getDateSouhaite())
                .dateMiseAJour(ticket.getDateMiseAJour())
                .build();
    }

    private Ticket findTicket(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
    }
    public TicketResponse approveTicket(Long id) {
        Ticket t = findTicket(id);

        if (t.getStatut() != Statut.A_faire) {
            throw new RuntimeException("Ticket déjà traité");
        }

        t.setStatut(Statut.Approuvé);
        t.setDateMiseAJour(LocalDate.now());

        return mapToResponse(ticketRepository.save(t));
    }

    public TicketResponse rejectTicket(Long id) {
        Ticket t = findTicket(id);

        if (t.getStatut() != Statut.A_faire) {
            throw new RuntimeException("Ticket déjà traité");
        }

        t.setStatut(Statut.Rejeté);
        t.setDateMiseAJour(LocalDate.now());

        return mapToResponse(ticketRepository.save(t));
    }
}