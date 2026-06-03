package dao;

import entities.Missao;
import infra.DataBaseConfigs;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MissaoDAO {

    public void inserir(Missao m) {

        String sql = """
                INSERT INTO T_MISSAO
                (
                    ID_MISSAO,
                    NM_MISSAO,
                    DS_MISSAO,
                    DT_LANCAMENTO,
                    NR_DURACAO_DIAS,
                    DS_STATUS,
                    DS_DESTINO,
                    ID_AGENCIA
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = DataBaseConfigs.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, m.getId());
            stmt.setString(2, m.getNome());
            stmt.setString(3, m.getDescricao());
            stmt.setDate(4, Date.valueOf(m.getDataLancamento()));
            stmt.setInt(5, m.getDuracaoDias());
            stmt.setString(6, m.getStatus());
            stmt.setString(7, m.getDestino());
            stmt.setLong(8, m.getAgenciaId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir missão", e);
        }
    }

    public List<Missao> listar() {

        List<Missao> lista = new ArrayList<>();

        String sql = "SELECT * FROM T_MISSAO";

        try (Connection conn = DataBaseConfigs.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Missao m = new Missao();

                m.setId(rs.getLong("ID_MISSAO"));
                m.setNome(rs.getString("NM_MISSAO"));
                m.setDescricao(rs.getString("DS_MISSAO"));

                Date data = rs.getDate("DT_LANCAMENTO");

                if (data != null) {
                    m.setDataLancamento(data.toLocalDate());
                }
                m.setDuracaoDias(rs.getInt("NR_DURACAO_DIAS"));
                m.setStatus(rs.getString("DS_STATUS"));
                m.setDestino(rs.getString("DS_DESTINO"));
                m.setAgenciaId(rs.getLong("ID_AGENCIA"));

                lista.add(m);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar missões", e);
        }
        return lista;
    }

    public Missao buscarPorId(Long id) {

        String sql =
                "SELECT * FROM T_MISSAO WHERE ID_MISSAO = ?";

        try (Connection conn = DataBaseConfigs.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Missao m = new Missao();

                m.setId(rs.getLong("ID_MISSAO"));
                m.setNome(rs.getString("NM_MISSAO"));
                m.setDescricao(rs.getString("DS_MISSAO"));

                Date data = rs.getDate("DT_LANCAMENTO");

                if (data != null) {
                    m.setDataLancamento(data.toLocalDate());
                }

                m.setDuracaoDias(rs.getInt("NR_DURACAO_DIAS"));
                m.setStatus(rs.getString("DS_STATUS"));
                m.setDestino(rs.getString("DS_DESTINO"));
                m.setAgenciaId(rs.getLong("ID_AGENCIA"));

                return m;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar missão", e);
        }
        return null;
    }
    public void atualizar(Missao m) {

        String sql = """
                UPDATE T_MISSAO
                SET
                    NM_MISSAO = ?,
                    DS_MISSAO = ?,
                    DT_LANCAMENTO = ?,
                    NR_DURACAO_DIAS = ?,
                    DS_STATUS = ?,
                    DS_DESTINO = ?,
                    ID_AGENCIA = ?
                WHERE ID_MISSAO = ?
                """;

        try (Connection conn = DataBaseConfigs.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, m.getNome());
            stmt.setString(2, m.getDescricao());
            stmt.setDate(3, Date.valueOf(m.getDataLancamento()));
            stmt.setInt(4, m.getDuracaoDias());
            stmt.setString(5, m.getStatus());
            stmt.setString(6, m.getDestino());
            stmt.setLong(7, m.getAgenciaId());
            stmt.setLong(8, m.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar missão", e);
        }
    }

    public void deletar(Long id) {

        String sql =
                "DELETE FROM T_MISSAO WHERE ID_MISSAO = ?";

        try (Connection conn = DataBaseConfigs.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar missão", e);
        }
    }

    public boolean missaoExiste(Long id) {

        String sql =
                "SELECT COUNT(*) FROM T_MISSAO WHERE ID_MISSAO = ?";

        try (Connection conn = DataBaseConfigs.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar missão", e);
        }
        return false;
    }
}