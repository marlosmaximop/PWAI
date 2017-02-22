package br.edu.opet.biblioteca.dao;

import java.util.List;

import br.edu.opet.biblioteca.model.Exemplar;

public interface ExemplarDAO
{
    // Método para criar um contato na base de dados (INSERT)
    Exemplar create(Exemplar pExemplar);

    // Método para recuperar um contato da base de dados (SELECT)
    Exemplar recovery(int pId);

    // Método para atualizar um contato na base de dados (UPDATE)
    Exemplar update(Exemplar pExemplar);

    // Método para deletar um contato na base de dados (DELETE)
    boolean delete(int pId);

    // Método para pesquisar todos os contatos da base de dados
    List<Exemplar> search();
}
