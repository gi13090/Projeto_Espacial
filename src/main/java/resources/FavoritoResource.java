package resources;
import dao.FavoritoDAO;
import dao.MissaoDAO;
import dao.UsuarioDAO;
import entities.Favorito;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/favoritos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FavoritoResource {
    private final FavoritoDAO dao = new FavoritoDAO();
    @GET
    public Response listar() {
        List<Favorito> favoritos = dao.listar();
        return Response.ok(favoritos).build();
    }
    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        Favorito favorito = dao.buscarPorId(id);
        if (favorito == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Favorito não encontrado")
                    .build();
        }
        return Response.ok(favorito).build();
    }
    @POST
    public Response inserir(Favorito favorito) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        if (usuarioDAO.buscarPorId(
                favorito.getUsuarioId()) == null) {

            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Usuário não encontrado")
                    .build();
        }
        MissaoDAO missaoDAO = new MissaoDAO();

        if (missaoDAO.buscarPorId(
                favorito.getMissaoId()) == null) {

            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Missão não encontrada")
                    .build();
        }

        if (dao.favoritoExiste(
                favorito.getUsuarioId(),
                favorito.getMissaoId())) {

            return Response.status(Response.Status.CONFLICT)
                    .entity("Missão já favoritada")
                    .build();
        }
        try {
            dao.inserir(favorito);
            return Response.status(Response.Status.CREATED)
                    .entity(favorito)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao cadastrar favorito")
                    .build();
        }
    }
    @DELETE
    @Path("/{id}")
    public Response excluir(@PathParam("id") Long id) {
        try {
            dao.deletar(id);

            return Response.noContent().build();

        } catch (Exception e) {

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao excluir favorito")
                    .build();
        }
    }
}