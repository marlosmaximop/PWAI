package br.edu.opet.biblioteca.dao.jdbc;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.edu.opet.biblioteca.dao.EmprestimoDAO;
import br.edu.opet.biblioteca.enums.SituacaoAluno;
import br.edu.opet.biblioteca.enums.StatusEmprestimo;
import br.edu.opet.biblioteca.jdbc.Conexao;
import br.edu.opet.biblioteca.model.Aluno;
import br.edu.opet.biblioteca.model.Emprestimo;
import br.edu.opet.biblioteca.model.Exemplar;
import br.edu.opet.biblioteca.model.Livro;
import br.edu.opet.biblioteca.util.ExceptionUtil;

public class EmprestimoJdbcDAO implements EmprestimoDAO
{
    private Connection conexao = Conexao.getConexao();            // Conexão com o banco de dados
    private String     campos  = "ID, ID_EXEMPLAR, MATRICULA_ALUNO, DATA_LOCACAO, DATA_DEVOLUCAO, STATUS, VALOR_MULTA";
    
    @Override
    public Emprestimo create(Emprestimo pEmprestimo)
    {
        // Definindo o objeto emprestimo de retorno
        Emprestimo tEmprestimo = null;

        try
        {
            // Criando o comando SQL e o comando JDBC
            String tComandoSQL =
                "INSERT INTO EMPRESTIMO (" + campos + ") VALUES (EMPRESTIMO_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?)";
            PreparedStatement tComandoJDBC = conexao.prepareStatement(tComandoSQL, new String [] {"ID"});

            // Colocando os parâmetros recebidos no comando JDBC
            int i = 1;

            tComandoJDBC.setLong(i++, pEmprestimo.getExemplar().getId());
            tComandoJDBC.setLong(i++, pEmprestimo.getAluno().getMatricula());
            tComandoJDBC.setDate(i++, Date.valueOf(pEmprestimo.getDataLocacao()));
            if (pEmprestimo.getDataDevolucao() == null)
                tComandoJDBC.setNull(i++, Types.DATE);
            else
                tComandoJDBC.setDate(i++, Date.valueOf(pEmprestimo.getDataDevolucao()));
            tComandoJDBC.setString(i++, String.valueOf(pEmprestimo.getStatus().getCodigo()));
            if (pEmprestimo.getValorMulta() == null)
                tComandoJDBC.setNull(i++, Types.DECIMAL);
            else
                tComandoJDBC.setBigDecimal(i++, pEmprestimo.getValorMulta());

            // Executando o comando de gravação
            int tQtdeReg = tComandoJDBC.executeUpdate();

            // Verificando se um registro foi incluído
            if (tQtdeReg == 1)
            {
                // Copiando o emprestimo para o retorno
                tEmprestimo = pEmprestimo;

                // Recuperando o código gerado pelo Oracle
                ResultSet tRsChave = tComandoJDBC.getGeneratedKeys();
                tRsChave.next();
                tEmprestimo.setId(tRsChave.getInt(1));
            }

            // Liberando os recursos JDBC
            tComandoJDBC.close();
        }
        catch (SQLException tExcept)
        {
            ExceptionUtil.mostrarErro(tExcept, "Erro no método de criação do emprestimo");
        }

        // Retornando o objeto Emprestimo
        return tEmprestimo;
    }

