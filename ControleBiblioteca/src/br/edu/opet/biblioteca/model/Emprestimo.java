package br.edu.opet.biblioteca.model;

import java.time.LocalDate;
import java.util.Comparator;

import br.edu.opet.biblioteca.enums.StatusEmprestimo;

public class Emprestimo implements Comparable<Emprestimo>
{
    private int              id;
    private Livro            livro;
    private Aluno            aluno;
    private LocalDate        dataLocacao;
    private LocalDate        dataDevolucao;
    private StatusEmprestimo status;
    private double           valorMulta;

    public Emprestimo()
    {
        super();
        // TODO Auto-generated constructor stub
    }

    public Emprestimo(int pId, Livro pLivro, Aluno pAluno, LocalDate pDataLocacao, LocalDate pDataDevolucao, StatusEmprestimo pStatus,
                    double pValorMulta)
    {
        super();
        id = pId;
        livro = pLivro;
        aluno = pAluno;
        dataLocacao = pDataLocacao;
        dataDevolucao = pDataDevolucao;
        status = pStatus;
        valorMulta = pValorMulta;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int pId)
    {
        id = pId;
    }

    public Livro getLivro()
    {
        return livro;
    }

    public void setLivro(Livro pLivro)
    {
        livro = pLivro;
    }

    public Aluno getAluno()
    {
        return aluno;
    }

    public void setAluno(Aluno pAluno)
    {
        aluno = pAluno;
    }

    public LocalDate getDataLocacao()
    {
        return dataLocacao;
    }

    public void setDataLocacao(LocalDate pDataLocacao)
    {
        dataLocacao = pDataLocacao;
    }

    public LocalDate getDataDevolucao()
    {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate pDataDevolucao)
    {
        dataDevolucao = pDataDevolucao;
    }

    public StatusEmprestimo getStatus()
    {
        return status;
    }

    public void setStatus(StatusEmprestimo pStatus)
    {
        status = pStatus;
    }

    public double getValorMulta()
    {
        return valorMulta;
    }

    public void setValorMulta(double pValorMulta)
    {
        valorMulta = pValorMulta;
    }

    @Override
    public String toString()
    {
        StringBuilder tBuilder = new StringBuilder();
        tBuilder.append("Emprestimo [");
        tBuilder.append(getId());
        tBuilder.append(", ");
        tBuilder.append(getLivro());
        tBuilder.append(", ");
        tBuilder.append(getAluno());
        tBuilder.append(", ");
        tBuilder.append(getDataLocacao());
        tBuilder.append(", ");
        tBuilder.append(getDataDevolucao());
        tBuilder.append(", ");
        tBuilder.append(getStatus());
        tBuilder.append(", ");
        tBuilder.append(getValorMulta());
        tBuilder.append("]");
        return tBuilder.toString();
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
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
        Emprestimo other = (Emprestimo) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public int compareTo(Emprestimo pObj)
    {
        return getId() - pObj.getId();
    }

    public static class DataDevolucaoComparator implements Comparator<Emprestimo>
    {
        @Override
        public int compare(Emprestimo pObj1, Emprestimo pObj2)
        {
            return pObj1.getDataDevolucao().compareTo(pObj2.getDataDevolucao());
        }
    }
}
