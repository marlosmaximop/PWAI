package br.edu.opet.biblioteca.model;

import java.util.Comparator;

import br.edu.opet.biblioteca.enums.SituacaoAluno;

public class Aluno implements Comparable<Aluno>
{
    private long          matricula;
    private String        nome;
    private String        curso;
    private long          telefone;
    private String        email;
    private SituacaoAluno situacao;

    public Aluno()
    {
        super();
    }

    public Aluno(long pMatricula, String pNome, String pCurso, long pTelefone, String pEmail, SituacaoAluno pSituacao)
    {
        super();
        matricula = pMatricula;
        nome = pNome;
        curso = pCurso;
        telefone = pTelefone;
        email = pEmail;
        situacao = pSituacao;
    }

    public long getMatricula()
    {
        return matricula;
    }

    public void setMatricula(long pMatricula)
    {
        matricula = pMatricula;
    }

    public String getNome()
    {
        return nome;
    }

    public void setNome(String pNome)
    {
        nome = pNome;
    }

    public String getCurso()
    {
        return curso;
    }

    public void setCurso(String pCurso)
    {
        curso = pCurso;
    }

    public long getTelefone()
    {
        return telefone;
    }

    public void setTelefone(long pTelefone)
    {
        telefone = pTelefone;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String pEmail)
    {
        email = pEmail;
    }

    public SituacaoAluno getSituacao()
    {
        return situacao;
    }

    public void setSituacao(SituacaoAluno pSituacao)
    {
        situacao = pSituacao;
    }

    @Override
    public String toString()
    {
        StringBuilder tBuilder = new StringBuilder();
        tBuilder.append("Aluno [");
        tBuilder.append(getMatricula());
        tBuilder.append(", ");
        tBuilder.append(getNome());
        tBuilder.append(", ");
        tBuilder.append(getCurso());
        tBuilder.append(", ");
        tBuilder.append(getTelefone());
        tBuilder.append(", ");
        tBuilder.append(getEmail());
        tBuilder.append(", ");
        tBuilder.append(getSituacao());
        tBuilder.append("]");
        return tBuilder.toString();
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (matricula ^ (matricula >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Aluno other = (Aluno) obj;
        if (matricula != other.matricula)
            return false;
        return true;
    }

    @Override
    public int compareTo(Aluno pObj)
    {
        return Long.compare(getMatricula(), pObj.getMatricula());
    }

    public static class NomeComparator implements Comparator<Aluno>
    {
        @Override
        public int compare(Aluno pObj1, Aluno pObj2)
        {
            return pObj1.getNome().compareTo(pObj2.getNome());
        }
    }
}
