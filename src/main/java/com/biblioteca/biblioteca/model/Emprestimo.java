package com.biblioteca.biblioteca.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "emprestimo")
@Schema(description = "Empréstimo de um livro para um leitor")
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "Identificador do empréstimo", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Nome do leitor é obrigatório")
    @Size(min = 3, max = 100, message = "Nome do leitor deve ter entre 3 e 100 caracteres")
    @Schema(description = "Nome da pessoa que recebeu o livro", example = "Ana Silva")
    private String nomeLeitor;

    @Column(nullable = false)
    @Schema(description = "Data do empréstimo", example = "2026-11-06", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDate dataEmprestimo;

    @Column(nullable = false)
    @Schema(description = "Prazo de devolução", example = "2026-11-20", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDate dataPrevistaDevolucao;

    @Schema(description = "Data em que o livro foi devolvido", example = "2026-11-15", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDate dataDevolucao;

    @Column(nullable = false)
    @Schema(description = "Informa se o empréstimo já foi encerrado", example = "false", accessMode = Schema.AccessMode.READ_ONLY)
    private boolean devolvido;

    @NotNull(message = "Livro é obrigatório")
    @ManyToOne(optional = false)
    @JoinColumn(name = "livro_id", nullable = false)
    @Schema(description = "Livro emprestado. No cadastro, informe ao menos o id")
    private Livro livro;
}
