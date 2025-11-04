package com.academia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.academia.model.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {}
