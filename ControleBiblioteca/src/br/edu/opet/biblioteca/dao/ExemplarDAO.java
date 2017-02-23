package br.edu.opet.biblioteca.dao;

import java.util.List;

import br.edu.opet.biblioteca.model.Exemplar;
import br.edu.opet.biblioteca.model.Livro;

public interface ExemplarDAO
{
    // Método para criar um exemplar na base de dados (INSERT)
    Exemplar create(Exemplar pExemplar);

    // Método para recuperar um exemplar da base de dados (SELECT)
    Exemplar recovery(int pId);

    // Método para atualizar um exemplar na base de dados (UPDATE)
    Exemplar update(Exemplar pExemplar);

    // Método para deletar um exemplar na base de dados (DELETE)
    boolean delete(int pId);

    // Método para pesquisar todos os exemplares da base de dados
    List<Exemplar> search();

    // Método para pesquisar por livro todos os exemplares da base de dados
    List<Exemplar> searchByLivro(Livro pLivro);
}
