package br.edu.opet.biblioteca.dto;

import java.util.List;

import br.edu.opet.biblioteca.model.Aluno;

public class AlunoDTO extends AbstractDTO<Aluno>
{
    public AlunoDTO(boolean pOk, String pMensagem)
    {
        super(pOk, pMensagem);
    }

    public AlunoDTO(boolean pOk, String pMensagem, Aluno pObjeto)
    {
        super(pOk, pMensagem, pObjeto);
    }

    public AlunoDTO(boolean pOk, String pMensagem, List<Aluno> pLista)
    {
        super(pOk, pMensagem, pLista);
    }

    public Aluno getAluno()
    {
        return getObjeto();
    }
}
