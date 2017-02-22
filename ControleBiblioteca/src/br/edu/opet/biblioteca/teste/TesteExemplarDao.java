package br.edu.opet.biblioteca.teste;

import java.util.List;

import br.edu.opet.biblioteca.dao.DaoFactory;
import br.edu.opet.biblioteca.dao.ExemplarDAO;
import br.edu.opet.biblioteca.model.Exemplar;
import br.edu.opet.biblioteca.model.Livro;

public class TesteExemplarDao
{
    public static void main(String[] args)
    {
        // Obtendo o objeto de persistência
        ExemplarDAO tDao = DaoFactory.getExemplarDAO();

        // Criando os exemplares para inclusão
        Livro tLivro1 = new Livro(9_788_539_605_378L, "Cozinha Prática", "Rita Lobo", "Senac SP", 2016, 1, 61.40);
        Livro tLivro2 = new Livro();
        tLivro2.setIsbn(9_788_567_431_024L);
        tLivro2.setTitulo("Pitadas da Rita");
        tLivro2.setAutor("Rita Lobo");
        tLivro2.setEditora("Panelinha");
        tLivro2.setAnoEdicao(2014);
        tLivro2.setEdicao(1);
        tLivro2.setValorCompra(58.16);
        
        Exemplar tExemplar1 = new Exemplar(11111, tLivro1, false);

        Exemplar tExemplar2 = new Exemplar();
        tExemplar2.setId(22222);
        tExemplar2.setLivro(tLivro2);
        tExemplar2.setExemplarLocal(true);

        System.out.println();
        System.out.println("Gravando os exemplares");
        if (tDao.create(tExemplar1) != null)
            System.out.println("Exemplar 1 gravado com sucesso");
        else
            System.out.println("Problemas na gravação do exemplar 1");

        if (tDao.create(tExemplar2) != null)
            System.out.println("Exemplar 2 gravado com sucesso");
        else
            System.out.println("Problemas na gravação do exemplar 2");

        System.out.println();
        System.out.println("Lendo os exemplares");
        tExemplar1 = tDao.recovery(tExemplar1.getId());
        if (tExemplar1 != null)
            System.out.println("Exemplar 1 : " + tExemplar1);
        else
            System.out.println("Problemas na leitura do exemplar 1");
        tExemplar2 = tDao.recovery(tExemplar2.getId());
        if (tExemplar2 != null)
            System.out.println("Exemplar 2 : " + tExemplar2);
        else
            System.out.println("Problemas na leitura do exemplar 1");

        tExemplar1.setExemplarLocal(true);
        tExemplar1.setLivro(tLivro2);

        tExemplar2.setExemplarLocal(false);
        tExemplar2.setLivro(tLivro1);

        System.out.println();
        System.out.println("Atualizando os exemplares");
        if (tDao.update(tExemplar1) != null)
            System.out.println("Exemplar 1 atualizado com sucesso");
        else
            System.out.println("Problemas na atualização do exemplar 1");

        if (tDao.update(tExemplar2) != null)
            System.out.println("Exemplar 2 atualizado com sucesso");
        else
            System.out.println("Problemas na atualização do exemplar 2");
        
        List<Exemplar> tLista1 = tDao.search();

        System.out.println();
        System.out.println("Lista geral de exemplares cadastrados");
        int i = 1;
        for (Exemplar tExemplar : tLista1)
        {
            System.out.println("Exemplar " + i++ + " : " + tExemplar);
        }
        
        System.out.println();
        System.out.println("Removendo os exemplares");
        if (tDao.delete(tExemplar1.getId()))
            System.out.println("Exemplar 1 removido com sucesso");
        else
            System.out.println("Problemas na remoção do exemplar 1");

        if (tDao.delete(tExemplar2.getId()))
            System.out.println("Exemplar 2 removido com sucesso");
        else
            System.out.println("Problemas na remoção do exemplar 2");
    }
}
