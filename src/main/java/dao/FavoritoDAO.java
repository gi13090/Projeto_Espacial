package dao;

import entities.Favorito;
import infra.DataBaseConfigs;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FavoritoDAO {
    public void inserir(Favorito f) {

        String sql = """
                INSERT INTO FAVORITOS
                (ID, ID_USUARIO, ID_MISSAO)
                VALUES (?, ?, ?)
                """;

        try (Connection conn = DataBaseConfigs.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, f.getId());
            stmt.setLong(2, f.getUsuarioId());
            stmt.setLong(3, f.getMissaoId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir favorito", e);
        }
    }
    public List<Favorito> listar() {

        List<Favorito> lista = new ArrayList<>();

        String sql = "SELECT * FROM FAVORITOS";

        try (Connection conn = DataBaseConfigs.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Favorito f = new Favorito();

                f.setId(rs.getLong("ID"));
                f.setUsuarioId(rs.getLong("ID_USUARIO"));
                f.setMissaoId(rs.getLong("ID_MISSAO"));

                lista.add(f);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar favoritos", e);
        }
        return lista;
    }
    public Favorito buscarPorId(Long id) {

        String sql = "SELECT * FROM FAVORITOS WHERE ID=?";

        try (Connection conn = DataBaseConfigs.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Favorito f = new Favorito();
                f.setId(rs.getLong("ID"));
                f.setUsuarioId(rs.getLong("ID_USUARIO"));
                f.setMissaoId(rs.getLong("ID_MISSAO"));

                return f;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar favorito", e);
        }
        return null;
    }
    public void deletar(Long id) {

        String sql = "DELETE FROM FAVORITOS WHERE ID=?";
        try (Connection conn = DataBaseConfigs.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar favorito", e);
        }
    }
    public boolean favoritoExiste(Long usuarioId,
                                  Long missaoId) {

        String sql = """
            SELECT COUNT(*)
            FROM FAVORITOS
            WHERE ID_USUARIO = ?
            AND ID_MISSAO = ?
            """;

        try (Connection conn = DataBaseConfigs.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, usuarioId);
            stmt.setLong(2, missaoId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar favorito", e);
        }
        return false;
    }
}