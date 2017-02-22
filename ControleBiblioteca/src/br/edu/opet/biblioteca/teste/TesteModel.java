package br.edu.opet.biblioteca.teste;

import br.edu.opet.biblioteca.enums.SituacaoAluno;
import br.edu.opet.biblioteca.model.Aluno;
import br.edu.opet.biblioteca.model.Exemplar;
import br.edu.opet.biblioteca.model.Livro;

public class TesteModel
{
    public static void main(String[] args)
    {
        Aluno tAluno1 = new Aluno();
        tAluno1.setMatricula(22377772);
        tAluno1.setNome("Jostracracia Olivento");
        tAluno1.setCurso("Biologia");
        tAluno1.setTelefone(4132232323L);
        tAluno1.setEmail("jostra@hotmail.com");
        tAluno1.setSituacao(SituacaoAluno.MATRICULADO);

        Aluno tAluno2 = new Aluno(22323882, "Sondravio Gulildo", "Matemática", 4321322323L, "songu@gmail.com", SituacaoAluno.FORMADO);
        
        System.out.println();
        System.out.println("Alunos");
        System.out.println("Aluno 1 : " + tAluno1);
        System.out.println("Aluno 2 : " + tAluno2);

        Livro tLivro1 = new Livro(9_788_539_605_378L, "Cozinha Prática", "Rita Lobo", "Senac SP", 2016, 1, 61.40);
        Livro tLivro2 = new Livro();
        tLivro2.setIsbn(9_788_567_431_024L);
        tLivro2.setTitulo("Pitadas da Rita");
        tLivro2.setAutor("Rita Lobo");
        tLivro2.setEditora("Panelinha");
        tLivro2.setAnoEdicao(2014);
        tLivro2.setEdicao(1);
        tLivro2.setValorCompra(58.16);
        
        System.out.println();
        System.out.println("Livros");
        System.out.println("Livro 1 : " + tLivro1);
        System.out.println("Livro 2 : " + tLivro2);
        
        Exemplar tExemplar1 = new Exemplar(11111, tLivro1, false);

        Exemplar tExemplar2 = new Exemplar();
        tExemplar2.setId(22222);
        tExemplar2.setLivro(tLivro2);
        tExemplar2.setExemplarLocal(true);
        
        System.out.println();
        System.out.println("Exemplares");
        System.out.println("Exemplar 1 : " + tExemplar1);
        System.out.println("Exemplar 2 : " + tExemplar2);
        
    }
}
