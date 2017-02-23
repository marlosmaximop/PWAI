package br.edu.opet.biblioteca.dto;

import java.util.List;

import br.edu.opet.biblioteca.model.Livro;

public class LivroDTO extends AbstractDTO<Livro>
{
    public LivroDTO(boolean pOk, String pMensagem)
    {
        super(pOk, pMensagem);
    }

    public LivroDTO(boolean pOk, String pMensagem, Livro pObjeto)
    {
        super(pOk, pMensagem, pObjeto);
    }

    public LivroDTO(boolean pOk, String pMensagem, List<Livro> pLista)
    {
        super(pOk, pMensagem, pLista);
    }

    public Livro getLivro()
    {
        return getObjeto();
    }
}
