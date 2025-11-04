package com.academia.service;

import com.academia.dto.PlanoDto;
import com.academia.exception.BadRequestException;
import com.academia.exception.ResourceNotFoundException;
import com.academia.model.Plano;
import com.academia.repository.PlanoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlanoServiceTest {

    @Mock
    private PlanoRepository planoRepository;

    @InjectMocks
    private PlanoService planoService;

    private Plano plano;

    @BeforeEach
    void setUp() {
        plano = new Plano();
        plano.setId(1L);
        plano.setNome("Plano Premium");
        plano.setValorMensal(new BigDecimal("149.90"));
    }

    @Test
    void listarPlanos_DeveRetornarListaDePlanos() {
        // Given
        Plano plano2 = new Plano();
        plano2.setId(2L);
        plano2.setNome("Plano Básico");
        plano2.setValorMensal(new BigDecimal("79.90"));

        when(planoRepository.findAll()).thenReturn(Arrays.asList(plano, plano2));

        // When
        List<PlanoDto> resultado = planoService.listarPlanos();

        // Then
        assertThat(resultado).hasSize(2);
        assertThat(resultado.get(0).getNome()).isEqualTo("Plano Premium");
        assertThat(resultado.get(1).getNome()).isEqualTo("Plano Básico");
    }

    @Test
    void criarPlano_ComDadosValidos_DeveRetornarPlanoDto() {
        // Given
        when(planoRepository.save(any(Plano.class))).thenReturn(plano);

        // When
        PlanoDto resultado = planoService.criarPlano(plano);

        // Then
        assertThat(resultado).isNotNull();
        assertThat(resultado.getNome()).isEqualTo("Plano Premium");
        assertThat(resultado.getValorMensal()).isEqualTo(new BigDecimal("149.90"));
        verify(planoRepository, times(1)).save(any(Plano.class));
    }

    @Test
    void criarPlano_ComValorMensalZero_DeveLancarBadRequestException() {
        // Given
        plano.setValorMensal(BigDecimal.ZERO);

        // When & Then
        assertThatThrownBy(() -> planoService.criarPlano(plano))
            .isInstanceOf(BadRequestException.class)
            .hasMessage("Valor mensal deve ser maior que zero");
    }

    @Test
    void criarPlano_ComValorMensalNegativo_DeveLancarBadRequestException() {
        // Given
        plano.setValorMensal(new BigDecimal("-50.00"));

        // When & Then
        assertThatThrownBy(() -> planoService.criarPlano(plano))
            .isInstanceOf(BadRequestException.class)
            .hasMessage("Valor mensal deve ser maior que zero");
    }

    @Test
    void buscarPorId_ComIdExistente_DeveRetornarPlanoDto() {
        // Given
        when(planoRepository.findById(1L)).thenReturn(Optional.of(plano));

        // When
        PlanoDto resultado = planoService.buscarPorId(1L);

        // Then
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getNome()).isEqualTo("Plano Premium");
    }

    @Test
    void buscarPorId_ComIdInexistente_DeveLancarResourceNotFoundException() {
        // Given
        when(planoRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> planoService.buscarPorId(1L))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessage("Plano não encontrado");
    }

    @Test
    void atualizarPlano_ComDadosValidos_DeveRetornarPlanoAtualizado() {
        // Given
        Plano planoAtualizado = new Plano();
        planoAtualizado.setNome("Plano Premium Plus");
        planoAtualizado.setValorMensal(new BigDecimal("199.90"));

        when(planoRepository.findById(1L)).thenReturn(Optional.of(plano));
        when(planoRepository.save(any(Plano.class))).thenReturn(plano);

        // When
        PlanoDto resultado = planoService.atualizarPlano(1L, planoAtualizado);

        // Then
        assertThat(resultado).isNotNull();
        verify(planoRepository, times(1)).save(any(Plano.class));
    }

    @Test
    void deletarPlano_ComIdExistente_DeveExecutarDelete() {
        // Given
        when(planoRepository.existsById(1L)).thenReturn(true);

        // When
        planoService.deletarPlano(1L);

        // Then
        verify(planoRepository, times(1)).deleteById(1L);
    }

    @Test
    void deletarPlano_ComIdInexistente_DeveLancarResourceNotFoundException() {
        // Given
        when(planoRepository.existsById(1L)).thenReturn(false);

        // When & Then
        assertThatThrownBy(() -> planoService.deletarPlano(1L))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessage("Plano não encontrado");
    }
}
