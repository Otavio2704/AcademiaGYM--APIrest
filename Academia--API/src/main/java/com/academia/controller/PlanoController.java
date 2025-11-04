package com.academia.controller;

import com.academia.dto.PlanoDto;
import com.academia.model.Plano;
import com.academia.service.PlanoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/planos")
public class PlanoController {

    @Autowired
    private PlanoService planoService;

    @GetMapping
    public ResponseEntity<List<PlanoDto>> listarPlanos() {
        List<PlanoDto> planos = planoService.listarPlanos();
        return ResponseEntity.ok(planos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanoDto> buscarPorId(@PathVariable Long id) {
        PlanoDto plano = planoService.buscarPorId(id);
        return ResponseEntity.ok(plano);
    }

    @PostMapping
    public ResponseEntity<PlanoDto> criar(@RequestBody @Valid Plano plano) {
        PlanoDto novoPlano = planoService.criarPlano(plano);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPlano);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanoDto> atualizar(@PathVariable Long id, 
                                             @RequestBody @Valid Plano plano) {
        PlanoDto planoAtualizado = planoService.atualizarPlano(id, plano);
        return ResponseEntity.ok(planoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        planoService.deletarPlano(id);
        return ResponseEntity.noContent().build();
    }
}