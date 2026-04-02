package com.iset.gestion_projet.service;

import com.iset.gestion_projet.entity.SousTicket;
import com.iset.gestion_projet.repository.SousTicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SousTicketService {

    private final SousTicketRepository sousTicketRepository;

    public SousTicket createSousTicket(SousTicket s) {
        return sousTicketRepository.save(s);
    }

    public List<SousTicket> getAll() {
        return sousTicketRepository.findAll();
    }
}
