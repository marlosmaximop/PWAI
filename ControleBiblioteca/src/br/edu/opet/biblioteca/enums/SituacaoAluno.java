package br.edu.opet.biblioteca.enums;

public enum SituacaoAluno
{
    MATRICULADO('M'), FORMADO('F'), TRANCADO('T'), CANCELADO('C');

    private char codigo;

    private SituacaoAluno(char pCodigo)
    {
        codigo = pCodigo;
    }

    public char getCodigo()
    {
        return codigo;
    }

    public static SituacaoAluno fromChar(char pCodigo)
    {
        switch (pCodigo)
        {
            case 'M':
                return MATRICULADO;
            case 'F':
                return FORMADO;
            case 'T':
                return TRANCADO;
            case 'C':
                return CANCELADO;
            default:
                return null;
        }
    }
}
