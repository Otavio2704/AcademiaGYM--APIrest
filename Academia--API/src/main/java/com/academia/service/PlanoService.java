package com.academia.service;

import com.academia.dto.PlanoDto;
import com.academia.exception.BadRequestException;
import com.academia.exception.ResourceNotFoundException;
import com.academia.model.Plano;
import com.academia.repository.PlanoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PlanoService {

    @Autowired
    private PlanoRepository planoRepository;

    public List<PlanoDto> listarPlanos() {
        return planoRepository.findAll()
            .stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }

    public PlanoDto buscarPorId(Long id) {
        Plano plano = planoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Plano não encontrado"));
        return convertToDto(plano);
    }

    public PlanoDto criarPlano(Plano plano) {
        validarValorMensal(plano.getValorMensal());
        Plano planoSalvo = planoRepository.save(plano);
        return convertToDto(planoSalvo);
    }

    public PlanoDto atualizarPlano(Long id, Plano plano) {
        Plano planoExistente = planoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Plano não encontrado"));
        
        validarValorMensal(plano.getValorMensal());
        
        planoExistente.setNome(plano.getNome());
        planoExistente.setValorMensal(plano.getValorMensal());
        
        Plano planoAtualizado = planoRepository.save(planoExistente);
        return convertToDto(planoAtualizado);
    }

    public void deletarPlano(Long id) {
        if (!planoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Plano não encontrado");
        }
        planoRepository.deleteById(id);
    }

    private void validarValorMensal(java.math.BigDecimal valor) {
        if (valor != null && valor.compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Valor mensal deve ser maior que zero");
        }
    }

    private PlanoDto convertToDto(Plano plano) {
        PlanoDto dto = new PlanoDto();
        dto.setId(plano.getId());
        dto.setNome(plano.getNome());
        dto.setValorMensal(plano.getValorMensal());
        return dto;
    }
}
