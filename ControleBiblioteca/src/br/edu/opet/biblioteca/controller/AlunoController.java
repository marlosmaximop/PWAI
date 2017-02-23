package br.edu.opet.biblioteca.controller;

import java.util.List;

import br.edu.opet.biblioteca.dao.AlunoDAO;
import br.edu.opet.biblioteca.dao.DaoFactory;
import br.edu.opet.biblioteca.dao.EmprestimoDAO;
import br.edu.opet.biblioteca.dto.AlunoDTO;
import br.edu.opet.biblioteca.enums.StatusEmprestimo;
import br.edu.opet.biblioteca.model.Aluno;
import br.edu.opet.biblioteca.model.Emprestimo;

public class AlunoController
{
    public static AlunoDTO cadastrar(Aluno pAluno)
    {
        if (pAluno == null)
            return new AlunoDTO(false, "Tentativa de inserir um aluno nulo");

        // Criando o objeto de persistência/recuperação
        AlunoDAO tDaoAluno = DaoFactory.getAlunoDAO();
        
        // Persistindo o objeto
        Aluno tAluno = tDaoAluno.create(pAluno);

        // Verificando se houve erro de criação
        if (tAluno == null)
            return new AlunoDTO(false, "Problemas no cadastramento do aluno");

        // Retornando o indicativo de sucesso com o objeto criado
        return new AlunoDTO(true, "Aluno cadastrado com sucesso", tAluno);
    }

    public static AlunoDTO recuperar(int pMatricula)
    {
        // Criando o objeto de persistência/recuperação
        AlunoDAO tDaoAluno = DaoFactory.getAlunoDAO();
        
        // Lendo o objeto
        Aluno tAluno = tDaoAluno.recovery(pMatricula);

        // Verificando se houve erro de recuperação
        if (tAluno == null)
            return new AlunoDTO(false, "Aluno não existe no cadastro");

        // Retornando o indicativo de sucesso com o objeto recuperado
        return new AlunoDTO(true, "Aluno recuperado com sucesso", tAluno);
    }

    // Método para atualizar um aluno
    public static AlunoDTO atualizar(Aluno pAluno)
    {
        if (pAluno == null)
            return new AlunoDTO(false, "Tentativa de atualizar um aluno nulo");

        // Chamando a camada de persistência
        AlunoDAO tDaoAluno = DaoFactory.getAlunoDAO();
        Aluno tAluno = tDaoAluno.update(pAluno);

        // Verificando se houve erro de atualização
        if (tAluno == null)
            return new AlunoDTO(false, "Aluno não existe no cadastro");

        // Retornando o indicativo de sucesso com o objeto atualizado
        return new AlunoDTO(true, "Aluno atualizado com sucesso", tAluno);
    }

    public static AlunoDTO remover(int pMatricula)
    {
        // Criando os objetos de persistência/recuperação
        EmprestimoDAO tDaoEmprestimo = DaoFactory.getEmprestimoDAO();
        AlunoDAO tDaoAluno = DaoFactory.getAlunoDAO();
        
        // Lendo o objeto
        Aluno tAluno = tDaoAluno.recovery(pMatricula);

        // Verificando se houve erro de recuperação
        if (tAluno == null)
            return new AlunoDTO(false, "Aluno não existe no cadastro");
        
        // Verificando se o aluno não tem empréstimo válido
        List<Emprestimo> tLista = tDaoEmprestimo.searchByAluno(tAluno);
        for (Emprestimo tEmprestimo : tLista)
        {
            if (tEmprestimo.getStatus() == StatusEmprestimo.EMPRESTADO)
                return new AlunoDTO(false, "Aluno com empréstimo vigente");
        }

        // Removendo o aluno 
        tDaoAluno.delete(pMatricula);

        // Retornando o indicativo de sucesso com o objeto removido
        return new AlunoDTO(true, "Aluno removido com sucesso");
    }

    public static AlunoDTO pesquisar()
    {
        // Criando o objeto de persistência/recuperação
        AlunoDAO tDaoAluno = DaoFactory.getAlunoDAO();

        // Obtendo a lista de alunos
        List<Aluno> tLista = tDaoAluno.search();

        // Verificando se a lista está vazia
        if (tLista.isEmpty())
            return new AlunoDTO(false, "Lista de alunos vazia");

        // Retornando a lista obtida
        return new AlunoDTO(true, "Lista de alunos recuperada", tLista);
    }

    public static AlunoDTO pesquisarPorNome(String pNome)
    {
        // Caso o nome de pesquisa seja nulo, retorna a lista geral
        if (pNome == null)
            return pesquisar();

        // Criando o objeto de persistência/recuperação
        AlunoDAO tDaoAluno = DaoFactory.getAlunoDAO();

        // Obtendo a lista de alunos
        List<Aluno> tLista = tDaoAluno.searchByNome(pNome);

        // Verificando se a lista está vazia
        if (tLista.isEmpty())
            return new AlunoDTO(false, "Nenhum registro encontrado com nome '" + pNome + "'");

        // Retornando a lista obtida
        return new AlunoDTO(true, "Lista de alunos recuperada", tLista);
    }

    public static AlunoDTO pesquisarPorEmail(String pEmail)
    {
        // Caso o email de pesquisa seja nulo, retorna a lista geral
        if (pEmail == null)
            return pesquisar();

        // Criando o objeto de persistência/recuperação
        AlunoDAO tDaoAluno = DaoFactory.getAlunoDAO();

        // Obtendo a lista de alunos
        List<Aluno> tLista = tDaoAluno.searchByEmail(pEmail);

        // Verificando se a lista está vazia
        if (tLista.isEmpty())
            return new AlunoDTO(false, "Nenhum registro encontrado com email '" + pEmail + "'");

        // Retornando a lista obtida
        return new AlunoDTO(true, "Lista de alunos recuperada", tLista);
    }

    public static AlunoDTO pesquisarPorCurso(String pCurso)
    {
        // Caso o email de pesquisa seja nulo, retorna a lista geral
        if (pCurso == null)
            return pesquisar();

        // Criando o objeto de persistência/recuperação
        AlunoDAO tDaoAluno = DaoFactory.getAlunoDAO();

        // Obtendo a lista de alunos
        List<Aluno> tLista = tDaoAluno.searchByCurso(pCurso);

        // Verificando se a lista está vazia
        if (tLista.isEmpty())
            return new AlunoDTO(false, "Nenhum registro encontrado com curso '" + pCurso + "'");

        // Retornando a lista obtida
        return new AlunoDTO(true, "Lista de alunos recuperada", tLista);
    }
}
