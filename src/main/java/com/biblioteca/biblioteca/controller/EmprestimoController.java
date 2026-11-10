package com.biblioteca.biblioteca.controller;

import com.biblioteca.biblioteca.model.Emprestimo;
import com.biblioteca.biblioteca.service.EmprestimoService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Empréstimos", description = "Controle de empréstimos e devoluções")
@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {

    @Autowired
    private EmprestimoService emprestimoService;

    @Operation(summary = "Realizar empréstimo", description = "Empresta um livro disponível e calcula automaticamente o prazo de 14 dias.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Empréstimo realizado",
                    content = @Content(schema = @Schema(implementation = Emprestimo.class),
                            examples = @ExampleObject(value = "{\"id\":1,\"nomeLeitor\":\"Ana Silva\",\"dataEmprestimo\":\"2026-11-06\",\"dataPrevistaDevolucao\":\"2026-11-20\",\"dataDevolucao\":null,\"devolvido\":false,\"livro\":{\"id\":1,\"titulo\":\"Dom Casmurro\",\"isbn\":\"9788535910663\",\"disponivel\":false}}"))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado", content = @Content),
            @ApiResponse(responseCode = "409", description = "Livro indisponível", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Emprestimo> cadastrar(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Nome do leitor e id do livro",
                    required = true,
                    content = @Content(examples = @ExampleObject(value = "{\"nomeLeitor\":\"Ana Silva\",\"livro\":{\"id\":1}}")))
            @Valid @RequestBody Emprestimo emprestimo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(emprestimoService.cadastrar(emprestimo));
    }

    @Operation(summary = "Listar empréstimos", description = "Retorna todos os empréstimos cadastrados.")
    @ApiResponse(responseCode = "200", description = "Empréstimos encontrados")
    @GetMapping
    public List<Emprestimo> listar() {
        return emprestimoService.listar();
    }

    @Operation(summary = "Buscar empréstimo por id", description = "Retorna um empréstimo pelo seu identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empréstimo encontrado"),
            @ApiResponse(responseCode = "404", description = "Empréstimo não encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public Emprestimo pesquisar(
            @Parameter(description = "Identificador do empréstimo", example = "1", required = true)
            @PathVariable Long id) {
        return emprestimoService.pesquisar(id);
    }

    @Operation(summary = "Registrar devolução", description = "Encerra o empréstimo e torna o livro disponível novamente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Devolução registrada"),
            @ApiResponse(responseCode = "404", description = "Empréstimo não encontrado", content = @Content),
            @ApiResponse(responseCode = "409", description = "Empréstimo já devolvido", content = @Content)
    })
    @PatchMapping("/{id}/devolucao")
    public Emprestimo devolver(
            @Parameter(description = "Identificador do empréstimo", example = "1", required = true)
            @PathVariable Long id) {
        return emprestimoService.devolver(id);
    }
}
