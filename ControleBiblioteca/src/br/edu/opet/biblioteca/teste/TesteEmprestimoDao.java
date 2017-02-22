package br.edu.opet.biblioteca.teste;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import br.edu.opet.biblioteca.dao.AlunoDAO;
import br.edu.opet.biblioteca.dao.DaoFactory;
import br.edu.opet.biblioteca.dao.EmprestimoDAO;
import br.edu.opet.biblioteca.dao.ExemplarDAO;
import br.edu.opet.biblioteca.dao.LivroDAO;
import br.edu.opet.biblioteca.enums.SituacaoAluno;
import br.edu.opet.biblioteca.enums.StatusEmprestimo;
import br.edu.opet.biblioteca.model.Aluno;
import br.edu.opet.biblioteca.model.Emprestimo;
import br.edu.opet.biblioteca.model.Exemplar;
import br.edu.opet.biblioteca.model.Livro;

public class TesteEmprestimoDao
{
    public static void main(String[] args)
    {
        // Obtendo os objetos de persistência
        LivroDAO tDaoLivro = DaoFactory.getLivroDAO();
        AlunoDAO tDaoAluno = DaoFactory.getAlunoDAO();
        ExemplarDAO tDaoExemplar = DaoFactory.getExemplarDAO();
        EmprestimoDAO tDaoEmprestimo = DaoFactory.getEmprestimoDAO();

        // Criando os empréstimos para inclusão
        Livro tLivro1 = new Livro(9_788_539_605_378L, "Cozinha Prática", "Rita Lobo", "Senac SP", 2016, 1, new BigDecimal("61.40"));
        Livro tLivro2 = new Livro();
        tLivro2.setIsbn(9_788_567_431_024L);
        tLivro2.setTitulo("Pitadas da Rita");
        tLivro2.setAutor("Rita Lobo");
        tLivro2.setEditora("Panelinha");
        tLivro2.setAnoEdicao(2014);
        tLivro2.setEdicao(1);
        tLivro2.setValorCompra(new BigDecimal("58.16"));
        
        // Gravando os Livros
        System.out.println();
        System.out.println("Gravando os livros");
        tDaoLivro.create(tLivro1);
        tDaoLivro.create(tLivro2);
        
        Exemplar tExemplar1 = new Exemplar(11111, tLivro1, false);

        Exemplar tExemplar2 = new Exemplar();
        tExemplar2.setId(22222);
        tExemplar2.setLivro(tLivro2);
        tExemplar2.setExemplarLocal(true);
        
        // Gravando os Exemplares
        System.out.println();
        System.out.println("Gravando os exemplares");
        tDaoExemplar.create(tExemplar1);
        tDaoExemplar.create(tExemplar2);

        Aluno tAluno1 = new Aluno(2234772, "Circronvio Frandulfo", "Matemática", 4138878787L, "cirfran@gmail.com", SituacaoAluno.MATRICULADO);
        Aluno tAluno2 = new Aluno(22323882, "Sondravio Gulildo", "Matemática", 4321322323L, "songu@gmail.com", SituacaoAluno.FORMADO);
        
        // Gravando os Alunos
        System.out.println();
        System.out.println("Gravando os alunos");
        tDaoAluno.create(tAluno1);
        tDaoAluno.create(tAluno2);
        
        Emprestimo tEmprestimo1 = new Emprestimo(0, tExemplar1, tAluno1, LocalDate.now(), null, StatusEmprestimo.EMPRESTADO, null);
        Emprestimo tEmprestimo2 = new Emprestimo();
        tEmprestimo2.setExemplar(tExemplar2);
        tEmprestimo2.setAluno(tAluno2);
        tEmprestimo2.setDataLocacao(LocalDate.now().minusDays(1));
        tEmprestimo2.setStatus(StatusEmprestimo.EMPRESTADO);
        
        System.out.println();
        System.out.println("Gravando os empréstimos");
        tEmprestimo1 = tDaoEmprestimo.create(tEmprestimo1);
        if (tEmprestimo1 != null)
            System.out.println("Empréstimo 1 gravado com sucesso");
        else
            System.out.println("Problemas na gravação do empréstimo 1");

        tEmprestimo2 = tDaoEmprestimo.create(tEmprestimo2);
        if (tEmprestimo2 != null)
            System.out.println("Empréstimo 2 gravado com sucesso");
        else
            System.out.println("Problemas na gravação do empréstimo 2");

        System.out.println();
        System.out.println("Lendo os Empréstimos");
        tEmprestimo1 = tDaoEmprestimo.recovery(tEmprestimo1.getId());
        if (tEmprestimo1 != null)
            System.out.println("Empréstimo 1 : " + tEmprestimo1);
        else
            System.out.println("Problemas na leitura do empréstimo 1");
        tEmprestimo2 = tDaoEmprestimo.recovery(tEmprestimo2.getId());
        if (tEmprestimo2 != null)
            System.out.println("Empréstimo 2 : " + tEmprestimo2);
        else
            System.out.println("Problemas na leitura do empréstimo 1");

        tEmprestimo1.setDataDevolucao(LocalDate.now().plusDays(3));
        tEmprestimo1.setStatus(StatusEmprestimo.DEVOLVIDO);
        tEmprestimo1.setValorMulta(new BigDecimal("12.45"));
        
        tEmprestimo2.setDataDevolucao(LocalDate.now().plusMonths(4));
        tEmprestimo2.setStatus(StatusEmprestimo.CANCELADO);

        System.out.println();
        System.out.println("Atualizando os empréstimos");
        if (tDaoEmprestimo.update(tEmprestimo1) != null)
            System.out.println("Empréstimo 1 atualizado com sucesso");
        else
            System.out.println("Problemas na atualização do empréstimo 1");

        if (tDaoEmprestimo.update(tEmprestimo2) != null)
            System.out.println("Empréstimo 2 atualizado com sucesso");
        else
            System.out.println("Problemas na atualização do empréstimo 2");
        
        List<Emprestimo> tLista1 = tDaoEmprestimo.search();

        System.out.println();
        System.out.println("Lista geral de empréstimos cadastrados");
        int i = 1;
        for (Emprestimo tEmprestimo : tLista1)
        {
            System.out.println("Empréstimo " + i++ + " : " + tEmprestimo);
        }
        
        List<Emprestimo> tLista2 = tDaoEmprestimo.searchByAluno(tAluno1);

        System.out.println();
        System.out.println("Lista geral de empréstimos do aluno 1");
        i = 1;
        for (Emprestimo tEmprestimo : tLista2)
        {
            System.out.println("Empréstimo " + i++ + " : " + tEmprestimo);
        }
        
        List<Emprestimo> tLista3 = tDaoEmprestimo.searchByStatus(StatusEmprestimo.CANCELADO);

        System.out.println();
        System.out.println("Lista geral de empréstimos cancelados");
        i = 1;
        for (Emprestimo tEmprestimo : tLista3)
        {
            System.out.println("Empréstimo " + i++ + " : " + tEmprestimo);
        }
        
        List<Emprestimo> tLista4 = tDaoEmprestimo.searchByDataEmprestimo(LocalDate.now());

        System.out.println();
        System.out.println("Lista geral de empréstimos de hoje");
        i = 1;
        for (Emprestimo tEmprestimo : tLista4)
        {
            System.out.println("Empréstimo " + i++ + " : " + tEmprestimo);
        }
        
        List<Emprestimo> tLista5 = tDaoEmprestimo.searchByDataDevolucao(LocalDate.now().plusMonths(4));

        System.out.println();
        System.out.println("Lista geral de empréstimos daqui a 4 meses");
        i = 1;
        for (Emprestimo tEmprestimo : tLista5)
        {
            System.out.println("Empréstimo " + i++ + " : " + tEmprestimo);
        }
        
        System.out.println();
        System.out.println("Removendo os empréstimos");
        if (tDaoEmprestimo.delete(tEmprestimo1.getId()))
            System.out.println("Empréstimo 1 removido com sucesso");
        else
            System.out.println("Problemas na remoção do empréstimo 1");

        if (tDaoEmprestimo.delete(tEmprestimo2.getId()))
            System.out.println("Empréstimo 2 removido com sucesso");
        else
            System.out.println("Problemas na remoção do empréstimo 2");
        
        // Removendo os Exemplares
        System.out.println();
        System.out.println("Removendo os exemplares");
        tDaoExemplar.delete(tExemplar1.getId());
        tDaoExemplar.delete(tExemplar2.getId());
        
        // Removendo os Livros
        System.out.println();
        System.out.println("Removendo os livros");
        tDaoLivro.delete(tLivro1.getIsbn());
        tDaoLivro.delete(tLivro2.getIsbn());

        // Removendo os Alunos
        System.out.println();
        System.out.println("Removendo os aluno");
        tDaoAluno.delete(tAluno1.getMatricula());
        tDaoAluno.delete(tAluno2.getMatricula());
    }
}
