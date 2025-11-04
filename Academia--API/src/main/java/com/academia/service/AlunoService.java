package com.academia.service;

import com.academia.dto.AlunoDto;
import com.academia.dto.CreateAlunoDto;
import com.academia.exception.BadRequestException;
import com.academia.exception.ResourceNotFoundException;
import com.academia.model.Aluno;
import com.academia.model.Plano;
import com.academia.repository.AlunoRepository;
import com.academia.repository.PlanoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;

@Service
@Transactional
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;
    
    @Autowired
    private PlanoRepository planoRepository;

    public Page<AlunoDto> listarAlunos(Pageable pageable) {
        return alunoRepository.findAll(pageable)
            .map(this::convertToDto);
    }

    public AlunoDto buscarPorId(Long id) {
        Aluno aluno = alunoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Aluno não encontrado"));
        return convertToDto(aluno);
    }

    public AlunoDto criarAluno(CreateAlunoDto dto) {
        validarIdadeMinima(dto.getDataNascimento());
        
        Plano plano = null;
        if (dto.getPlanoId() != null) {
            plano = planoRepository.findById(dto.getPlanoId())
                .orElseThrow(() -> new BadRequestException("Plano não encontrado"));
        }

        Aluno aluno = new Aluno();
        aluno.setNome(dto.getNome());
        aluno.setEmail(dto.getEmail());
        aluno.setDataNascimento(dto.getDataNascimento());
        aluno.setPlano(plano);

        try {
            Aluno alunoSalvo = alunoRepository.save(aluno);
            return convertToDto(alunoSalvo);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Email já cadastrado");
        }
    }

    public AlunoDto atualizarAluno(Long id, CreateAlunoDto dto) {
        Aluno aluno = alunoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Aluno não encontrado"));
        
        validarIdadeMinima(dto.getDataNascimento());
        
        Plano plano = null;
        if (dto.getPlanoId() != null) {
            plano = planoRepository.findById(dto.getPlanoId())
                .orElseThrow(() -> new BadRequestException("Plano não encontrado"));
        }

        aluno.setNome(dto.getNome());
        aluno.setEmail(dto.getEmail());
        aluno.setDataNascimento(dto.getDataNascimento());
        aluno.setPlano(plano);

        try {
            Aluno alunoAtualizado = alunoRepository.save(aluno);
            return convertToDto(alunoAtualizado);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Email já cadastrado");
        }
    }

    public void deletarAluno(Long id) {
        if (!alunoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Aluno não encontrado");
        }
        alunoRepository.deleteById(id);
    }

    private void validarIdadeMinima(LocalDate dataNascimento) {
        if (dataNascimento != null) {
            int idade = Period.between(dataNascimento, LocalDate.now()).getYears();
            if (idade < 16) {
                throw new BadRequestException("Idade mínima permitida é 16 anos");
            }
        }
    }

    private AlunoDto convertToDto(Aluno aluno) {
        AlunoDto dto = new AlunoDto();
        dto.setId(aluno.getId());
        dto.setNome(aluno.getNome());
        dto.setEmail(aluno.getEmail());
        dto.setDataNascimento(aluno.getDataNascimento());
        
        if (aluno.getPlano() != null) {
            PlanoDto planoDto = new PlanoDto();
            planoDto.setId(aluno.getPlano().getId());
            planoDto.setNome(aluno.getPlano().getNome());
            planoDto.setValorMensal(aluno.getPlano().getValorMensal());
            dto.setPlano(planoDto);
        }
        
        if (aluno.getTreinos() != null) {
            dto.setQuantidadeTreinos(aluno.getTreinos().size());
        }
        
        return dto;
    }
}
