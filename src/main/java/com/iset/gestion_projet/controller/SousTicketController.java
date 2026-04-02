package com.iset.gestion_projet.controller;



import com.iset.gestion_projet.entity.SousTicket;
import com.iset.gestion_projet.service.SousTicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sous-tickets")
@RequiredArgsConstructor
@CrossOrigin("*")
public class SousTicketController {

    private final SousTicketService sousTicketService;

    @PostMapping
    public SousTicket create(@RequestBody SousTicket s) {
        return sousTicketService.createSousTicket(s);
    }

    @GetMapping
    public List<SousTicket> getAll() {
        return sousTicketService.getAll();
    }
}