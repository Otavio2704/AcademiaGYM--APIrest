package com.academia.controller;

import com.academia.dto.AlunoDto;
import com.academia.dto.CreateAlunoDto;
import com.academia.service.AlunoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/alunos")
@Validated
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @GetMapping
    public ResponseEntity<Page<AlunoDto>> listarAlunos(
        @PageableDefault(size = 20, sort = "nome") Pageable pageable) {
        Page<AlunoDto> alunos = alunoService.listarAlunos(pageable);
        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoDto> buscarPorId(@PathVariable Long id) {
        AlunoDto aluno = alunoService.buscarPorId(id);
        return ResponseEntity.ok(aluno);
    }

    @PostMapping
    public ResponseEntity<AlunoDto> criar(@RequestBody @Valid CreateAlunoDto dto) {
        AlunoDto novoAluno = alunoService.criarAluno(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoAluno);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlunoDto> atualizar(@PathVariable Long id, 
                                             @RequestBody @Valid CreateAlunoDto dto) {
        AlunoDto alunoAtualizado = alunoService.atualizarAluno(id, dto);
        return ResponseEntity.ok(alunoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        alunoService.deletarAluno(id);
        return ResponseEntity.noContent().build();
    }
}
