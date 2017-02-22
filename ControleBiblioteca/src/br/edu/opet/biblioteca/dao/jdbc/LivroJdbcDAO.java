package br.edu.opet.biblioteca.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.opet.biblioteca.dao.LivroDAO;
import br.edu.opet.biblioteca.jdbc.Conexao;
import br.edu.opet.biblioteca.model.Livro;
import br.edu.opet.biblioteca.util.ExceptionUtil;

public class LivroJdbcDAO implements LivroDAO
{
    private Connection conexao = Conexao.getConexao();                               // Conexão com o banco de dados
    private String     campos  = "ISBN, TITULO, AUTOR, EDITORA, ANO_EDICAO, EDICAO, VALOR_COMPRA";
    @Override
    public Livro create(Livro pLivro)
    {
        // Definindo o objeto livro de retorno
        Livro tLivro = null;

        try
        {
            // Criando o comando SQL e o comando JDBC
            String tComandoSQL =
                "INSERT INTO LIVRO (" + campos + ") VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement tComandoJDBC = conexao.prepareStatement(tComandoSQL);

            // Colocando os parâmetros recebidos no comando JDBC
            int i = 1;

            tComandoJDBC.setLong(i++, pLivro.getIsbn());
            tComandoJDBC.setString(i++, pLivro.getTitulo());
            tComandoJDBC.setString(i++, pLivro.getAutor());
            tComandoJDBC.setString(i++, pLivro.getEditora());
            tComandoJDBC.setInt(i++, pLivro.getAnoEdicao());
            tComandoJDBC.setInt(i++, pLivro.getEdicao());
            tComandoJDBC.setBigDecimal(i++, pLivro.getValorCompra());

            // Executando o comando de gravação
            int tQtdeReg = tComandoJDBC.executeUpdate();

            // Verificando se um registro foi incluído
            if (tQtdeReg == 1)
            {
                // Copiando o livro para o retorno
                tLivro = pLivro;
            }

            // Liberando os recursos JDBC
            tComandoJDBC.close();
        }
        catch (SQLException tExcept)
        {
            ExceptionUtil.mostrarErro(tExcept, "Erro no método de criação do livro");
        }

        // Retornando o objeto Livro
        return tLivro;
    }

    @Override
    public Livro recovery(long pIsbn)
    {
        // Definindo o objeto livro de retorno
        Livro tLivro = null;

        try
        {
            // Criando o comando SQL e o comando JDBC
            String tComandoSQL =
                            "SELECT " 
                            + campos  
                            + " FROM LIVRO "
                            + "WHERE ISBN = ?";
            PreparedStatement tComandoJDBC = conexao.prepareStatement(tComandoSQL);

            // Colocando o parâmetro recebido no comando JDBC
            tComandoJDBC.setLong(1, pIsbn);

            // Executando o comando e salvando o ResultSet para processar
            ResultSet tResultSet = tComandoJDBC.executeQuery();

            // Verificando se um registro foi lido
            if (tResultSet.next())
            {
                // Salvando o Livro para retornar depois
                tLivro = carregarLivro(tResultSet);
            }

            // Liberando os recursos JDBC
            tResultSet.close();
            tComandoJDBC.close();
        }
        catch (SQLException tExcept)
        {
            ExceptionUtil.mostrarErro(tExcept, "Erro no método de recuperação do livro");
        }

        // Retornando o objeto Livro
        return tLivro;
    }

