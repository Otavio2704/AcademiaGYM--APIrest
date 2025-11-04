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

import com.academia.model.Instrutor;
import com.academia.repository.InstrutorRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/instrutores")
public class InstrutorController {
    @Autowired
    private InstrutorRepository instrutorRepository;

    @GetMapping
    public ResponseEntity<List<Instrutor>> listarInstrutores() {
        List<Instrutor> instrutores = instrutorRepository.findAll();
        return ResponseEntity.ok(instrutores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Instrutor> buscarPorId(@PathVariable Long id) {
        return instrutorRepository.findById(id)
            .map(instrutor -> ResponseEntity.ok(instrutor))
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Instrutor> criar(@RequestBody @Valid Instrutor instrutor) {
        Instrutor novoInstrutor = instrutorRepository.save(instrutor);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoInstrutor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Instrutor> atualizar(@PathVariable Long id, @RequestBody @Valid Instrutor instrutor) {
        return instrutorRepository.findById(id)
            .map(instrutorExistente -> {
                instrutorExistente.setNome(instrutor.getNome());
                instrutorExistente.setEspecialidade(instrutor.getEspecialidade());
                return ResponseEntity.ok(instrutorRepository.save(instrutorExistente));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (instrutorRepository.existsById(id)) {
            instrutorRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
