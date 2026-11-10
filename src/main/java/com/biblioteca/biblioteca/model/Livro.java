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
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "livro")
@Schema(description = "Livro disponível na biblioteca")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "Identificador do livro", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Título é obrigatório")
    @Size(min = 2, max = 150, message = "Título deve ter entre 2 e 150 caracteres")
    @Schema(description = "Título do livro", example = "Dom Casmurro")
    private String titulo;

    @Column(nullable = false, unique = true, length = 13)
    @NotBlank(message = "ISBN é obrigatório")
    @Pattern(regexp = "\\d{10}|\\d{13}", message = "ISBN deve conter 10 ou 13 números")
    @Schema(description = "ISBN com 10 ou 13 números", example = "9788535910663")
    private String isbn;

    @Column(nullable = false)
    @Schema(description = "Informa se o livro pode ser emprestado", example = "true", accessMode = Schema.AccessMode.READ_ONLY)
    private boolean disponivel = true;

    @NotNull(message = "Autor é obrigatório")
    @ManyToOne(optional = false)
    @JoinColumn(name = "autor_id", nullable = false)
    @Schema(description = "Autor do livro. No cadastro, informe ao menos o id")
    private Autor autor;
}