    @Override
    public Livro update(Livro pLivro)
    {
        // Definindo o objeto livro de retorno
        Livro tLivro = null;

        try
        {
            // Criando o comando SQL e o comando JDBC
            String tComandoSQL =
                            "UPDATE LIVRO SET "
                            + "TITULO = ?, "
                            + "AUTOR = ?, "
                            + "EDITORA = ?, "
                            + "ANO_EDICAO = ?, "
                            + "EDICAO = ?, " 
                            + "VALOR_COMPRA = ? " 
                            + "WHERE ISBN = ?";
            PreparedStatement tComandoJDBC = conexao.prepareStatement(tComandoSQL);

            // Colocando os parâmetros recebidos no comando JDBC
            int i = 1;

            tComandoJDBC.setString(i++, pLivro.getTitulo());
            tComandoJDBC.setString(i++, pLivro.getAutor());
            tComandoJDBC.setString(i++, pLivro.getEditora());
            tComandoJDBC.setInt(i++, pLivro.getAnoEdicao());
            tComandoJDBC.setInt(i++, pLivro.getEdicao());
            tComandoJDBC.setBigDecimal(i++, pLivro.getValorCompra());
            tComandoJDBC.setLong(i++, pLivro.getIsbn());

            // Executando o comando de regravação
            int tQtdeReg = tComandoJDBC.executeUpdate();

            // Verificando se um registro foi alterado
            if (tQtdeReg == 1)
            {
                // Copiando o livro para o retorno
                tLivro = pLivro;
            }

            // Liberando os recursos JDBC
            tComandoJDBC.close();
        }
        catch (SQLException tExcept)
        {
            ExceptionUtil.mostrarErro(tExcept, "Erro no método de atualização do livro");
        }

        // Retornando o objeto Livro
        return tLivro;
    }

    @Override
    public boolean delete(long pIsbn)
    {
        try
        {
            // Criando o comando SQL e o comando JDBC
            String tComandoSQL = "DELETE LIVRO " +
                            " WHERE ISBN = ?";
            PreparedStatement tComandoJDBC = conexao.prepareStatement(tComandoSQL);

            // Colocando o parâmetro recebido no comando JDBC
            tComandoJDBC.setLong(1, pIsbn);

            // Executando o comando de remoção
            int tQtdeReg = tComandoJDBC.executeUpdate();

            // Liberando os recursos JDBC
            tComandoJDBC.close();

            // Se excluiu um registro, a remoção foi efetuada com sucesso
            return tQtdeReg == 1;
        }
        catch (SQLException tExcept)
        {
            ExceptionUtil.mostrarErro(tExcept, "Erro no método de remoção do livro");
        }

        // Retornando indicativo de falha de remoção
        return false;
    }

    @Override
    public List<Livro> search()
    {
        // Criando a tLista de livros vazia
        List<Livro> tLista = new ArrayList<>();

        try
        {
            // Criando o comando SQL e o comando JDBC
            String tComandoSQL =
                            "SELECT " + campos + " FROM LIVRO " +
                            "ORDER BY UPPER(TITULO)";
            PreparedStatement tComandoJDBC = conexao.prepareStatement(tComandoSQL);

            // Executando o comando e salvando o ResultSet para processar
            ResultSet tResultSet = tComandoJDBC.executeQuery();

            // Enquanto houver registros, processa
            while (tResultSet.next())
            {
                // Salvando o Livro retornado para adicionar na lista
                Livro tLivro = carregarLivro(tResultSet);

                // Adicionando o livro na tLista
                tLista.add(tLivro);
            }

            // Liberando os recursos JDBC
            tResultSet.close();
            tComandoJDBC.close();
        }
        catch (SQLException tExcept)
        {
            ExceptionUtil.mostrarErro(tExcept, "Erro no método de recuperação da lista de livros");
        }

        // Retornando a lista de livros
        return tLista;
    }

    @Override
    public List<Livro> searchByTitulo(String pTitulo)
    {
        // Acertando o critério de pesquisa
        String tNomePesquisa = "%" + pTitulo + "%";

        // Criando a tLista de livros vazia
        List<Livro> tLista = new ArrayList<>();

        try
        {
            // Criando o comando SQL e o comando JDBC
            String tComandoSQL =
                            "SELECT " + campos + " FROM LIVRO " +
                            "WHERE UPPER(TITULO) LIKE UPPER(?)" +
                            "ORDER BY UPPER(TITULO)";
            PreparedStatement tComandoJDBC = conexao.prepareStatement(tComandoSQL);

            // Colocando o parâmetro recebido no comando JDBC
            tComandoJDBC.setString(1, tNomePesquisa);

            // Executando o comando e salvando o ResultSet para processar
            ResultSet tResultSet = tComandoJDBC.executeQuery();

            // Enquanto houver registros, processa
            while (tResultSet.next())
            {
                // Salvando o Livro retornado para adicionar na lista
                Livro tLivro = carregarLivro(tResultSet);

                // Adicionando o livro na tLista
                tLista.add(tLivro);
            }

            // Liberando os recursos JDBC
            tResultSet.close();
            tComandoJDBC.close();
        }
        catch (SQLException tExcept)
        {
            ExceptionUtil.mostrarErro(tExcept, "Erro no método da pesquisa por título dos livros");
        }

        // Retornando a lista de livros
        return tLista;
    }

