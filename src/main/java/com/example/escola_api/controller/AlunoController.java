package com.example.escola_api.controller;

import com.example.escola_api.model.Aluno;
import com.example.escola_api.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    // Endpoint para listar todos os alunos
    @GetMapping
    public List<Aluno> getAllAlunos() {
        return alunoService.getAllAlunos();
    }

    // Endpoint para buscar um aluno por ID
    @GetMapping("/{id}")
    public ResponseEntity<Aluno> getAlunoById(@PathVariable Long id) {
        Optional<Aluno> aluno = alunoService.getAlunoById(id);
        if (aluno.isPresent()) {
            return ResponseEntity.ok(aluno.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para criar um novo aluno
    @PostMapping
    public Aluno createAluno(@RequestBody Aluno aluno) {
        return alunoService.saveAluno(aluno);
    }

    // Endpoint para atualizar um aluno existente
    @PutMapping("/{id}")
    public ResponseEntity<Aluno> updateAluno(@PathVariable Long id, @RequestBody Aluno alunoDetails) {
        Optional<Aluno> optionalAluno = alunoService.getAlunoById(id);
        if (optionalAluno.isPresent()) {
            Aluno aluno = optionalAluno.get();
            aluno.setNome(alunoDetails.getNome());
            aluno.setIdade(alunoDetails.getIdade());
            aluno.setNotaPrimeiroSemestre(alunoDetails.getNotaPrimeiroSemestre());
            aluno.setNotaSegundoSemestre(alunoDetails.getNotaSegundoSemestre());
            aluno.setNomeProfessor(alunoDetails.getNomeProfessor());
            aluno.setNumeroSala(alunoDetails.getNumeroSala());

            Aluno updatedAluno = alunoService.saveAluno(aluno);
            return ResponseEntity.ok(updatedAluno);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para deletar um aluno
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAluno(@PathVariable Long id) {
        Optional<Aluno> aluno = alunoService.getAlunoById(id);
        if (aluno.isPresent()) {
            alunoService.deleteAluno(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
