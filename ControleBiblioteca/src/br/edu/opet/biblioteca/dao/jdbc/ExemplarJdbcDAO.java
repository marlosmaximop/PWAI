package br.edu.opet.biblioteca.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.opet.biblioteca.dao.ExemplarDAO;
import br.edu.opet.biblioteca.jdbc.Conexao;
import br.edu.opet.biblioteca.model.Aluno;
import br.edu.opet.biblioteca.model.Exemplar;
import br.edu.opet.biblioteca.model.Livro;
import br.edu.opet.biblioteca.util.ExceptionUtil;

public class ExemplarJdbcDAO implements ExemplarDAO
{
    private Connection conexao = Conexao.getConexao();                               // Conexão com o banco de dados
    private String     campos  = "ID, ISBN_LIVRO, EXEMPLAR_LOCAL";

    @Override
    public Exemplar create(Exemplar pExemplar)
    {
        // Definindo o objeto exemplar de retorno
        Exemplar tExemplar = null;

        try
        {
            // Criando o comando SQL e o comando JDBC
            String tComandoSQL =
                "INSERT INTO EXEMPLAR (" + campos + ") VALUES (EXEMPLAR_SEQ.NEXTVAL, ?, ?)";
            PreparedStatement tComandoJDBC = conexao.prepareStatement(tComandoSQL, new String [] {"ID"});

            // Colocando os parâmetros recebidos no comando JDBC
            int i = 1;

            tComandoJDBC.setLong(i++, pExemplar.getLivro().getIsbn());
            tComandoJDBC.setString(i++, pExemplar.isExemplarLocal() ? "S" : "N");

            // Executando o comando de gravação
            int tQtdeReg = tComandoJDBC.executeUpdate();

            // Verificando se um registro foi incluído
            if (tQtdeReg == 1)
            {
                // Copiando o exemplar para o retorno
                tExemplar = pExemplar;

                // Recuperando o código gerado pelo Oracle
                ResultSet tRsChave = tComandoJDBC.getGeneratedKeys();
                tRsChave.next();
                tExemplar.setId(tRsChave.getInt(1));
            }

            // Liberando os recursos JDBC
            tComandoJDBC.close();
        }
        catch (SQLException tExcept)
        {
            ExceptionUtil.mostrarErro(tExcept, "Erro no método de criação do exemplar");
        }

        // Retornando o objeto Exemplar
        return tExemplar;
    }

    @Override
    public Exemplar recovery(int pId)
    {
        // Definindo o objeto exemplar de retorno
        Exemplar tExemplar = null;

        try
        {
            // Criando o comando SQL e o comando JDBC
            String tComandoSQL = "SELECT EXEMPLAR.ID, LIVRO.ISBN, LIVRO.TITULO, LIVRO.AUTOR, "
                            + "LIVRO.EDITORA, LIVRO.ANO_EDICAO, LIVRO.EDICAO, LIVRO.VALOR_COMPRA, "
                            + "EXEMPLAR.EXEMPLAR_LOCAL FROM EXEMPLAR, LIVRO "
                            + "WHERE EXEMPLAR.ID = ? AND EXEMPLAR.ISBN_LIVRO = LIVRO.ISBN";
            PreparedStatement tComandoJDBC = conexao.prepareStatement(tComandoSQL);

            // Colocando o parâmetro recebido no comando JDBC
            tComandoJDBC.setLong(1, pId);

            // Executando o comando e salvando o ResultSet para processar
            ResultSet tResultSet = tComandoJDBC.executeQuery();

            // Verificando se um registro foi lido
            if (tResultSet.next())
            {
                // Salvando o Exemplar para retornar depois
                tExemplar = carregarExemplar(tResultSet);
            }

            // Liberando os recursos JDBC
            tResultSet.close();
            tComandoJDBC.close();
        }
        catch (SQLException tExcept)
        {
            ExceptionUtil.mostrarErro(tExcept, "Erro no método de recuperação do exemplar");
        }

        // Retornando o objeto Exemplar
        return tExemplar;
    }

    @Override
    public Exemplar update(Exemplar pExemplar)
    {
        // Definindo o objeto exemplar de retorno
        Exemplar tExemplar = null;

        try
        {
            // Criando o comando SQL e o comando JDBC
            String tComandoSQL =
                            "UPDATE EXEMPLAR SET "
                            + "ISBN_LIVRO = ?, "
                            + "EXEMPLAR_LOCAL = ? "
                            + "WHERE ID = ?";
            PreparedStatement tComandoJDBC = conexao.prepareStatement(tComandoSQL);

            // Colocando os parâmetros recebidos no comando JDBC
            int i = 1;

            tComandoJDBC.setLong(i++, pExemplar.getLivro().getIsbn());
            tComandoJDBC.setString(i++, pExemplar.isExemplarLocal() ? "S" : "N");
            tComandoJDBC.setLong(i++, pExemplar.getId());

            // Executando o comando de regravação
            int tQtdeReg = tComandoJDBC.executeUpdate();

            // Verificando se um registro foi alterado
            if (tQtdeReg == 1)
            {
                // Copiando o exemplar para o retorno
                tExemplar = pExemplar;
            }

            // Liberando os recursos JDBC
            tComandoJDBC.close();
        }
        catch (SQLException tExcept)
        {
            ExceptionUtil.mostrarErro(tExcept, "Erro no método de atualização do exemplar");
        }

        // Retornando o objeto Exemplar
        return tExemplar;
    }

