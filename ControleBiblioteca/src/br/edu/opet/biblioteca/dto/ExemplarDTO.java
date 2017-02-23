package br.edu.opet.biblioteca.dto;

import java.util.List;

import br.edu.opet.biblioteca.model.Livro;

public class ExemplarDTO extends AbstractDTO<Livro>
{
    public ExemplarDTO(boolean pOk, String pMensagem)
    {
        super(pOk, pMensagem);
    }

    public ExemplarDTO(boolean pOk, String pMensagem, Livro pObjeto)
    {
        super(pOk, pMensagem, pObjeto);
    }

    public ExemplarDTO(boolean pOk, String pMensagem, List<Livro> pLista)
    {
        super(pOk, pMensagem, pLista);
    }

    public Livro getAluno()
    {
        return getObjeto();
    }
}
