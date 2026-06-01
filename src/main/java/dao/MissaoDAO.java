package dao;

import entities.Missao;
import infra.DataBaseConfigs;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MissaoDAO {
    public void inserir(Missao m) {
        String sql = """
                INSERT INTO MISSOES
                (id, nome, data_lancamento, duracao_dias,
                 descricao, id_agencia, id_status, id_categoria)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;
        try (Connection conn = DataBaseConfigs.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, m.getId());
            stmt.setString(2, m.getNome());
            stmt.setDate(
                    3,
                    Date.valueOf(m.getDataLancamento())
            );
            stmt.setInt(4, m.getDuracaoDias());
            stmt.setString(5, m.getDescricao());
            stmt.setLong(6, m.getAgenciaId());
            stmt.setLong(7, m.getStatusId());
            stmt.setLong(8, m.getCategoriaId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao inserir missão",
                    e
            );
        }
    }
    public List<Missao> listar() {
        List<Missao> lista = new ArrayList<>();
        String sql = "SELECT * FROM MISSOES";
        try (Connection conn = DataBaseConfigs.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery())
        {
            while (rs.next()) {
                Missao m = new Missao();
                m.setId(rs.getLong("id"));
                m.setNome(rs.getString("nome"));

                Date data = rs.getDate("data_lancamento");

                if (data != null) {
                    m.setDataLancamento(
                            data.toLocalDate()
                    );
                }
                m.setDuracaoDias(
                        rs.getInt("duracao_dias")
                );
                m.setDescricao(
                        rs.getString("descricao")
                );
                m.setAgenciaId(
                        rs.getLong("id_agencia")
                );
                m.setStatusId(
                        rs.getLong("id_status")
                );
                m.setCategoriaId(
                        rs.getLong("id_categoria")
                );
                lista.add(m);
            }
        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao listar missões",
                    e
            );
        }

        return lista;
    }

    public Missao buscarPorId(Long id) {

        String sql =
                "SELECT * FROM MISSOES WHERE id = ?";

        try (Connection conn = DataBaseConfigs.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Missao m = new Missao();

                m.setId(rs.getLong("id"));
                m.setNome(rs.getString("nome"));
                Date data = rs.getDate("data_lancamento");

                if (data != null) {
                    m.setDataLancamento(
                            data.toLocalDate()
                    );
                }
                m.setDuracaoDias(
                        rs.getInt("duracao_dias")
                );
                m.setDescricao(
                        rs.getString("descricao")
                );
                m.setAgenciaId(
                        rs.getLong("id_agencia")
                );
                m.setStatusId(
                        rs.getLong("id_status")
                );
                m.setCategoriaId(
                        rs.getLong("id_categoria")
                );
                return m;
            }
        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao buscar missão",
                    e
            );
        }
        return null;
    }
    public void atualizar(Missao m) {

        String sql = """
                UPDATE MISSOES
                SET nome = ?,
                    data_lancamento = ?,
                    duracao_dias = ?,
                    descricao = ?,
                    id_agencia = ?,
                    id_status = ?,
                    id_categoria = ?
                WHERE id = ?
                """;

        try (Connection conn = DataBaseConfigs.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, m.getNome());
            stmt.setDate(
                    2,
                    Date.valueOf(m.getDataLancamento())
            );
            stmt.setInt(3, m.getDuracaoDias());
            stmt.setString(
                    4,
                    m.getDescricao()
            );
            stmt.setLong(
                    5,
                    m.getAgenciaId()
            );
            stmt.setLong(
                    6,
                    m.getStatusId()
            );
            stmt.setLong(
                    7,
                    m.getCategoriaId()
            );
            stmt.setLong(
                    8,
                    m.getId()
            );

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao atualizar missão",
                    e
            );
        }
    }
    public void deletar(Long id) {

        String sql =
                "DELETE FROM MISSOES WHERE id = ?";

        try (Connection conn = DataBaseConfigs.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao deletar missão",
                    e
            );
        }
    }
}