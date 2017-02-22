package br.edu.opet.biblioteca.dao;

import java.util.List;

import br.edu.opet.biblioteca.model.Aluno;

public interface AlunoDAO
{
    // Método para criar um aluno na base de dados (INSERT)
    Aluno create(Aluno pAluno);

    // Método para recuperar um aluno da base de dados (SELECT)
    Aluno recovery(long pMatricula);

    // Método para atualizar um aluno na base de dados (UPDATE)
    Aluno update(Aluno pAluno);

    // Método para deletar um aluno na base de dados (DELETE)
    boolean delete(long pMatricula);

    // Método para pesquisar todos os alunos da base de dados
    List<Aluno> search();

    // Método para pesquisar por nome todos os alunos da base de dados
    List<Aluno> searchByNome(String pNome);

    // Método para pesquisar por email todos os alunos da base de dados
    List<Aluno> searchByEmail(String pEmail);

    // Método para pesquisar por curso todos os alunos da base de dados
    List<Aluno> searchByCurso(String pCurso);
}