    @Override
    public Emprestimo recovery(int pId)
    {
        // Definindo o objeto emprestimo de retorno
        Emprestimo tEmprestimo = null;

        try
        {
            // Criando o comando SQL e o comando JDBC
            String tComandoSQL = "SELECT EMPRESTIMO.ID AS ID_EMPRESTIMO, EMPRESTIMO.DATA_LOCACAO, EMPRESTIMO.DATA_DEVOLUCAO, EMPRESTIMO.STATUS, EMPRESTIMO.VALOR_MULTA, "
                            + "LIVRO.ISBN, LIVRO.TITULO, LIVRO.AUTOR, LIVRO.EDITORA, LIVRO.ANO_EDICAO, LIVRO.EDICAO, LIVRO.VALOR_COMPRA, " 
                            + "ALUNO.MATRICULA, ALUNO.NOME, ALUNO.CURSO, ALUNO.TELEFONE, ALUNO.EMAIL, ALUNO.SITUACAO, "
                            + "EXEMPLAR.ID AS ID_EXEMPLAR, EXEMPLAR.EXEMPLAR_LOCAL "
                            + "FROM EMPRESTIMO, EXEMPLAR, LIVRO, ALUNO "
                            + "WHERE EMPRESTIMO.ID = ? AND EMPRESTIMO.ID_EXEMPLAR = EXEMPLAR.ID AND EXEMPLAR.ISBN_LIVRO = LIVRO.ISBN AND EMPRESTIMO.MATRICULA_ALUNO = ALUNO.MATRICULA";
            PreparedStatement tComandoJDBC = conexao.prepareStatement(tComandoSQL);

            // Colocando o parâmetro recebido no comando JDBC
            tComandoJDBC.setLong(1, pId);

            // Executando o comando e salvando o ResultSet para processar
            ResultSet tResultSet = tComandoJDBC.executeQuery();

            // Verificando se um registro foi lido
            if (tResultSet.next())
            {
                // Salvando o Emprestimo para retornar depois
                tEmprestimo = carregarEmprestimo(tResultSet);
            }

            // Liberando os recursos JDBC
            tResultSet.close();
            tComandoJDBC.close();
        }
        catch (SQLException tExcept)
        {
            ExceptionUtil.mostrarErro(tExcept, "Erro no método de recuperação do emprestimo");
        }

        // Retornando o objeto Emprestimo
        return tEmprestimo;
    }

    @Override
    public Emprestimo update(Emprestimo pEmprestimo)
    {
        // Definindo o objeto emprestimo de retorno
        Emprestimo tEmprestimo = null;

        try
        {
            // Criando o comando SQL e o comando JDBC
            String tComandoSQL =
                            "UPDATE EMPRESTIMO SET "
                            + "ID_EXEMPLAR = ?, "
                            + "MATRICULA_ALUNO = ?, "
                            + "DATA_LOCACAO = ?, "
                            + "DATA_DEVOLUCAO = ?, "
                            + "STATUS = ?, "
                            + "VALOR_MULTA = ? "
                            + "WHERE ID = ?";
            PreparedStatement tComandoJDBC = conexao.prepareStatement(tComandoSQL);

            // Colocando os parâmetros recebidos no comando JDBC
            int i = 1;

            tComandoJDBC.setLong(i++, pEmprestimo.getExemplar().getId());
            tComandoJDBC.setLong(i++, pEmprestimo.getAluno().getMatricula());
            tComandoJDBC.setDate(i++, Date.valueOf(pEmprestimo.getDataLocacao()));
            if (pEmprestimo.getDataDevolucao() == null)
                tComandoJDBC.setNull(i++, Types.DATE);
            else
                tComandoJDBC.setDate(i++, Date.valueOf(pEmprestimo.getDataDevolucao()));
            tComandoJDBC.setString(i++, String.valueOf(pEmprestimo.getStatus().getCodigo()));
            if (pEmprestimo.getValorMulta() == null)
                tComandoJDBC.setNull(i++, Types.DECIMAL);
            else
                tComandoJDBC.setBigDecimal(i++, pEmprestimo.getValorMulta());
            tComandoJDBC.setLong(i++, pEmprestimo.getExemplar().getId());

            // Executando o comando de regravação
            int tQtdeReg = tComandoJDBC.executeUpdate();

            // Verificando se um registro foi alterado
            if (tQtdeReg == 1)
            {
                // Copiando o emprestimo para o retorno
                tEmprestimo = pEmprestimo;
            }

            // Liberando os recursos JDBC
            tComandoJDBC.close();
        }
        catch (SQLException tExcept)
        {
            ExceptionUtil.mostrarErro(tExcept, "Erro no método de atualização do emprestimo");
        }

        // Retornando o objeto Emprestimo
        return tEmprestimo;
    }

