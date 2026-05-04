package com.iset.gestion_projet.controller;

import com.iset.gestion_projet.DTOS.TicketRequest;
import com.iset.gestion_projet.DTOS.TicketResponse;
import com.iset.gestion_projet.entity.Statut;
import com.iset.gestion_projet.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
// ✅ @CrossOrigin supprimé — géré globalement par SecurityConfig
public class TicketController {

    private final TicketService ticketService;

    @PostMapping
    public TicketResponse create(@RequestBody TicketRequest request) {
        return ticketService.createTicket(request);
    }

    @GetMapping
    public List<TicketResponse> getAll() {
        return ticketService.getAllTickets();
    }

    @GetMapping("/{id}")
    public TicketResponse getById(@PathVariable Long id) {
        return ticketService.getById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestBody TicketRequest request) {
        try {
            return ResponseEntity.ok(ticketService.updateTicket(id, request));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        ticketService.deleteTicket(id);
    }

    @GetMapping("/todo")
    public List<TicketResponse> getTodoTickets() {
        return ticketService.getByStatut(Statut.A_faire);
    }

    @PutMapping("/{id}/approve")
    public TicketResponse approve(@PathVariable Long id) {
        return ticketService.approveTicket(id);
    }

    @PutMapping("/{id}/reject")
    public TicketResponse reject(@PathVariable Long id) {
        return ticketService.rejectTicket(id);
    }
}