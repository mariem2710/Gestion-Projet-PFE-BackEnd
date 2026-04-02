package com.iset.gestion_projet.controller;

import com.iset.gestion_projet.entity.Statut;
import com.iset.gestion_projet.entity.Ticket;
import com.iset.gestion_projet.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
@CrossOrigin("*")
public class TicketController {

    private final TicketService ticketService;

    @PostMapping
    public Ticket create(@RequestBody Ticket ticket) {
        return ticketService.createTicket(ticket);
    }

    @GetMapping
    public List<Ticket> getAll() {
        return ticketService.getAllTickets();
    }

    @GetMapping("/{id}")
    public Ticket getById(@PathVariable Long id) {
        return ticketService.getById(id);
    }

    @PutMapping("/{id}")
    public Ticket update(@PathVariable Long id, @RequestBody Ticket ticket) {
        return ticketService.updateTicket(id, ticket);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        ticketService.deleteTicket(id);
    }
    @GetMapping("/todo")
    public List<Ticket> getTodoTickets() {
        return ticketService.getByStatut(Statut.TODO);
    }

}