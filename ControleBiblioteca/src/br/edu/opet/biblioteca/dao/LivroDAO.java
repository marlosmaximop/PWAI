package br.edu.opet.biblioteca.dao;

import java.util.List;

import br.edu.opet.biblioteca.model.Livro;

public interface LivroDAO
{
    // Método para criar um livro na base de dados (INSERT)
    Livro create(Livro pLivro);

    // Método para recuperar um livro da base de dados (SELECT)
    Livro recovery(long pIsbn);

    // Método para atualizar um livro na base de dados (UPDATE)
    Livro update(Livro pLivro);

    // Método para deletar um livro na base de dados (DELETE)
    boolean delete(long pIsbn);

    // Método para pesquisar todos os livros da base de dados
    List<Livro> search();

    // Método para pesquisar por nome todos os livros da base de dados
    List<Livro> searchByTitulo(String pTitulo);

    // Método para pesquisar por email todos os livros da base de dados
    List<Livro> searchByEditora(String pEditora);

    // Método para pesquisar por curso todos os livros da base de dados
    List<Livro> searchByAutor(String pAutor);
}
