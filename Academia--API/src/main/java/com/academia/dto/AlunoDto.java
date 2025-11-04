package com.academia.dto;

import java.time.LocalDate;

public class AlunoDto {
    
    private Long id;
    private String nome;
    private String email;
    private LocalDate dataNascimento;
    private PlanoDto plano;
    private int quantidadeTreinos;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }
    
    public PlanoDto getPlano() { return plano; }
    public void setPlano(PlanoDto plano) { this.plano = plano; }
    
    public int getQuantidadeTreinos() { return quantidadeTreinos; }
    public void setQuantidadeTreinos(int quantidadeTreinos) { this.quantidadeTreinos = quantidadeTreinos; }
}