    @Override
    public boolean delete(int pId)
    {
        try
        {
            // Criando o comando SQL e o comando JDBC
            String tComandoSQL = "DELETE EMPRESTIMO " +
                            " WHERE ID = ?";
            PreparedStatement tComandoJDBC = conexao.prepareStatement(tComandoSQL);

            // Colocando o parâmetro recebido no comando JDBC
            tComandoJDBC.setLong(1, pId);

            // Executando o comando de remoção
            int tQtdeReg = tComandoJDBC.executeUpdate();

            // Liberando os recursos JDBC
            tComandoJDBC.close();

            // Se excluiu um registro, a remoção foi efetuada com sucesso
            return tQtdeReg == 1;
        }
        catch (SQLException tExcept)
        {
            ExceptionUtil.mostrarErro(tExcept, "Erro no método de remoção do emprestimo");
        }

        // Retornando indicativo de falha de remoção
        return false;
    }

    @Override
    public List<Emprestimo> search()
    {
        // Criando a tLista de empréstimos vazia
        List<Emprestimo> tLista = new ArrayList<>();

        try
        {
            // Criando o comando SQL e o comando JDBC
            String tComandoSQL = "SELECT EMPRESTIMO.ID AS ID_EMPRESTIMO, EMPRESTIMO.DATA_LOCACAO, EMPRESTIMO.DATA_DEVOLUCAO, EMPRESTIMO.STATUS, EMPRESTIMO.VALOR_MULTA, "
                            + "LIVRO.ISBN, LIVRO.TITULO, LIVRO.AUTOR, LIVRO.EDITORA, LIVRO.ANO_EDICAO, LIVRO.EDICAO, LIVRO.VALOR_COMPRA, " 
                            + "ALUNO.MATRICULA, ALUNO.NOME, ALUNO.CURSO, ALUNO.TELEFONE, ALUNO.EMAIL, ALUNO.SITUACAO, "
                            + "EXEMPLAR.ID AS ID_EXEMPLAR, EXEMPLAR.EXEMPLAR_LOCAL "
                            + "FROM EMPRESTIMO, EXEMPLAR, LIVRO, ALUNO "
                            + "WHERE EMPRESTIMO.ID_EXEMPLAR = EXEMPLAR.ID AND EXEMPLAR.ISBN_LIVRO = LIVRO.ISBN AND EMPRESTIMO.MATRICULA_ALUNO = ALUNO.MATRICULA";
            PreparedStatement tComandoJDBC = conexao.prepareStatement(tComandoSQL);

            // Executando o comando e salvando o ResultSet para processar
            ResultSet tResultSet = tComandoJDBC.executeQuery();

            // Enquanto houver registros, processa
            while (tResultSet.next())
            {
                // Salvando o Emprestimo retornado para adicionar na lista
                Emprestimo tEmprestimo = carregarEmprestimo(tResultSet);

                // Adicionando o emprestimo na tLista
                tLista.add(tEmprestimo);
            }

            // Liberando os recursos JDBC
            tResultSet.close();
            tComandoJDBC.close();
        }
        catch (SQLException tExcept)
        {
            ExceptionUtil.mostrarErro(tExcept, "Erro no método de recuperação da lista de empréstimos");
        }

        // Retornando a lista de empréstimos
        return tLista;
    }

