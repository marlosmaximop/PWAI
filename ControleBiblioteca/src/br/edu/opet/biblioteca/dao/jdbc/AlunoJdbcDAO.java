package br.edu.opet.biblioteca.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.edu.opet.biblioteca.dao.AlunoDAO;
import br.edu.opet.biblioteca.enums.SituacaoAluno;
import br.edu.opet.biblioteca.jdbc.Conexao;
import br.edu.opet.biblioteca.model.Aluno;
import br.edu.opet.biblioteca.util.ExceptionUtil;

public class AlunoJdbcDAO implements AlunoDAO
{
    private Connection conexao = Conexao.getConexao();                               // Conexão com o banco de dados
    private String     campos  = "MATRICULA, NOME, CURSO, TELEFONE, EMAIL, SITUACAO, DATA_ATUALIZACAO";

    @Override
    public Aluno create(Aluno pAluno)
    {
        // Definindo o objeto aluno de retorno
        Aluno tAluno = null;

        try
        {
            // Criando o comando SQL e o comando JDBC
            String tComandoSQL =
                "INSERT INTO ALUNO (" + campos + ") VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement tComandoJDBC = conexao.prepareStatement(tComandoSQL);

            // Colocando os parâmetros recebidos no comando JDBC
            int i = 1;

            tComandoJDBC.setLong(i++, pAluno.getMatricula());
            tComandoJDBC.setString(i++, pAluno.getNome());
            tComandoJDBC.setString(i++, pAluno.getCurso());
            tComandoJDBC.setLong(i++, pAluno.getTelefone());
            tComandoJDBC.setString(i++, pAluno.getEmail());
            tComandoJDBC.setString(i++, String.valueOf(pAluno.getSituacao().getCodigo()));
            tComandoJDBC.setTimestamp(i++, Timestamp.valueOf(LocalDateTime.now()));

            // Executando o comando de gravação
            int tQtdeReg = tComandoJDBC.executeUpdate();

            // Verificando se um registro foi incluído
            if (tQtdeReg == 1)
            {
                // Copiando o aluno para o retorno
                tAluno = pAluno;
            }

            // Liberando os recursos JDBC
            tComandoJDBC.close();
        }
        catch (SQLException tExcept)
        {
            ExceptionUtil.mostrarErro(tExcept, "Erro no método de criação do aluno");
        }

        // Retornando o objeto Aluno
        return tAluno;
    }

    @Override
    public Aluno recovery(long pMatricula)
    {
        // Definindo o objeto aluno de retorno
        Aluno tAluno = null;

        try
        {
            // Criando o comando SQL e o comando JDBC
            String tComandoSQL =
                            "SELECT " 
                            + campos  
                            + " FROM ALUNO "
                            + "WHERE MATRICULA = ?";
            PreparedStatement tComandoJDBC = conexao.prepareStatement(tComandoSQL);

            // Colocando o parâmetro recebido no comando JDBC
            tComandoJDBC.setLong(1, pMatricula);

            // Executando o comando e salvando o ResultSet para processar
            ResultSet tResultSet = tComandoJDBC.executeQuery();

            // Verificando se um registro foi lido
            if (tResultSet.next())
            {
                // Salvando o Aluno para retornar depois
                tAluno = carregarAluno(tResultSet);
            }

            // Liberando os recursos JDBC
            tResultSet.close();
            tComandoJDBC.close();
        }
        catch (SQLException tExcept)
        {
            ExceptionUtil.mostrarErro(tExcept, "Erro no método de recuperação do aluno");
        }

        // Retornando o objeto Aluno
        return tAluno;
    }

