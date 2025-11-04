package com.academia.service;

import com.academia.dto.AlunoDto;
import com.academia.dto.CreateAlunoDto;
import com.academia.exception.BadRequestException;
import com.academia.exception.ResourceNotFoundException;
import com.academia.model.Aluno;
import com.academia.model.Plano;
import com.academia.repository.AlunoRepository;
import com.academia.repository.PlanoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlunoServiceTest {

    @Mock
    private AlunoRepository alunoRepository;

    @Mock
    private PlanoRepository planoRepository;

    @InjectMocks
    private AlunoService alunoService;

    private CreateAlunoDto createAlunoDto;
    private Aluno aluno;
    private Plano plano;

    @BeforeEach
    void setUp() {
        // Setup Plano
        plano = new Plano();
        plano.setId(1L);
        plano.setNome("Plano Mensal");
        plano.setValorMensal(new BigDecimal("89.90"));

        // Setup CreateAlunoDto
        createAlunoDto = new CreateAlunoDto();
        createAlunoDto.setNome("João Silva");
        createAlunoDto.setEmail("joao@email.com");
        createAlunoDto.setDataNascimento(LocalDate.of(1990, 5, 15));
        createAlunoDto.setPlanoId(1L);

        // Setup Aluno
        aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("João Silva");
        aluno.setEmail("joao@email.com");
        aluno.setDataNascimento(LocalDate.of(1990, 5, 15));
        aluno.setPlano(plano);
    }

    @Test
    void criarAluno_ComDadosValidos_DeveRetornarAlunoDto() {
        // Given
        when(planoRepository.findById(1L)).thenReturn(Optional.of(plano));
        when(alunoRepository.save(any(Aluno.class))).thenReturn(aluno);

        // When
        AlunoDto resultado = alunoService.criarAluno(createAlunoDto);

        // Then
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getNome()).isEqualTo("João Silva");
        assertThat(resultado.getEmail()).isEqualTo("joao@email.com");
        assertThat(resultado.getPlano().getNome()).isEqualTo("Plano Mensal");
        
        verify(alunoRepository, times(1)).save(any(Aluno.class));
        verify(planoRepository, times(1)).findById(1L);
    }

    @Test
    void criarAluno_ComEmailDuplicado_DeveLancarBadRequestException() {
        // Given
        when(planoRepository.findById(1L)).thenReturn(Optional.of(plano));
        when(alunoRepository.save(any(Aluno.class)))
            .thenThrow(new DataIntegrityViolationException("Email já existe"));

        // When & Then
        assertThatThrownBy(() -> alunoService.criarAluno(createAlunoDto))
            .isInstanceOf(BadRequestException.class)
            .hasMessage("Email já cadastrado");
    }

    @Test
    void criarAluno_ComIdadeMenorQue16Anos_DeveLancarBadRequestException() {
        // Given
        createAlunoDto.setDataNascimento(LocalDate.now().minusYears(15));

        // When & Then
        assertThatThrownBy(() -> alunoService.criarAluno(createAlunoDto))
            .isInstanceOf(BadRequestException.class)
            .hasMessage("Idade mínima permitida é 16 anos");
    }

    @Test
    void criarAluno_ComPlanoInexistente_DeveLancarBadRequestException() {
        // Given
        when(planoRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> alunoService.criarAluno(createAlunoDto))
            .isInstanceOf(BadRequestException.class)
            .hasMessage("Plano não encontrado");
    }

    @Test
    void buscarPorId_ComIdExistente_DeveRetornarAlunoDto() {
        // Given
        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));

        // When
        AlunoDto resultado = alunoService.buscarPorId(1L);

        // Then
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getNome()).isEqualTo("João Silva");
    }

    @Test
    void buscarPorId_ComIdInexistente_DeveLancarResourceNotFoundException() {
        // Given
        when(alunoRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> alunoService.buscarPorId(1L))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessage("Aluno não encontrado");
    }

    @Test
    void deletarAluno_ComIdExistente_DeveExecutarDelete() {
        // Given
        when(alunoRepository.existsById(1L)).thenReturn(true);

        // When
        alunoService.deletarAluno(1L);

        // Then
        verify(alunoRepository, times(1)).deleteById(1L);
    }

    @Test
    void deletarAluno_ComIdInexistente_DeveLancarResourceNotFoundException() {
        // Given
        when(alunoRepository.existsById(1L)).thenReturn(false);

        // When & Then
        assertThatThrownBy(() -> alunoService.deletarAluno(1L))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessage("Aluno não encontrado");
    }
}