    @Override
    public List<Livro> searchByAutor(String pAutor)
    {
        // Acertando o critério de pesquisa
        String tCursoPesquisa = "%" + pAutor + "%";

        // Criando a tLista de livros vazia
        List<Livro> tLista = new ArrayList<>();

        try
        {
            // Criando o comando SQL e o comando JDBC
            String tComandoSQL =
                            "SELECT " + campos + " FROM LIVRO " +
                            " WHERE UPPER(AUTOR) LIKE UPPER(?)" +
                            "ORDER BY UPPER(TITULO)";
            PreparedStatement tComandoJDBC = conexao.prepareStatement(tComandoSQL);

            // Colocando o parâmetro recebido no comando JDBC
            tComandoJDBC.setString(1, tCursoPesquisa);

            // Executando o comando e salvando o ResultSet para processar
            ResultSet tResultSet = tComandoJDBC.executeQuery();

            // Enquanto houver registros, processa
            while (tResultSet.next())
            {
                // Salvando o Livro retornado para adicionar na lista
                Livro tLivro = carregarLivro(tResultSet);

                // Adicionando o livro na tLista
                tLista.add(tLivro);
            }

            // Liberando os recursos JDBC
            tResultSet.close();
            tComandoJDBC.close();
        }
        catch (SQLException tExcept)
        {
            ExceptionUtil.mostrarErro(tExcept, "Erro no método da pesquisa por autor dos livros");
        }

        // Retornando a lista de livros
        return tLista;
    }

    @Override
    public List<Livro> searchByEditora(String pEditora)
    {
        // Acertando o critério de pesquisa
        String tEmailNomePesquisa = "%" + pEditora + "%";

        // Criando a tLista de livros vazia
        List<Livro> tLista = new ArrayList<>();

        try
        {
            // Criando o comando SQL e o comando JDBC
            String tComandoSQL =
                            "SELECT " + campos + " FROM LIVRO " +
                            "WHERE UPPER(EDITORA) LIKE UPPER(?)" +
                            "ORDER BY UPPER(TITULO)";
            PreparedStatement tComandoJDBC = conexao.prepareStatement(tComandoSQL);

            // Colocando o parâmetro recebido no comando JDBC
            tComandoJDBC.setString(1, tEmailNomePesquisa);

            // Executando o comando e salvando o ResultSet para processar
            ResultSet tResultSet = tComandoJDBC.executeQuery();

            // Enquanto houver registros, processa
            while (tResultSet.next())
            {
                // Salvando o Livro retornado para adicionar na lista
                Livro tLivro = carregarLivro(tResultSet);

                // Adicionando o livro na tLista
                tLista.add(tLivro);
            }

            // Liberando os recursos JDBC
            tResultSet.close();
            tComandoJDBC.close();
        }
        catch (SQLException tExcept)
        {
            ExceptionUtil.mostrarErro(tExcept, "Erro no método da pesquisa por editora dos livros");
        }

        // Retornando a lista de livros
        return tLista;
    }

    private Livro carregarLivro(ResultSet tResultSet) throws SQLException
    {
        // Criando um novo livro para armazenar as informações lidas
        Livro tLivro = new Livro();

        // Recuperando as informações do ResultSet e colocando objeto criado
        tLivro.setIsbn(tResultSet.getLong("ISBN"));
        tLivro.setTitulo(tResultSet.getString("TITULO"));
        tLivro.setAutor(tResultSet.getString("AUTOR"));
        tLivro.setEditora(tResultSet.getString("EDITORA"));
        tLivro.setAnoEdicao(tResultSet.getInt("ANO_EDICAO"));
        tLivro.setEdicao(tResultSet.getInt("EDICAO"));
        tLivro.setValorCompra(tResultSet.getBigDecimal("VALOR_COMPRA"));

        // Retornando o livro criado
        return tLivro;
    }
}