    @Override
    public List<Emprestimo> searchByAluno(Aluno pAluno)
    {
        // Criando a tLista de empréstimos vazia
        List<Emprestimo> tLista = new ArrayList<>();

        try
        {
            // Criando o comando SQL e o comando JDBC
            String tComandoSQL = "SELECT EMPRESTIMO.ID AS ID_EMPRESTIMO, EMPRESTIMO.DATA_LOCACAO, EMPRESTIMO.DATA_DEVOLUCAO, EMPRESTIMO.STATUS, EMPRESTIMO.VALOR_MULTA, "
                            + "LIVRO.ISBN, LIVRO.TITULO, LIVRO.AUTOR, LIVRO.EDITORA, LIVRO.ANO_EDICAO, LIVRO.EDICAO, LIVRO.VALOR_COMPRA, " 
                            + "ALUNO.MATRICULA, ALUNO.NOME, ALUNO.CURSO, ALUNO.TELEFONE, ALUNO.EMAIL, ALUNO.SITUACAO, "
                            + "EXEMPLAR.ID AS ID_EXEMPLAR, EXEMPLAR.EXEMPLAR_LOCAL "
                            + "FROM EMPRESTIMO, EXEMPLAR, LIVRO, ALUNO "
                            + "WHERE EMPRESTIMO.MATRICULA_ALUNO = ? AND EMPRESTIMO.ID_EXEMPLAR = EXEMPLAR.ID AND EXEMPLAR.ISBN_LIVRO = LIVRO.ISBN AND EMPRESTIMO.MATRICULA_ALUNO = ALUNO.MATRICULA";
            PreparedStatement tComandoJDBC = conexao.prepareStatement(tComandoSQL);

            // Colocando o parâmetro recebido no comando JDBC
            tComandoJDBC.setLong(1, pAluno.getMatricula());

            // Executando o comando e salvando o ResultSet para processar
            ResultSet tResultSet = tComandoJDBC.executeQuery();

            // Enquanto houver registros, processa
            while (tResultSet.next())
            {
                // Salvando o Emprestimo retornado para adicionar na lista
                Emprestimo tEmprestimo = carregarEmprestimo(tResultSet);

                // Adicionando o emprestimo na tLista
                tLista.add(tEmprestimo);
            }

            // Liberando os recursos JDBC
            tResultSet.close();
            tComandoJDBC.close();
        }
        catch (SQLException tExcept)
        {
            ExceptionUtil.mostrarErro(tExcept, "Erro no método de recuperação da lista de empréstimos");
        }

        // Retornando a lista de empréstimos
        return tLista;
    }

    @Override
    public List<Emprestimo> searchByStatus(StatusEmprestimo pStatus)
    {
        // Criando a tLista de empréstimos vazia
        List<Emprestimo> tLista = new ArrayList<>();

        try
        {
            // Criando o comando SQL e o comando JDBC
            String tComandoSQL = "SELECT EMPRESTIMO.ID AS ID_EMPRESTIMO, EMPRESTIMO.DATA_LOCACAO, EMPRESTIMO.DATA_DEVOLUCAO, EMPRESTIMO.STATUS, EMPRESTIMO.VALOR_MULTA, "
                            + "LIVRO.ISBN, LIVRO.TITULO, LIVRO.AUTOR, LIVRO.EDITORA, LIVRO.ANO_EDICAO, LIVRO.EDICAO, LIVRO.VALOR_COMPRA, " 
                            + "ALUNO.MATRICULA, ALUNO.NOME, ALUNO.CURSO, ALUNO.TELEFONE, ALUNO.EMAIL, ALUNO.SITUACAO, "
                            + "EXEMPLAR.ID AS ID_EXEMPLAR, EXEMPLAR.EXEMPLAR_LOCAL "
                            + "FROM EMPRESTIMO, EXEMPLAR, LIVRO, ALUNO "
                            + "WHERE EMPRESTIMO.STATUS = ? AND EMPRESTIMO.ID_EXEMPLAR = EXEMPLAR.ID AND EXEMPLAR.ISBN_LIVRO = LIVRO.ISBN AND EMPRESTIMO.MATRICULA_ALUNO = ALUNO.MATRICULA";
            PreparedStatement tComandoJDBC = conexao.prepareStatement(tComandoSQL);

            // Colocando o parâmetro recebido no comando JDBC
            tComandoJDBC.setString(1, String.valueOf(pStatus.getCodigo()));

            // Executando o comando e salvando o ResultSet para processar
            ResultSet tResultSet = tComandoJDBC.executeQuery();

            // Enquanto houver registros, processa
            while (tResultSet.next())
            {
                // Salvando o Emprestimo retornado para adicionar na lista
                Emprestimo tEmprestimo = carregarEmprestimo(tResultSet);

                // Adicionando o emprestimo na tLista
                tLista.add(tEmprestimo);
            }

            // Liberando os recursos JDBC
            tResultSet.close();
            tComandoJDBC.close();
        }
        catch (SQLException tExcept)
        {
            ExceptionUtil.mostrarErro(tExcept, "Erro no método de recuperação da lista de empréstimos");
        }

        // Retornando a lista de empréstimos
        return tLista;
    }

