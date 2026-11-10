package com.biblioteca.biblioteca.controller;

import com.biblioteca.biblioteca.model.Livro;
import com.biblioteca.biblioteca.service.LivroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Livros", description = "Cadastro e consulta do acervo")
@RestController
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @Operation(summary = "Cadastrar livro", description = "Cadastra um livro associado a um autor existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Livro cadastrado",
                    content = @Content(schema = @Schema(implementation = Livro.class),
                            examples = @ExampleObject(value = "{\"id\":1,\"titulo\":\"Dom Casmurro\",\"isbn\":\"9788535910663\",\"disponivel\":true,\"autor\":{\"id\":1,\"nome\":\"Machado de Assis\",\"nacionalidade\":\"Brasileira\"}}"))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Livro> cadastrar(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do livro e id do autor",
                    required = true,
                    content = @Content(examples = @ExampleObject(value = "{\"titulo\":\"Dom Casmurro\",\"isbn\":\"9788535910663\",\"autor\":{\"id\":1}}")))
            @Valid @RequestBody Livro livro) {
        return ResponseEntity.status(HttpStatus.CREATED).body(livroService.cadastrar(livro));
    }

    @Operation(summary = "Listar livros", description = "Retorna todos os livros do acervo.")
    @ApiResponse(responseCode = "200", description = "Livros encontrados")
    @GetMapping
    public List<Livro> listar() {
        return livroService.listar();
    }

    @Operation(summary = "Listar livros disponíveis", description = "Retorna somente os livros disponíveis para empréstimo.")
    @ApiResponse(responseCode = "200", description = "Livros disponíveis encontrados")
    @GetMapping("/disponiveis")
    public List<Livro> listarDisponiveis() {
        return livroService.listarDisponiveis();
    }

    @Operation(summary = "Buscar livro por id", description = "Retorna um livro pelo seu identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Livro encontrado"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public Livro pesquisar(
            @Parameter(description = "Identificador do livro", example = "1", required = true)
            @PathVariable Long id) {
        return livroService.pesquisar(id);
    }

    @Operation(summary = "Atualizar livro", description = "Atualiza título, ISBN e autor do livro, sem alterar sua disponibilidade.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Livro atualizado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Livro ou autor não encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public Livro atualizar(
            @Parameter(description = "Identificador do livro", example = "1", required = true)
            @PathVariable Long id,
            @Valid @RequestBody Livro livro) {
        return livroService.atualizar(id, livro);
    }

    @Operation(summary = "Excluir livro", description = "Exclui um livro pelo identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Livro excluído"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "Identificador do livro", example = "1", required = true)
            @PathVariable Long id) {
        livroService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
