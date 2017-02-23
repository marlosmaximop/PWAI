package br.edu.opet.biblioteca.dto;

import java.util.List;

import br.edu.opet.biblioteca.model.Emprestimo;

public class EmprestimoDTO extends AbstractDTO<Emprestimo>
{
    public EmprestimoDTO(boolean pOk, String pMensagem)
    {
        super(pOk, pMensagem);
    }

    public EmprestimoDTO(boolean pOk, String pMensagem, Emprestimo pObjeto)
    {
        super(pOk, pMensagem, pObjeto);
    }

    public EmprestimoDTO(boolean pOk, String pMensagem, List<Emprestimo> pLista)
    {
        super(pOk, pMensagem, pLista);
    }

    public Emprestimo getEmprestimo()
    {
        return getObjeto();
    }
}
