package br.edu.opet.biblioteca.dao;

import java.time.LocalDate;
import java.util.List;

import br.edu.opet.biblioteca.enums.StatusEmprestimo;
import br.edu.opet.biblioteca.model.Aluno;
import br.edu.opet.biblioteca.model.Emprestimo;

public interface EmprestimoDAO
{
    // Método para criar um contato na base de dados (INSERT)
    Emprestimo create(Emprestimo pEmprestimo);

    // Método para recuperar um contato da base de dados (SELECT)
    Emprestimo recovery(int pId);

    // Método para atualizar um contato na base de dados (UPDATE)
    Emprestimo update(Emprestimo pEmprestimo);

    // Método para deletar um contato na base de dados (DELETE)
    boolean delete(int pId);

    // Método para pesquisar todos os empréstimos da base de dados
    List<Emprestimo> search();

    // Método para pesquisar por curso todos os empréstimos da base de dados
    List<Emprestimo> searchByAluno(Aluno pAluno);

    // Método para pesquisar por nome todos os empréstimos da base de dados
    List<Emprestimo> searchByStatus(StatusEmprestimo pStatus);

    // Método para pesquisar por email todos os empréstimos da base de dados
    List<Emprestimo> searchByDataEmprestimo(LocalDate pDataEmprestimo);

    // Método para pesquisar por curso todos os empréstimos da base de dados
    List<Emprestimo> searchByDataDevolucao(LocalDate pDataDevolucao);
}
