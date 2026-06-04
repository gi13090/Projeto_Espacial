package dao;

import entities.Usuario;
import infra.DataBaseConfigs;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public void inserir(Usuario u) {

        String sql = "INSERT INTO T_USUARIO (ID_USUARIO, NM_USUARIO, DS_EMAIL, DS_SENHA) VALUES (?, ?, ?, ?)";

        try (Connection conn = DataBaseConfigs.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, u.getId());
            stmt.setString(2, u.getNome());
            stmt.setString(3, u.getEmail());
            stmt.setString(4, u.getSenha());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir usuário", e);
        }
    }

    public List<Usuario> listar() {

        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM T_USUARIO";
        try (Connection conn = DataBaseConfigs.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Usuario u = new Usuario();
                u.setId(rs.getLong("ID"));
                u.setNome(rs.getString("NM_USUARIO"));
                u.setEmail(rs.getString("DS_EMAIL"));
                u.setSenha(rs.getString("DS_SENHA"));

                lista.add(u);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar usuários", e);
        }
        return lista;
    }
    public Usuario buscarPorId(Long id) {
        String sql = "SELECT * FROM T_USUARIO WHERE ID_USUARIO = ?";

        try (Connection conn = DataBaseConfigs.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Usuario u = new Usuario();
                u.setId(rs.getLong("ID_USUARIO"));
                u.setNome(rs.getString("NM_USUARIO"));
                u.setEmail(rs.getString("DS_EMAIL"));
                u.setSenha(rs.getString("DS_SENHA"));

                return u;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário", e);
        }
        return null;
    }
    public void atualizar(Usuario u) {

        String sql = """
                UPDATE T_USUARIO
                SET NM_USUARIO=?, DS_EMAIL=?, DS_SENHA=?
                WHERE ID_USUARIO=?
                """;

        try (Connection conn = DataBaseConfigs.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, u.getNome());
            stmt.setString(2, u.getEmail());
            stmt.setString(3, u.getSenha());
            stmt.setLong(4, u.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar usuário", e);
        }
    }
    public void deletar(Long id) {

        String sql = "DELETE FROM T_USUARIO WHERE ID_USUARIO=?";

        try (Connection conn = DataBaseConfigs.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar usuário", e);
        }
    }
    public boolean emailExiste(String email) {

        String sql = "SELECT COUNT(*) FROM T_USUARIO WHERE DS_EMAIL = ?";

        try (Connection conn = DataBaseConfigs.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar email", e);
        }

        return false;
    }
    public boolean usuarioExiste(Long id) {

        String sql = "SELECT COUNT(*) FROM T_USUARIO WHERE ID_USUARIO = ?";

        try (Connection conn = DataBaseConfigs.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar usuário", e);
        }
        return false;
    }
}