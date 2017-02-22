package br.edu.opet.biblioteca.model;

import java.math.BigDecimal;
import java.util.Comparator;

public class Livro implements Comparable<Livro>
{
    private long    isbn;
    private String  titulo;
    private String  autor;
    private String  editora;
    private int     anoEdicao;
    private int     edicao;
    private BigDecimal  valorCompra;

    public Livro()
    {
        super();
    }

    public Livro(long pIsbn, String pTitulo, String pAutor, String pEditora, int pAnoEdicao, int pEdicao, BigDecimal pValorCompra)
    {
        super();
        isbn = pIsbn;
        titulo = pTitulo;
        autor = pAutor;
        editora = pEditora;
        anoEdicao = pAnoEdicao;
        edicao = pEdicao;
        valorCompra = pValorCompra;
    }

    public long getIsbn()
    {
        return isbn;
    }

    public void setIsbn(long pIsbn)
    {
        isbn = pIsbn;
    }

    public String getTitulo()
    {
        return titulo;
    }

    public void setTitulo(String pTitulo)
    {
        titulo = pTitulo;
    }

    public String getAutor()
    {
        return autor;
    }

    public void setAutor(String pAutor)
    {
        autor = pAutor;
    }

    public String getEditora()
    {
        return editora;
    }

    public void setEditora(String pEditora)
    {
        editora = pEditora;
    }

    public int getAnoEdicao()
    {
        return anoEdicao;
    }

    public void setAnoEdicao(int pAnoEdicao)
    {
        anoEdicao = pAnoEdicao;
    }

    public int getEdicao()
    {
        return edicao;
    }

    public void setEdicao(int pEdicao)
    {
        edicao = pEdicao;
    }

    public BigDecimal getValorCompra()
    {
        return valorCompra;
    }

    public void setValorCompra(BigDecimal pValorCompra)
    {
        valorCompra = pValorCompra;
    }

    @Override
    public String toString()
    {
        StringBuilder tBuilder = new StringBuilder();
        tBuilder.append("Livro [");
        tBuilder.append(getIsbn());
        tBuilder.append(", ");
        tBuilder.append(getTitulo());
        tBuilder.append(", ");
        tBuilder.append(getAutor());
        tBuilder.append(", ");
        tBuilder.append(getEditora());
        tBuilder.append(", ");
        tBuilder.append(getAnoEdicao());
        tBuilder.append(", ");
        tBuilder.append(getEdicao());
        tBuilder.append(", ");
        tBuilder.append(getValorCompra());
        tBuilder.append(", ");
        tBuilder.append(hashCode());
        tBuilder.append("]");
        return tBuilder.toString();
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (isbn ^ (isbn >>> 32));
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
        Livro other = (Livro) obj;
        if (isbn != other.isbn)
            return false;
        return true;
    }

    @Override
    public int compareTo(Livro pObj)
    {
        return Long.compare(getIsbn(), pObj.getIsbn());
    }

    public static class TituloComparator implements Comparator<Livro>
    {
        @Override
        public int compare(Livro pObj1, Livro pObj2)
        {
            return pObj1.getTitulo().compareTo(pObj2.getTitulo());
        }
    }

    public static class AutorComparator implements Comparator<Livro>
    {
        @Override
        public int compare(Livro pObj1, Livro pObj2)
        {
            return pObj1.getAutor().compareTo(pObj2.getAutor());
        }
    }

}
