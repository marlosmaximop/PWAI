package br.edu.opet.biblioteca.teste;

import java.util.List;

import br.edu.opet.biblioteca.dao.AlunoDAO;
import br.edu.opet.biblioteca.dao.DaoFactory;
import br.edu.opet.biblioteca.enums.SituacaoAluno;
import br.edu.opet.biblioteca.model.Aluno;

public class TesteAlunoDao
{
    public static void main(String[] args)
    {
        // Obtendo o objeto de persistência
        AlunoDAO tDao = DaoFactory.getAlunoDAO();

        // Criando os alunos para inclusão
        Aluno tAluno1 = new Aluno();
        tAluno1.setMatricula(22377772);
        tAluno1.setNome("Jostracracia Olivento");
        tAluno1.setCurso("Biologia");
        tAluno1.setTelefone(4132232323L);
        tAluno1.setEmail("jostra@hotmail.com");
        tAluno1.setSituacao(SituacaoAluno.MATRICULADO);

        Aluno tAluno2 = new Aluno(22323882, "Sondravio Gulildo", "Matemática", 4321322323L, "songu@gmail.com", SituacaoAluno.FORMADO);

        System.out.println();
        System.out.println("Gravando os alunos");
        if (tDao.create(tAluno1) != null)
            System.out.println("Aluno 1 gravado com sucesso");
        else
            System.out.println("Problemas na gravação do aluno 1");

        if (tDao.create(tAluno2) != null)
            System.out.println("Aluno 2 gravado com sucesso");
        else
            System.out.println("Problemas na gravação do aluno 2");

        System.out.println();
        System.out.println("Lendo os alunos");
        tAluno1 = tDao.recovery(tAluno1.getMatricula());
        if (tAluno1 != null)
            System.out.println("Aluno 1 : " + tAluno1);
        else
            System.out.println("Problemas na leitura do aluno 1");
        tAluno2 = tDao.recovery(tAluno2.getMatricula());
        if (tAluno2 != null)
            System.out.println("Aluno 2 : " + tAluno2);
        else
            System.out.println("Problemas na leitura do aluno 1");

        tAluno1.setCurso("Biologia Marinha");
        tAluno1.setTelefone(41985003677L);
        tAluno1.setEmail("olivento_jostra@gmail.com");

        tAluno2.setCurso("Matemática Aplicada");
        tAluno2.setTelefone(41998949632L);
        tAluno2.setEmail("gulido@msn.com");

        System.out.println();
        System.out.println("Atualizando os alunos");
        if (tDao.update(tAluno1) != null)
            System.out.println("Aluno 1 atualizado com sucesso");
        else
            System.out.println("Problemas na atualização do aluno 1");

        if (tDao.update(tAluno2) != null)
            System.out.println("Aluno 2 atualizado com sucesso");
        else
            System.out.println("Problemas na atualização do aluno 2");
        
        List<Aluno> tLista1 = tDao.search();

        System.out.println();
        System.out.println("Lista geral de alunos cadastrados");
        int i = 1;
        for (Aluno tAluno : tLista1)
        {
            System.out.println("Aluno " + i++ + " : " + tAluno);
        }
        
        List<Aluno> tLista2 = tDao.searchByNome("Jos");

        System.out.println();
        System.out.println("Pesquisa por nome");
        i = 1;
        for (Aluno tAluno : tLista2)
        {
            System.out.println("Aluno " + i++ + " : " + tAluno);
        }
        
        
        List<Aluno> tLista3 = tDao.searchByCurso("mat");

        System.out.println();
        System.out.println("Pesquisa por curso");
        i = 1;
        for (Aluno tAluno : tLista3)
        {
            System.out.println("Aluno " + i++ + " : " + tAluno);
        }
        
        
        List<Aluno> tLista4 = tDao.searchByEmail("GMAIL");

        System.out.println();
        System.out.println("Pesquisa por email");
        i = 1;
        for (Aluno tAluno : tLista4)
        {
            System.out.println("Aluno " + i++ + " : " + tAluno);
        }
        

        System.out.println();
        System.out.println("Removendo os alunos");
        if (tDao.delete(tAluno1.getMatricula()))
            System.out.println("Aluno 1 removido com sucesso");
        else
            System.out.println("Problemas na remoção do aluno 1");

        if (tDao.delete(tAluno2.getMatricula()))
            System.out.println("Aluno 2 removido com sucesso");
        else
            System.out.println("Problemas na remoção do aluno 2");
    }
}
