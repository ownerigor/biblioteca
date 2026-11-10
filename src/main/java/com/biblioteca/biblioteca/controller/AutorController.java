package com.biblioteca.biblioteca.controller;

import com.biblioteca.biblioteca.model.Autor;
import com.biblioteca.biblioteca.service.AutorService;
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

@Tag(name = "Autores", description = "Cadastro e consulta de autores")
@RestController
@RequestMapping("/autores")
public class AutorController {

    @Autowired
    private AutorService autorService;

    @Operation(summary = "Cadastrar autor", description = "Cadastra um novo autor na biblioteca.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Autor cadastrado",
                    content = @Content(schema = @Schema(implementation = Autor.class),
                            examples = @ExampleObject(value = "{\"id\":1,\"nome\":\"Machado de Assis\",\"nacionalidade\":\"Brasileira\"}"))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Autor> cadastrar(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do autor",
                    required = true,
                    content = @Content(examples = @ExampleObject(value = "{\"nome\":\"Machado de Assis\",\"nacionalidade\":\"Brasileira\"}")))
            @Valid @RequestBody Autor autor) {
        return ResponseEntity.status(HttpStatus.CREATED).body(autorService.cadastrar(autor));
    }

    @Operation(summary = "Listar autores", description = "Retorna todos os autores cadastrados.")
    @ApiResponse(responseCode = "200", description = "Autores encontrados")
    @GetMapping
    public List<Autor> listar() {
        return autorService.listar();
    }

    @Operation(summary = "Buscar autor por id", description = "Retorna um autor pelo seu identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autor encontrado"),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public Autor pesquisar(
            @Parameter(description = "Identificador do autor", example = "1", required = true)
            @PathVariable Long id) {
        return autorService.pesquisar(id);
    }

    @Operation(summary = "Atualizar autor", description = "Atualiza nome e nacionalidade de um autor.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autor atualizado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public Autor atualizar(
            @Parameter(description = "Identificador do autor", example = "1", required = true)
            @PathVariable Long id,
            @Valid @RequestBody Autor autor) {
        return autorService.atualizar(id, autor);
    }

    @Operation(summary = "Excluir autor", description = "Exclui um autor pelo identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Autor excluído"),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "Identificador do autor", example = "1", required = true)
            @PathVariable Long id) {
        autorService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
