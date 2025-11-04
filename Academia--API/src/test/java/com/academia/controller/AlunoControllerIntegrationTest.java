package com.academia.controller;

import com.academia.dto.CreateAlunoDto;
import com.academia.model.Aluno;
import com.academia.model.Plano;
import com.academia.repository.AlunoRepository;
import com.academia.repository.PlanoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
@ActiveProfiles("test")
@Transactional
class AlunoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private PlanoRepository planoRepository;

    private Plano plano;

    @BeforeEach
    void setUp() {
        alunoRepository.deleteAll();
        planoRepository.deleteAll();

        // Criar plano para testes
        plano = new Plano();
        plano.setNome("Plano Teste");
        plano.setValorMensal(new BigDecimal("99.90"));
        plano = planoRepository.save(plano);
    }

    @Test
    void criarAluno_ComDadosValidos_DeveRetornar201() throws Exception {
        CreateAlunoDto dto = new CreateAlunoDto();
        dto.setNome("João Silva");
        dto.setEmail("joao@teste.com");
        dto.setDataNascimento(LocalDate.of(1990, 5, 15));
        dto.setPlanoId(plano.getId());

        mockMvc.perform(post("/api/alunos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.nome").exists());
    }

    @Test
    void criarAluno_ComEmailInvalido_DeveRetornar400() throws Exception {
        CreateAlunoDto dto = new CreateAlunoDto();
        dto.setNome("João Silva");
        dto.setEmail("email-invalido");
        dto.setDataNascimento(LocalDate.of(1990, 5, 15));

        mockMvc.perform(post("/api/alunos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email").exists());
    }

    @Test
    void criarAluno_ComIdadeMenorQue16Anos_DeveRetornar400() throws Exception {
        CreateAlunoDto dto = new CreateAlunoDto();
        dto.setNome("João Silva");
        dto.setEmail("joao@teste.com");
        dto.setDataNascimento(LocalDate.now().minusYears(15));
        dto.setPlanoId(plano.getId());

        mockMvc.perform(post("/api/alunos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Idade mínima permitida é 16 anos")));
    }

    @Test
    void listarAlunos_DeveRetornarListaPaginada() throws Exception {
        // Criar alunos para teste
        Aluno aluno1 = new Aluno();
        aluno1.setNome("João Silva");
        aluno1.setEmail("joao@teste.com");
        aluno1.setDataNascimento(LocalDate.of(1990, 5, 15));
        aluno1.setPlano(plano);
        alunoRepository.save(aluno1);

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Maria Santos");
        aluno2.setEmail("maria@teste.com");
        aluno2.setDataNascimento(LocalDate.of(1995, 8, 20));
        aluno2.setPlano(plano);
        alunoRepository.save(aluno2);

        mockMvc.perform(get("/api/alunos")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].nome").exists())
                .andExpect(jsonPath("$.totalElements", is(2)));
    }

    @Test
    void buscarPorId_ComIdExistente_DeveRetornarAluno() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("João Silva");
        aluno.setEmail("joao@teste.com");
        aluno.setDataNascimento(LocalDate.of(1990, 5, 15));
        aluno.setPlano(plano);
        aluno = alunoRepository.save(aluno);

        mockMvc.perform(get("/api/alunos/{id}", aluno.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(aluno.getId().intValue())))
                .andExpect(jsonPath("$.nome", is("João Silva")))
                .andExpect(jsonPath("$.email", is("joao@teste.com")));
    }

    @Test
    void buscarPorId_ComIdInexistente_DeveRetornar404() throws Exception {
        mockMvc.perform(get("/api/alunos/{id}", 999L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Aluno não encontrado")));
    }

    @Test
    void atualizarAluno_ComDadosValidos_DeveRetornar200() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("João Silva");
        aluno.setEmail("joao@teste.com");
        aluno.setDataNascimento(LocalDate.of(1990, 5, 15));
        aluno.setPlano(plano);
        aluno = alunoRepository.save(aluno);

        CreateAlunoDto dto = new CreateAlunoDto();
        dto.setNome("João Silva Atualizado");
        dto.setEmail("joao.novo@teste.com");
        dto.setDataNascimento(LocalDate.of(1990, 5, 15));
        dto.setPlanoId(plano.getId());

        mockMvc.perform(put("/api/alunos/{id}", aluno.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("João Silva Atualizado")))
                .andExpect(jsonPath("$.email", is("joao.novo@teste.com")));
    }

    @Test
    void deletarAluno_ComIdExistente_DeveRetornar204() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("João Silva");
        aluno.setEmail("joao@teste.com");
        aluno.setDataNascimento(LocalDate.of(1990, 5, 15));
        aluno = alunoRepository.save(aluno);

        mockMvc.perform(delete("/api/alunos/{id}", aluno.getId()))
                .andExpect(status().isNoContent());

        // Verificar que foi deletado
        mockMvc.perform(get("/api/alunos/{id}", aluno.getId()))
                .andExpect(status().isNotFound());
    }
}
