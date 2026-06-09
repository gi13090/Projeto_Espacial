package resources;

import dao.UsuarioDAO;
import entities.Usuario;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {


    private final UsuarioDAO dao = new UsuarioDAO();
    @GET
    public Response listar() {

        List<Usuario> usuarios = dao.listar();
        return Response.ok(usuarios).build();
    }
    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {

        Usuario usuario = dao.buscarPorId(id);
        if (usuario == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Usuário não encontrado")
                    .build();
        }
        return Response.ok(usuario).build();
    }
    @POST
    public Response inserir(Usuario usuario) {
        if (usuario.getNome() == null ||
                usuario.getNome().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Nome é obrigatório")
                    .build();
        }
        if (usuario.getEmail() == null ||
                usuario.getEmail().isBlank()) {

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Email é obrigatório")
                    .build();
        }
        if (dao.emailExiste(usuario.getEmail())) {

            return Response.status(Response.Status.CONFLICT)
                    .entity("Email já cadastrado")
                    .build();
        }
        if (!usuario.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Email inválido")
                    .build();
        }
        if (usuario.getSenha() == null ||
                usuario.getSenha().length() < 6) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Senha deve possuir no mínimo 6 caracteres")
                    .build();
        }
        try {
            usuario.setId(dao.proximoId());
            dao.inserir(usuario);
            return Response.status(Response.Status.CREATED)
                    .entity(usuario)
                    .build();

        } catch (Exception e) {

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao cadastrar usuário")
                    .build();
        }

    }
    @POST
    @Path("/login")
    public Response login(Usuario usuario) {

        if (usuario.getEmail() == null || usuario.getSenha() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Email e senha são obrigatórios")
                    .build();
        }

        if (dao.login(usuario.getEmail(), usuario.getSenha())) {
            return Response.ok("Login realizado com sucesso").build();
        }

        return Response.status(Response.Status.UNAUTHORIZED)
                .entity("Email ou senha inválidos")
                .build();
    }
    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, Usuario usuario) {
        try {

            usuario.setId(id);
            if (dao.buscarPorId(id) == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Usuário não encontrado")
                        .build();
            }

            return Response.ok(usuario).build();

        } catch (Exception e) {

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao atualizar usuário")
                    .build();
        }
    }
    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) {
        try {
            dao.deletar(id);

            return Response.noContent().build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao deletar usuário")
                    .build();
        }
    }
}