    @Override
    public Aluno update(Aluno pAluno)
    {
        // Definindo o objeto aluno de retorno
        Aluno tAluno = null;

        try
        {
            // Criando o comando SQL e o comando JDBC
            String tComandoSQL =
                            "UPDATE ALUNO SET "
                            + "NOME = ?, "
                            + "CURSO = ?, "
                            + "TELEFONE = ?, "
                            + "EMAIL = ?, "
                            + "SITUACAO = ?, " 
                            + "DATA_ATUALIZACAO = ? " 
                            + "WHERE MATRICULA = ?";
            PreparedStatement tComandoJDBC = conexao.prepareStatement(tComandoSQL);

            // Colocando os parâmetros recebidos no comando JDBC
            int i = 1;

            tComandoJDBC.setString(i++, pAluno.getNome());
            tComandoJDBC.setString(i++, pAluno.getCurso());
            tComandoJDBC.setLong(i++, pAluno.getTelefone());
            tComandoJDBC.setString(i++, pAluno.getEmail());
            tComandoJDBC.setString(i++, String.valueOf(pAluno.getSituacao().getCodigo()));
            tComandoJDBC.setTimestamp(i++, Timestamp.valueOf(LocalDateTime.now()));
            tComandoJDBC.setLong(i++, pAluno.getMatricula());

            // Executando o comando de regravação
            int tQtdeReg = tComandoJDBC.executeUpdate();

            // Verificando se um registro foi alterado
            if (tQtdeReg == 1)
            {
                // Copiando o aluno para o retorno
                tAluno = pAluno;
            }

            // Liberando os recursos JDBC
            tComandoJDBC.close();
        }
        catch (SQLException tExcept)
        {
            ExceptionUtil.mostrarErro(tExcept, "Erro no método de atualização do aluno");
        }

        // Retornando o objeto Aluno
        return tAluno;
    }

    @Override
    public boolean delete(long pMatricula)
    {
        try
        {
            // Criando o comando SQL e o comando JDBC
            String tComandoSQL =
                            "DELETE ALUNO "
                            + "WHERE MATRICULA = ?";
            PreparedStatement tComandoJDBC = conexao.prepareStatement(tComandoSQL);

            // Colocando o parâmetro recebido no comando JDBC
            int i = 1;

            tComandoJDBC.setLong(i++, pMatricula);

            // Executando o comando de remoção
            int tQtdeReg = tComandoJDBC.executeUpdate();

            // Liberando os recursos JDBC
            tComandoJDBC.close();

            // Se excluiu um registro, a remoção foi efetuada com sucesso
            return tQtdeReg == 1;
        }
        catch (SQLException tExcept)
        {
            ExceptionUtil.mostrarErro(tExcept, "Erro no método de remoção do aluno");
        }

        // Retornando indicativo de falha de remoção
        return false;
    }

    @Override
    public List<Aluno> search()
    {
        // Criando a tLista de alunos vazia
        List<Aluno> tLista = new ArrayList<>();

        try
        {
            // Criando o comando SQL e o comando JDBC
            String tComandoSQL =
                            "SELECT " + campos + " FROM ALUNO " 
                            + "ORDER BY UPPER(NOME)";
            PreparedStatement tComandoJDBC = conexao.prepareStatement(tComandoSQL);

            // Executando o comando e salvando o ResultSet para processar
            ResultSet tResultSet = tComandoJDBC.executeQuery();

            // Enquanto houver registros, processa
            while (tResultSet.next())
            {
                // Salvando o Aluno retornado para adicionar na lista
                Aluno tAluno = carregarAluno(tResultSet);

                // Adicionando o aluno na tLista
                tLista.add(tAluno);
            }

            // Liberando os recursos JDBC
            tResultSet.close();
            tComandoJDBC.close();
        }
        catch (SQLException tExcept)
        {
            ExceptionUtil.mostrarErro(tExcept, "Erro no método de recuperação da lista de alunos");
        }

        // Retornando a lista de alunos
        return tLista;
    }

    @Override
    public List<Aluno> searchByNome(String pNome)
    {
        // Acertando o critério de pesquisa
        String tNomePesquisa = "%" + pNome + "%";

        // Criando a tLista de alunos vazia
        List<Aluno> tLista = new ArrayList<>();

        try
        {
            // Criando o comando SQL e o comando JDBC
            String tComandoSQL =
                            "SELECT " + campos + " FROM ALUNO " +
                            "WHERE UPPER(NOME) LIKE UPPER(?) " +
                            "ORDER BY UPPER(NOME)";
            PreparedStatement tComandoJDBC = conexao.prepareStatement(tComandoSQL);

            // Colocando o parâmetro recebido no comando JDBC
            tComandoJDBC.setString(1, tNomePesquisa);

            // Executando o comando e salvando o ResultSet para processar
            ResultSet tResultSet = tComandoJDBC.executeQuery();

            // Enquanto houver registros, processa
            while (tResultSet.next())
            {
                // Salvando o Aluno retornado para adicionar na lista
                Aluno tAluno = carregarAluno(tResultSet);

                // Adicionando o aluno na tLista
                tLista.add(tAluno);
            }

            // Liberando os recursos JDBC
            tResultSet.close();
            tComandoJDBC.close();
        }
        catch (SQLException tExcept)
        {
            ExceptionUtil.mostrarErro(tExcept, "Erro no método da pesquisa por nome dos alunos");
        }

        // Retornando a lista de alunos
        return tLista;
    }

