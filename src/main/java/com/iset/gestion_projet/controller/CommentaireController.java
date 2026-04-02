package com.iset.gestion_projet.controller;



import com.iset.gestion_projet.entity.Commentaire;
import com.iset.gestion_projet.service.CommentaireService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commentaires")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CommentaireController {

    private final CommentaireService commentaireService;

    @PostMapping
    public Commentaire add(@RequestBody Commentaire c) {
        return commentaireService.addComment(c);
    }

    @GetMapping
    public List<Commentaire> getAsll() {
        return commentaireService.getAll();
    }
    @PostMapping("/{ticketId}")
    public Commentaire addComment(
            @PathVariable Long ticketId,
            @RequestBody Commentaire c) {
        return commentaireService.addComment(ticketId, c);
    }

}
