package br.edu.opet.biblioteca.teste;

import java.math.BigDecimal;
import java.util.List;

import br.edu.opet.biblioteca.dao.DaoFactory;
import br.edu.opet.biblioteca.dao.LivroDAO;
import br.edu.opet.biblioteca.model.Livro;

public class TesteLivroDao
{
    public static void main(String[] args)
    {
        // Obtendo o objeto de persistência
        LivroDAO tDao = DaoFactory.getLivroDAO();

        // Criando os livros para inclusão
        Livro tLivro1 = new Livro(9_788_539_605_378L, "Cozinha Prática", "Rita Lobo", "Senac SP", 2016, 1, new BigDecimal("61.40"));
        
        Livro tLivro2 = new Livro();
        tLivro2.setIsbn(9_788_567_431_024L);
        tLivro2.setTitulo("Pitadas da Rita");
        tLivro2.setAutor("Rita Lobo");
        tLivro2.setEditora("Panelinha");
        tLivro2.setAnoEdicao(2014);
        tLivro2.setEdicao(1);
        tLivro2.setValorCompra(new BigDecimal("58.16"));

        System.out.println();
        System.out.println("Gravando os livros");
        if (tDao.create(tLivro1) != null)
            System.out.println("Livro 1 gravado com sucesso");
        else
            System.out.println("Problemas na gravação do livro 1");

        if (tDao.create(tLivro2) != null)
            System.out.println("Livro 2 gravado com sucesso");
        else
            System.out.println("Problemas na gravação do livro 2");

        System.out.println();
        System.out.println("Lendo os livros");
        tLivro1 = tDao.recovery(tLivro1.getIsbn());
        if (tLivro1 != null)
            System.out.println("Livro 1 : " + tLivro1);
        else
            System.out.println("Problemas na leitura do livro 1");
        tLivro2 = tDao.recovery(tLivro2.getIsbn());
        if (tLivro2 != null)
            System.out.println("Livro 2 : " + tLivro2);
        else
            System.out.println("Problemas na leitura do livro 1");

        tLivro1.setTitulo("Cozinha Prática com Rita Lobo");
        tLivro1.setAutor("Rita Lobo Souza");
        tLivro1.setEdicao(3);


        tLivro2.setTitulo("Pitadas da Rita Lobo");
        tLivro2.setAutor("Rita Lobo Souza");
        tLivro2.setEditora("Globo");

        System.out.println();
        System.out.println("Atualizando os livros");
        if (tDao.update(tLivro1) != null)
            System.out.println("Livro 1 atualizado com sucesso");
        else
            System.out.println("Problemas na atualização do livro 1");

        if (tDao.update(tLivro2) != null)
            System.out.println("Livro 2 atualizado com sucesso");
        else
            System.out.println("Problemas na atualização do livro 2");
        
        List<Livro> tLista1 = tDao.search();

        System.out.println();
        System.out.println("Lista geral de livros cadastrados");
        int i = 1;
        for (Livro tLivro : tLista1)
        {
            System.out.println("Livro " + i++ + " : " + tLivro);
        }
        
        List<Livro> tLista2 = tDao.searchByAutor("Lobo");

        System.out.println();
        System.out.println("Pesquisa por autor");
        i = 1;
        for (Livro tLivro : tLista2)
        {
            System.out.println("Livro " + i++ + " : " + tLivro);
        }
        
        
        List<Livro> tLista3 = tDao.searchByEditora("globo");

        System.out.println();
        System.out.println("Pesquisa por editora");
        i = 1;
        for (Livro tLivro : tLista3)
        {
            System.out.println("Livro " + i++ + " : " + tLivro);
        }
        
        
        List<Livro> tLista4 = tDao.searchByTitulo("prática");

        System.out.println();
        System.out.println("Pesquisa por título");
        i = 1;
        for (Livro tLivro : tLista4)
        {
            System.out.println("Livro " + i++ + " : " + tLivro);
        }
        

        System.out.println();
        System.out.println("Removendo os livros");
        if (tDao.delete(tLivro1.getIsbn()))
            System.out.println("Livro 1 removido com sucesso");
        else
            System.out.println("Problemas na remoção do livro 1");

        if (tDao.delete(tLivro2.getIsbn()))
            System.out.println("Livro 2 removido com sucesso");
        else
            System.out.println("Problemas na remoção do livro 2");
    }
}