    @Override
    public List<Emprestimo> searchByDataEmprestimo(LocalDate pDataEmprestimo)
    {
        // Criando a tLista de empréstimos vazia
        List<Emprestimo> tLista = new ArrayList<>();

        try
        {
            // Criando o comando SQL e o comando JDBC
            String tComandoSQL = "SELECT EMPRESTIMO.ID AS ID_EMPRESTIMO, EMPRESTIMO.DATA_LOCACAO, EMPRESTIMO.DATA_DEVOLUCAO, EMPRESTIMO.STATUS, EMPRESTIMO.VALOR_MULTA, "
                            + "LIVRO.ISBN, LIVRO.TITULO, LIVRO.AUTOR, LIVRO.EDITORA, LIVRO.ANO_EDICAO, LIVRO.EDICAO, LIVRO.VALOR_COMPRA, " 
                            + "ALUNO.MATRICULA, ALUNO.NOME, ALUNO.CURSO, ALUNO.TELEFONE, ALUNO.EMAIL, ALUNO.SITUACAO, "
                            + "EXEMPLAR.ID AS ID_EXEMPLAR, EXEMPLAR.EXEMPLAR_LOCAL "
                            + "FROM EMPRESTIMO, EXEMPLAR, LIVRO, ALUNO "
                            + "WHERE EMPRESTIMO.DATA_LOCACAO = ? AND EMPRESTIMO.ID_EXEMPLAR = EXEMPLAR.ID AND EXEMPLAR.ISBN_LIVRO = LIVRO.ISBN AND EMPRESTIMO.MATRICULA_ALUNO = ALUNO.MATRICULA";
            PreparedStatement tComandoJDBC = conexao.prepareStatement(tComandoSQL);

            // Colocando o parâmetro recebido no comando JDBC
            tComandoJDBC.setDate(1, Date.valueOf(pDataEmprestimo));

            // Executando o comando e salvando o ResultSet para processar
            ResultSet tResultSet = tComandoJDBC.executeQuery();

            // Enquanto houver registros, processa
            while (tResultSet.next())
            {
                // Salvando o Emprestimo retornado para adicionar na lista
                Emprestimo tEmprestimo = carregarEmprestimo(tResultSet);

                // Adicionando o emprestimo na tLista
                tLista.add(tEmprestimo);
            }

            // Liberando os recursos JDBC
            tResultSet.close();
            tComandoJDBC.close();
        }
        catch (SQLException tExcept)
        {
            ExceptionUtil.mostrarErro(tExcept, "Erro no método de recuperação da lista de empréstimos");
        }

        // Retornando a lista de empréstimos
        return tLista;
    }

