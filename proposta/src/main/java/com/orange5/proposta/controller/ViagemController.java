package com.orange5.proposta.controller;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

import com.orange5.proposta.dto.ViagemRequest;
import com.orange5.proposta.entity.Card;
import com.orange5.proposta.entity.Viagem;
import com.orange5.proposta.repository.CardRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cartoes/{id}/viagens")
public class ViagemController {

    @Autowired
    CardRepository cardRepository;

    @Autowired
    EntityManager manager;

    @PostMapping
    @Transactional
    public ResponseEntity<?> cadastrar(@PathVariable("id") String id, @RequestBody @Valid ViagemRequest viagemRequest,
            HttpServletRequest httpServletRequest) {
        try {
            Card card = cardRepository.findById(id).get();
            Viagem viagem = viagemRequest.toModel(card, httpServletRequest);

            manager.persist(viagem);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }

}