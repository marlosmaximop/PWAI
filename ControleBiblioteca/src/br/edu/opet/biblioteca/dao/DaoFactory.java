package br.edu.opet.biblioteca.dao;

import br.edu.opet.biblioteca.dao.jdbc.AlunoJdbcDAO;
import br.edu.opet.biblioteca.dao.jdbc.EmprestimoJdbcDAO;
import br.edu.opet.biblioteca.dao.jdbc.ExemplarJdbcDAO;
import br.edu.opet.biblioteca.dao.jdbc.LivroJdbcDAO;

public class DaoFactory
{
    public static AlunoDAO getAlunoDAO()
    {
        return new AlunoJdbcDAO();
    }

    public static LivroDAO getLivroDAO()
    {
        return new LivroJdbcDAO();
    }

    public static ExemplarDAO getExemplarDAO()
    {
        return new ExemplarJdbcDAO();
    }

    public static EmprestimoDAO getEmprestimoDAO()
    {
        return new EmprestimoJdbcDAO();
    }
}