    @Override
    public List<Emprestimo> searchByDataDevolucao(LocalDate pDataDevolucao)
    {
        // Criando a tLista de empréstimos vazia
        List<Emprestimo> tLista = new ArrayList<>();

        try
        {
            // Criando o comando SQL e o comando JDBC
            String tComandoSQL = "SELECT EMPRESTIMO.ID AS ID_EMPRESTIMO, EMPRESTIMO.DATA_LOCACAO, EMPRESTIMO.DATA_DEVOLUCAO, EMPRESTIMO.STATUS, EMPRESTIMO.VALOR_MULTA, "
                            + "LIVRO.ISBN, LIVRO.TITULO, LIVRO.AUTOR, LIVRO.EDITORA, LIVRO.ANO_EDICAO, LIVRO.EDICAO, LIVRO.VALOR_COMPRA, " 
                            + "ALUNO.MATRICULA, ALUNO.NOME, ALUNO.CURSO, ALUNO.TELEFONE, ALUNO.EMAIL, ALUNO.SITUACAO, "
                            + "EXEMPLAR.ID AS ID_EXEMPLAR, EXEMPLAR.EXEMPLAR_LOCAL "
                            + "FROM EMPRESTIMO, EXEMPLAR, LIVRO, ALUNO "
                            + "WHERE EMPRESTIMO.DATA_DEVOLUCAO = ? AND EMPRESTIMO.ID_EXEMPLAR = EXEMPLAR.ID AND EXEMPLAR.ISBN_LIVRO = LIVRO.ISBN AND EMPRESTIMO.MATRICULA_ALUNO = ALUNO.MATRICULA";
            PreparedStatement tComandoJDBC = conexao.prepareStatement(tComandoSQL);

            // Colocando o parâmetro recebido no comando JDBC
            tComandoJDBC.setDate(1, Date.valueOf(pDataDevolucao));

            // Executando o comando e salvando o ResultSet para processar
            ResultSet tResultSet = tComandoJDBC.executeQuery();

            
            // Enquanto houver registros, processa
            while (tResultSet.next())
            {
                // Salvando o Emprestimo retornado para adicionar na lista
                Emprestimo tEmprestimo = carregarEmprestimo(tResultSet);

                // Adicionando o emprestimo na tLista
                tLista.add(tEmprestimo);
            }

            // Liberando os recursos JDBC
            tResultSet.close();
            tComandoJDBC.close();
        }
        catch (SQLException tExcept)
        {
            ExceptionUtil.mostrarErro(tExcept, "Erro no método de recuperação da lista de empréstimos");
        }

        // Retornando a lista de empréstimos
        return tLista;
    }

    @Override
    public List<Emprestimo> searchByExemplar(Exemplar pExemplar)
    {
        // Criando a tLista de empréstimos vazia
        List<Emprestimo> tLista = new ArrayList<>();

        try
        {
            // Criando o comando SQL e o comando JDBC
            String tComandoSQL = "SELECT EMPRESTIMO.ID AS ID_EMPRESTIMO, EMPRESTIMO.DATA_LOCACAO, EMPRESTIMO.DATA_DEVOLUCAO, EMPRESTIMO.STATUS, EMPRESTIMO.VALOR_MULTA, "
                            + "LIVRO.ISBN, LIVRO.TITULO, LIVRO.AUTOR, LIVRO.EDITORA, LIVRO.ANO_EDICAO, LIVRO.EDICAO, LIVRO.VALOR_COMPRA, " 
                            + "ALUNO.MATRICULA, ALUNO.NOME, ALUNO.CURSO, ALUNO.TELEFONE, ALUNO.EMAIL, ALUNO.SITUACAO, "
                            + "EXEMPLAR.ID AS ID_EXEMPLAR, EXEMPLAR.EXEMPLAR_LOCAL "
                            + "FROM EMPRESTIMO, EXEMPLAR, LIVRO, ALUNO "
                            + "WHERE EMPRESTIMO.ID_EXEMPLAR = ? AND EMPRESTIMO.ID_EXEMPLAR = EXEMPLAR.ID AND EXEMPLAR.ISBN_LIVRO = LIVRO.ISBN AND EMPRESTIMO.MATRICULA_ALUNO = ALUNO.MATRICULA";
            PreparedStatement tComandoJDBC = conexao.prepareStatement(tComandoSQL);

            // Colocando o parâmetro recebido no comando JDBC
            tComandoJDBC.setInt(1, pExemplar.getId());

            // Executando o comando e salvando o ResultSet para processar
            ResultSet tResultSet = tComandoJDBC.executeQuery();

            
            // Enquanto houver registros, processa
            while (tResultSet.next())
            {
                // Salvando o Emprestimo retornado para adicionar na lista
                Emprestimo tEmprestimo = carregarEmprestimo(tResultSet);

                // Adicionando o emprestimo na tLista
                tLista.add(tEmprestimo);
            }

            // Liberando os recursos JDBC
            tResultSet.close();
            tComandoJDBC.close();
        }
        catch (SQLException tExcept)
        {
            ExceptionUtil.mostrarErro(tExcept, "Erro no método de recuperação da lista de empréstimos");
        }

        // Retornando a lista de empréstimos
        return tLista;
    }

