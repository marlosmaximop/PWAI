package br.edu.opet.biblioteca.model;

public class Exemplar implements Comparable<Exemplar>
{
    private int     id;
    private Livro   livro;
    private boolean exemplarLocal;

    public Exemplar()
    {
        super();
    }

    public Exemplar(int pId, Livro pLivro, boolean pExemplarLocal)
    {
        super();
        id = pId;
        livro = pLivro;
        exemplarLocal = pExemplarLocal;
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

    public boolean isExemplarLocal()
    {
        return exemplarLocal;
    }

    public void setExemplarLocal(boolean pExemplarLocal)
    {
        exemplarLocal = pExemplarLocal;
    }

    @Override
    public String toString()
    {
        StringBuilder tBuilder = new StringBuilder();
        tBuilder.append("Exemplar [");
        tBuilder.append(getId());
        tBuilder.append(", ");
        tBuilder.append(getLivro());
        tBuilder.append(", ");
        tBuilder.append(isExemplarLocal());
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
        Exemplar other = (Exemplar) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public int compareTo(Exemplar pObj)
    {
        return getId() - pObj.getId();
    }


}
