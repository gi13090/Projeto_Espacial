package dao;

import entities.Agencia;
import infra.DataBaseConfigs;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgenciaDAO {

    public void inserir(Agencia a) {

        String sql = """
                INSERT INTO T_AGENCIA
                (
                    ID_AGENCIA,
                    NM_AGENCIA,
                    DS_PAIS,
                    NR_ANO_FUNDACAO,
                    DS_SITE
                )
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection conn = DataBaseConfigs.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, a.getId());
            stmt.setString(2, a.getNome());
            stmt.setString(3, a.getPais());
            stmt.setInt(4, a.getAnoFundacao());
            stmt.setString(5, a.getSite());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir agência", e);
        }
    }
    public List<Agencia> listar() {

        List<Agencia> lista = new ArrayList<>();

        String sql = "SELECT * FROM T_AGENCIA";
        try (Connection conn = DataBaseConfigs.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Agencia a = new Agencia();
                a.setId(rs.getLong("ID_AGENCIA"));
                a.setNome(rs.getString("NM_AGENCIA"));
                a.setPais(rs.getString("DS_PAIS"));
                a.setAnoFundacao(rs.getInt("NR_ANO_FUNDACAO"));
                a.setSite(rs.getString("DS_SITE"));

                lista.add(a);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar agências", e);
        }
        return lista;
    }
    public Agencia buscarPorId(Long id) {

        String sql =
                "SELECT * FROM T_AGENCIA WHERE ID_AGENCIA = ?";

        try (Connection conn = DataBaseConfigs.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Agencia a = new Agencia();
                a.setId(rs.getLong("ID_AGENCIA"));
                a.setNome(rs.getString("NM_AGENCIA"));
                a.setPais(rs.getString("DS_PAIS"));
                a.setAnoFundacao(rs.getInt("NR_ANO_FUNDACAO"));
                a.setSite(rs.getString("DS_SITE"));

                return a;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar agência", e);
        }
        return null;
    }

    public void atualizar(Agencia a) {

        String sql = """
                UPDATE T_AGENCIA
                SET
                    NM_AGENCIA = ?,
                    DS_PAIS = ?,
                    NR_ANO_FUNDACAO = ?,
                    DS_SITE = ?
                WHERE ID_AGENCIA = ?
                """;

        try (Connection conn = DataBaseConfigs.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, a.getNome());
            stmt.setString(2, a.getPais());
            stmt.setInt(3, a.getAnoFundacao());
            stmt.setString(4, a.getSite());
            stmt.setLong(5, a.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar agência", e);
        }
    }
    public void deletar(Long id) {

        String sql =
                "DELETE FROM T_AGENCIA WHERE ID_AGENCIA = ?";

        try (Connection conn = DataBaseConfigs.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar agência", e);
        }
    }
    public boolean agenciaExiste(Long id) {

        String sql =
                "SELECT COUNT(*) FROM T_AGENCIA WHERE ID_AGENCIA = ?";

        try (Connection conn = DataBaseConfigs.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar agência", e);
        }
        return false;
    }
}