    private Emprestimo carregarEmprestimo(ResultSet tResultSet) throws SQLException
    {
        // Criando um novo emprestimo para armazenar as informações lidas
        Aluno tAluno = new Aluno();
        Exemplar tExemplar = new Exemplar();
        Livro tLivro = new Livro();
        Emprestimo tEmprestimo = new Emprestimo();
    
        // Recuperando as informações do ResultSet e colocando objeto criado
        tAluno.setMatricula(tResultSet.getLong("MATRICULA"));
        tAluno.setNome(tResultSet.getString("NOME"));
        tAluno.setCurso(tResultSet.getString("CURSO"));
        tAluno.setTelefone(tResultSet.getLong("TELEFONE"));
        tAluno.setEmail(tResultSet.getString("EMAIL"));
        tAluno.setSituacao(SituacaoAluno.fromChar(tResultSet.getString("SITUACAO").charAt(0)));
    
        tLivro.setIsbn(tResultSet.getLong("ISBN"));
        tLivro.setTitulo(tResultSet.getString("TITULO"));
        tLivro.setAutor(tResultSet.getString("AUTOR"));
        tLivro.setEditora(tResultSet.getString("EDITORA"));
        tLivro.setAnoEdicao(tResultSet.getInt("ANO_EDICAO"));
        tLivro.setEdicao(tResultSet.getInt("EDICAO"));
        tLivro.setValorCompra(tResultSet.getBigDecimal("VALOR_COMPRA"));
        
        tExemplar.setId(tResultSet.getInt("ID_EXEMPLAR"));
        tExemplar.setLivro(tLivro);
        tExemplar.setExemplarLocal(tResultSet.getString("EXEMPLAR_LOCAL").charAt(0) == 'S');
        
        tEmprestimo.setId(tResultSet.getInt("ID_EMPRESTIMO"));
        tEmprestimo.setAluno(tAluno);
        tEmprestimo.setExemplar(tExemplar);
        tEmprestimo.setDataLocacao(tResultSet.getDate("DATA_LOCACAO").toLocalDate());
        Date tDataDevolucao = tResultSet.getDate("DATA_DEVOLUCAO");
        if (tDataDevolucao == null)
            tEmprestimo.setDataDevolucao(null);
        else
            tEmprestimo.setDataDevolucao(tDataDevolucao.toLocalDate());
        tEmprestimo.setStatus(StatusEmprestimo.fromChar(tResultSet.getString("STATUS").charAt(0)));
        BigDecimal tValorMulta = tResultSet.getBigDecimal("VALOR_MULTA");
        if (tValorMulta == null)
            tEmprestimo.setValorMulta(null);
        else
            tEmprestimo.setValorMulta(tValorMulta);
    
        // Retornando o emprestimo criado
        return tEmprestimo;
    }
}
