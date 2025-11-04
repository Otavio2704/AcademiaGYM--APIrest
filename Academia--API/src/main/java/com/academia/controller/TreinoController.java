package com.academia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.academia.model.Treino;
import com.academia.repository.TreinoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/treinos")
public class TreinoController {
    @Autowired
    private TreinoRepository treinoRepository;

    @GetMapping
    public ResponseEntity<List<Treino>> listarTreinos() {
        List<Treino> treinos = treinoRepository.findAll();
        return ResponseEntity.ok(treinos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Treino> buscarPorId(@PathVariable Long id) {
        return treinoRepository.findById(id)
            .map(treino -> ResponseEntity.ok(treino))
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Treino> criar(@RequestBody @Valid Treino treino) {
        Treino novoTreino = treinoRepository.save(treino);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoTreino);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Treino> atualizar(@PathVariable Long id, @RequestBody @Valid Treino treino) {
        return treinoRepository.findById(id)
            .map(treinoExistente -> {
                treinoExistente.setDescricao(treino.getDescricao());
                treinoExistente.setAluno(treino.getAluno());
                treinoExistente.setInstrutor(treino.getInstrutor());
                return ResponseEntity.ok(treinoRepository.save(treinoExistente));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (treinoRepository.existsById(id)) {
            treinoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
