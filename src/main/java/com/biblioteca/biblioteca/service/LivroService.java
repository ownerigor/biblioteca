package com.biblioteca.biblioteca.service;

import com.biblioteca.biblioteca.model.Autor;
import com.biblioteca.biblioteca.model.Livro;
import com.biblioteca.biblioteca.repository.AutorRepository;
import com.biblioteca.biblioteca.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AutorRepository autorRepository;

    public Livro cadastrar(Livro livro) {
        livro.setAutor(pesquisarAutor(livro));
        livro.setDisponivel(true);
        return livroRepository.save(livro);
    }

    public List<Livro> listar() {
        return livroRepository.findAll();
    }

    public List<Livro> listarDisponiveis() {
        return livroRepository.findByDisponivelTrue();
    }

    public Livro pesquisar(Long id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado"));
    }

    public Livro atualizar(Long id, Livro livroAtualizado) {
        Livro livro = pesquisar(id);
        livro.setTitulo(livroAtualizado.getTitulo());
        livro.setIsbn(livroAtualizado.getIsbn());
        livro.setAutor(pesquisarAutor(livroAtualizado));
        return livroRepository.save(livro);
    }

    public void deletar(Long id) {
        Livro livro = pesquisar(id);
        livroRepository.delete(livro);
    }

    private Autor pesquisarAutor(Livro livro) {
        if (livro.getAutor() == null || livro.getAutor().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe o id do autor");
        }

        return autorRepository.findById(livro.getAutor().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Autor não encontrado"));
    }
}
