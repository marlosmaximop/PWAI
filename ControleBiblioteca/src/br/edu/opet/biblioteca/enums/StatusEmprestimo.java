package br.edu.opet.biblioteca.enums;

public enum StatusEmprestimo
{
    EMPRESTADO('E'), DEVOLVIDO('D'), CANCELADO('C');

    private char codigo;

    private StatusEmprestimo(char pCodigo)
    {
        codigo = pCodigo;
    }

    public char getCodigo()
    {
        return codigo;
    }

    public static StatusEmprestimo fromChar(char pCodigo)
    {
        switch (pCodigo)
        {
            case 'E':
                return EMPRESTADO;
            case 'D':
                return DEVOLVIDO;
            case 'C':
                return CANCELADO;
            default:
                return null;
        }
    }
}