    @Override
    public boolean delete(int pId)
    {
        try
        {
            // Criando o comando SQL e o comando JDBC
            String tComandoSQL = "DELETE EXEMPLAR " +
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
            ExceptionUtil.mostrarErro(tExcept, "Erro no método de remoção do exemplar");
        }

        // Retornando indicativo de falha de remoção
        return false;
    }

    @Override
    public List<Exemplar> search()
    {
        // Criando a tLista de exemplares vazia
        List<Exemplar> tLista = new ArrayList<>();

        try
        {
            // Criando o comando SQL e o comando JDBC
            String tComandoSQL = "SELECT EXEMPLAR.ID, LIVRO.ISBN, LIVRO.TITULO, "
                            + "LIVRO.AUTOR, LIVRO.EDITORA, LIVRO.ANO_EDICAO, "
                            + "LIVRO.EDICAO, LIVRO.VALOR_COMPRA, EXEMPLAR.EXEMPLAR_LOCAL "
                            + "FROM EXEMPLAR, LIVRO  "
                            + "WHERE EXEMPLAR.ISBN_LIVRO = LIVRO.ISBN";
            PreparedStatement tComandoJDBC = conexao.prepareStatement(tComandoSQL);

            // Executando o comando e salvando o ResultSet para processar
            ResultSet tResultSet = tComandoJDBC.executeQuery();

            // Enquanto houver registros, processa
            while (tResultSet.next())
            {
                // Salvando o Exemplar retornado para adicionar na lista
                Exemplar tExemplar = carregarExemplar(tResultSet);

                // Adicionando o exemplar na tLista
                tLista.add(tExemplar);
            }

            // Liberando os recursos JDBC
            tResultSet.close();
            tComandoJDBC.close();
        }
        catch (SQLException tExcept)
        {
            ExceptionUtil.mostrarErro(tExcept, "Erro no método de recuperação da lista de exemplares");
        }

        // Retornando a lista de exemplares
        return tLista;
    }

    @Override
    public List<Exemplar> searchByLivro(Livro pLivro)
    {
        // Criando a tLista de exemplares vazia
        List<Exemplar> tLista = new ArrayList<>();

        try
        {
            // Criando o comando SQL e o comando JDBC
            String tComandoSQL = "SELECT EXEMPLAR.ID, LIVRO.ISBN, LIVRO.TITULO, "
                            + "LIVRO.AUTOR, LIVRO.EDITORA, LIVRO.ANO_EDICAO, "
                            + "LIVRO.EDICAO, LIVRO.VALOR_COMPRA, EXEMPLAR.EXEMPLAR_LOCAL "
                            + "FROM EXEMPLAR, LIVRO  "
                            + "WHERE EXEMPLAR.ISBN_LIVRO = ? AND EXEMPLAR.ISBN_LIVRO = LIVRO.ISBN";
            PreparedStatement tComandoJDBC = conexao.prepareStatement(tComandoSQL);

            // Colocando o parâmetro recebido no comando JDBC
            tComandoJDBC.setLong(1, pLivro.getIsbn());

            // Executando o comando e salvando o ResultSet para processar
            ResultSet tResultSet = tComandoJDBC.executeQuery();

            // Enquanto houver registros, processa
            while (tResultSet.next())
            {
                // Salvando o Exemplar retornado para adicionar na lista
                Exemplar tExemplar = carregarExemplar(tResultSet);

                // Adicionando o exemplar na tLista
                tLista.add(tExemplar);
            }

            // Liberando os recursos JDBC
            tResultSet.close();
            tComandoJDBC.close();
        }
        catch (SQLException tExcept)
        {
            ExceptionUtil.mostrarErro(tExcept, "Erro no método de recuperação da lista de exemplares");
        }

        // Retornando a lista de exemplares
        return tLista;
    }

    private Exemplar carregarExemplar(ResultSet tResultSet) throws SQLException
    {
        // Criando um novo exemplar para armazenar as informações lidas
        Livro tLivro = new Livro();
        Exemplar tExemplar = new Exemplar();
    
        // Recuperando as informações do ResultSet e colocando objeto criado
        tLivro.setIsbn(tResultSet.getLong("ISBN"));
        tLivro.setTitulo(tResultSet.getString("TITULO"));
        tLivro.setAutor(tResultSet.getString("AUTOR"));
        tLivro.setEditora(tResultSet.getString("EDITORA"));
        tLivro.setAnoEdicao(tResultSet.getInt("ANO_EDICAO"));
        tLivro.setEdicao(tResultSet.getInt("EDICAO"));
        tLivro.setValorCompra(tResultSet.getBigDecimal("VALOR_COMPRA"));
        
        tExemplar.setId(tResultSet.getInt("ID"));
        tExemplar.setLivro(tLivro);
        tExemplar.setExemplarLocal(tResultSet.getString("EXEMPLAR_LOCAL").charAt(0) == 'S');
    
        // Retornando o exemplar criado
        return tExemplar;
    }
}