    @Override
    public List<Aluno> searchByCurso(String pCurso)
    {
        // Acertando o critério de pesquisa
        String tCursoPesquisa = "%" + pCurso + "%";

        // Criando a tLista de alunos vazia
        List<Aluno> tLista = new ArrayList<>();

        try
        {
            // Criando o comando SQL e o comando JDBC
            String tComandoSQL =
                            "SELECT " + campos + " FROM ALUNO " +
                            " WHERE UPPER(CURSO) LIKE UPPER(?) " +
                            "ORDER BY UPPER(NOME)";
            PreparedStatement tComandoJDBC = conexao.prepareStatement(tComandoSQL);

            // Colocando o parâmetro recebido no comando JDBC
            tComandoJDBC.setString(1, tCursoPesquisa);

            // Executando o comando e salvando o ResultSet para processar
            ResultSet tResultSet = tComandoJDBC.executeQuery();

            // Enquanto houver registros, processa
            while (tResultSet.next())
            {
                // Salvando o Aluno retornado para adicionar na lista
                Aluno tAluno = carregarAluno(tResultSet);

                // Adicionando o aluno na tLista
                tLista.add(tAluno);
            }

            // Liberando os recursos JDBC
            tResultSet.close();
            tComandoJDBC.close();
        }
        catch (SQLException tExcept)
        {
            ExceptionUtil.mostrarErro(tExcept, "Erro no método da pesquisa por nome dos alunos");
        }

        // Retornando a lista de alunos
        return tLista;
    }

    @Override
    public List<Aluno> searchByEmail(String pEmail)
    {
        // Acertando o critério de pesquisa
        String tEmailNomePesquisa = "%" + pEmail + "%";

        // Criando a tLista de alunos vazia
        List<Aluno> tLista = new ArrayList<>();

        try
        {
            // Criando o comando SQL e o comando JDBC
            String tComandoSQL =
                            "SELECT " + campos + " FROM ALUNO " +
                            "WHERE UPPER(EMAIL) LIKE UPPER(?) " +
                            "ORDER BY UPPER(NOME)";
            PreparedStatement tComandoJDBC = conexao.prepareStatement(tComandoSQL);

            // Colocando o parâmetro recebido no comando JDBC
            tComandoJDBC.setString(1, tEmailNomePesquisa);

            // Executando o comando e salvando o ResultSet para processar
            ResultSet tResultSet = tComandoJDBC.executeQuery();

            // Enquanto houver registros, processa
            while (tResultSet.next())
            {
                // Salvando o Aluno retornado para adicionar na lista
                Aluno tAluno = carregarAluno(tResultSet);

                // Adicionando o aluno na tLista
                tLista.add(tAluno);
            }

            // Liberando os recursos JDBC
            tResultSet.close();
            tComandoJDBC.close();
        }
        catch (SQLException tExcept)
        {
            ExceptionUtil.mostrarErro(tExcept, "Erro no método da pesquisa por nome dos alunos");
        }

        // Retornando a lista de alunos
        return tLista;
    }

    private Aluno carregarAluno(ResultSet tResultSet) throws SQLException
    {
        // Criando um novo aluno para armazenar as informações lidas
        Aluno tAluno = new Aluno();

        // Recuperando as informações do ResultSet e colocando objeto criado
        tAluno.setMatricula(tResultSet.getLong("MATRICULA"));
        tAluno.setNome(tResultSet.getString("NOME"));
        tAluno.setCurso(tResultSet.getString("CURSO"));
        tAluno.setTelefone(tResultSet.getLong("TELEFONE"));
        tAluno.setEmail(tResultSet.getString("EMAIL"));
        tAluno.setSituacao(SituacaoAluno.fromChar(tResultSet.getString("SITUACAO").charAt(0)));

        // Retornando o aluno criado
        return tAluno;
    }
}
