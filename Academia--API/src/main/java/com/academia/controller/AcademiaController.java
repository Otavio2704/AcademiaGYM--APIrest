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

import com.academia.model.Academia;
import com.academia.repository.AcademiaRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/academias")
public class AcademiaController {
    @Autowired
    private AcademiaRepository academiaRepository;

    @GetMapping
    public ResponseEntity<List<Academia>> listarAcademias() {
        List<Academia> academias = academiaRepository.findAll();
        return ResponseEntity.ok(academias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Academia> buscarPorId(@PathVariable Long id) {
        return academiaRepository.findById(id)
            .map(academia -> ResponseEntity.ok(academia))
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Academia> criar(@RequestBody @Valid Academia academia) {
        Academia novaAcademia = academiaRepository.save(academia);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaAcademia);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Academia> atualizar(@PathVariable Long id, @RequestBody @Valid Academia academia) {
        return academiaRepository.findById(id)
            .map(academiaExistente -> {
                academiaExistente.setNome(academia.getNome());
                academiaExistente.setEndereco(academia.getEndereco());
                return ResponseEntity.ok(academiaRepository.save(academiaExistente));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (academiaRepository.existsById(id)) {
            academiaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
