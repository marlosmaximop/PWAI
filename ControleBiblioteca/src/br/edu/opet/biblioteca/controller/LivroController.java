package br.edu.opet.biblioteca.controller;

import java.util.List;

import br.edu.opet.biblioteca.dao.DaoFactory;
import br.edu.opet.biblioteca.dao.EmprestimoDAO;
import br.edu.opet.biblioteca.dao.ExemplarDAO;
import br.edu.opet.biblioteca.dao.LivroDAO;
import br.edu.opet.biblioteca.dto.LivroDTO;
import br.edu.opet.biblioteca.enums.StatusEmprestimo;
import br.edu.opet.biblioteca.jdbc.Conexao;
import br.edu.opet.biblioteca.model.Emprestimo;
import br.edu.opet.biblioteca.model.Exemplar;
import br.edu.opet.biblioteca.model.Livro;

public class LivroController
{
    public static LivroDTO cadastrar(Livro pLivro, int pQtdExemplares)
    {
        if (pLivro == null)
            return new LivroDTO(false, "Tentativa de inserir um livro nulo");

        // Criando os objetos de persistência/recuperação
        LivroDAO tDaoLivro = DaoFactory.getLivroDAO();
        ExemplarDAO tDaoExemplar = DaoFactory.getExemplarDAO();
        
        // Iniciando a transação
        Conexao.iniciarTransacao();
        
        // Persistindo o objeto
        Livro tLivro = tDaoLivro.create(pLivro);

        // Verificando se houve erro de criação
        if (tLivro == null)
        {
            // Desfazendo a transação
            Conexao.desfazerTransacao();
            
            // Retornando o erro
            return new LivroDTO(false, "Problemas no cadastramento do livro");
        }
        
        // Criando os exemplares do livro
        for (int i = 0; i < pQtdExemplares; i++)
        {
            // Criando o objeto exemplar
            Exemplar tExemplar = new Exemplar(0, tLivro, false);
            
            // Persistindo o exemplar
            if (tDaoExemplar.create(tExemplar) == null)
            {
                // Desfazendo a transação
                Conexao.desfazerTransacao();
                
                // Retornando o erro
                return new LivroDTO(false, "Problemas no cadastramento do exemplar " + (i+1));
            }
        }
        
        // Confirmando a transação
        Conexao.confirmarTransacao();

        // Retornando o indicativo de sucesso com o objeto criado
        return new LivroDTO(true, "Livro cadastrado com sucesso", tLivro);
    }

    public static LivroDTO recuperar(int pIsbn)
    {
        // Criando o objeto de persistência/recuperação
        LivroDAO tDaoLivro = DaoFactory.getLivroDAO();
        
        // Lendo o objeto
        Livro tLivro = tDaoLivro.recovery(pIsbn);

        // Verificando se houve erro de recuperação
        if (tLivro == null)
            return new LivroDTO(false, "Livro não existe no cadastro");

        // Retornando o indicativo de sucesso com o objeto recuperado
        return new LivroDTO(true, "Livro recuperado com sucesso", tLivro);
    }

    // Método para atualizar um livro
    public static LivroDTO atualizar(Livro pLivro)
    {
        if (pLivro == null)
            return new LivroDTO(false, "Tentativa de atualizar um livro nulo");

        // Chamando a camada de persistência
        LivroDAO tDaoLivro = DaoFactory.getLivroDAO();
        Livro tLivro = tDaoLivro.update(pLivro);

        // Verificando se houve erro de atualização
        if (tLivro == null)
            return new LivroDTO(false, "Livro não existe no cadastro");

        // Retornando o indicativo de sucesso com o objeto atualizado
        return new LivroDTO(true, "Livro atualizado com sucesso", tLivro);
    }

