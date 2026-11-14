package com.biblioteca.biblioteca.service;

import com.biblioteca.biblioteca.model.Emprestimo;
import com.biblioteca.biblioteca.model.Livro;
import com.biblioteca.biblioteca.repository.EmprestimoRepository;
import com.biblioteca.biblioteca.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmprestimoService {

    private static final int PRAZO_EM_DIAS = 14;

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Transactional
    public Emprestimo cadastrar(Emprestimo emprestimo) {
        if (emprestimo.getLivro() == null || emprestimo.getLivro().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe o id do livro");
        }

        Livro livro = livroRepository.findById(emprestimo.getLivro().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado"));

        if (!livro.isDisponivel()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Livro indisponível para empréstimo");
        }

        LocalDate hoje = LocalDate.now();
        emprestimo.setLivro(livro);
        emprestimo.setDataEmprestimo(hoje);
        emprestimo.setDataPrevistaDevolucao(hoje.plusDays(PRAZO_EM_DIAS));
        emprestimo.setDataDevolucao(null);
        emprestimo.setDevolvido(false);

        livro.setDisponivel(false);
        livroRepository.save(livro);
        return emprestimoRepository.save(emprestimo);
    }

    public List<Emprestimo> listar() {
        return emprestimoRepository.findAll();
    }

    public Emprestimo pesquisar(Long id) {
        return emprestimoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empréstimo não encontrado"));
    }

    @Transactional
    public Emprestimo devolver(Long id) {
        Emprestimo emprestimo = pesquisar(id);

        if (emprestimo.isDevolvido()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este empréstimo já foi devolvido");
        }

        emprestimo.setDevolvido(true);
        emprestimo.setDataDevolucao(LocalDate.now());
        emprestimo.getLivro().setDisponivel(true);
        livroRepository.save(emprestimo.getLivro());
        return emprestimoRepository.save(emprestimo);
    }
}
