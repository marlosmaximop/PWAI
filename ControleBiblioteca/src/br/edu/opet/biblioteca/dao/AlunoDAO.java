package br.edu.opet.biblioteca.dao;

import java.util.List;

import br.edu.opet.biblioteca.model.Aluno;

public interface AlunoDAO
{
    // Método para criar um contato na base de dados (INSERT)
    Aluno create(Aluno pAluno);

    // Método para recuperar um contato da base de dados (SELECT)
    Aluno recovery(long pMatricula);

    // Método para atualizar um contato na base de dados (UPDATE)
    Aluno update(Aluno pAluno);

    // Método para deletar um contato na base de dados (DELETE)
    boolean delete(long pMatricula);

    // Método para pesquisar todos os contatos da base de dados
    List<Aluno> search();

    // Método para pesquisar por nome todos os contatos da base de dados
    List<Aluno> searchByNome(String pNome);

    // Método para pesquisar por email todos os contatos da base de dados
    List<Aluno> searchByEmail(String pEmail);

    // Método para pesquisar por curso todos os contatos da base de dados
    List<Aluno> searchByCurso(String pCurso);
}