    public static LivroDTO remover(int pIsbn)
    {
        // Criando os objetos de persistência/recuperação
        EmprestimoDAO tDaoEmprestimo = DaoFactory.getEmprestimoDAO();
        ExemplarDAO tDaoExemplar = DaoFactory.getExemplarDAO();
        LivroDAO tDaoLivro = DaoFactory.getLivroDAO();
        
        // Lendo o objeto
        Livro tLivro = tDaoLivro.recovery(pIsbn);

        // Verificando se houve erro de recuperação
        if (tLivro == null)
            return new LivroDTO(false, "Livro não existe no cadastro");
        
        // Verificando se o livro não tem algum exemplar emprestado
        List<Exemplar> tListaExemplar = tDaoExemplar.searchByLivro(tLivro);
        for (Exemplar tExemplar : tListaExemplar)
        {
            // Para cada exemplar, recupera a lista de empréstimos
            List<Emprestimo> tListaEmprestimo = tDaoEmprestimo.searchByExemplar(tExemplar);
            for (Emprestimo tEmprestimo : tListaEmprestimo)
            {
                if (tEmprestimo.getStatus() == StatusEmprestimo.EMPRESTADO)
                    return new LivroDTO(false, "Livro com empréstimo vigente");
            }
        }
        
        // Iniciando a transação
        Conexao.iniciarTransacao();

        // Removendo os exemplares do livro
        
        // Removendo o livro 
        tDaoLivro.delete(pIsbn);
        
        // Confirmando a transação
        Conexao.confirmarTransacao();

        // Retornando o indicativo de sucesso com o objeto removido
        return new LivroDTO(true, "Livro removido com sucesso");
    }

    public static LivroDTO pesquisar()
    {
        // Criando o objeto de persistência/recuperação
        LivroDAO tDaoLivro = DaoFactory.getLivroDAO();

        // Obtendo a lista de livros
        List<Livro> tLista = tDaoLivro.search();

        // Verificando se a lista está vazia
        if (tLista.isEmpty())
            return new LivroDTO(false, "Lista de livros vazia");

        // Retornando a lista obtida
        return new LivroDTO(true, "Lista de livros recuperada", tLista);
    }

    public static LivroDTO pesquisarPorNome(String pNome)
    {
        // Caso o nome de pesquisa seja nulo, retorna a lista geral
        if (pNome == null)
            return pesquisar();

        // Criando o objeto de persistência/recuperação
        LivroDAO tDaoLivro = DaoFactory.getLivroDAO();

        // Obtendo a lista de livros
        List<Livro> tLista = tDaoLivro.searchByNome(pNome);

        // Verificando se a lista está vazia
        if (tLista.isEmpty())
            return new LivroDTO(false, "Nenhum registro encontrado com nome '" + pNome + "'");

        // Retornando a lista obtida
        return new LivroDTO(true, "Lista de livros recuperada", tLista);
    }

    public static LivroDTO pesquisarPorEmail(String pEmail)
    {
        // Caso o email de pesquisa seja nulo, retorna a lista geral
        if (pEmail == null)
            return pesquisar();

        // Criando o objeto de persistência/recuperação
        LivroDAO tDaoLivro = DaoFactory.getLivroDAO();

        // Obtendo a lista de livros
        List<Livro> tLista = tDaoLivro.searchByEmail(pEmail);

        // Verificando se a lista está vazia
        if (tLista.isEmpty())
            return new LivroDTO(false, "Nenhum registro encontrado com email '" + pEmail + "'");

        // Retornando a lista obtida
        return new LivroDTO(true, "Lista de livros recuperada", tLista);
    }

    public static LivroDTO pesquisarPorCurso(String pCurso)
    {
        // Caso o email de pesquisa seja nulo, retorna a lista geral
        if (pCurso == null)
            return pesquisar();

        // Criando o objeto de persistência/recuperação
        LivroDAO tDaoLivro = DaoFactory.getLivroDAO();

        // Obtendo a lista de livros
        List<Livro> tLista = tDaoLivro.searchByCurso(pCurso);

        // Verificando se a lista está vazia
        if (tLista.isEmpty())
            return new LivroDTO(false, "Nenhum registro encontrado com curso '" + pCurso + "'");

        // Retornando a lista obtida
        return new LivroDTO(true, "Lista de livros recuperada", tLista);
    }
}
