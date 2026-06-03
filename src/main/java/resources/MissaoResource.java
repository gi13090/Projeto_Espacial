package resources;

import dao.MissaoDAO;
import entities.Missao;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/missoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MissaoResource {

    private final MissaoDAO dao = new MissaoDAO();

    @GET
    public Response listar() {

        List<Missao> missoes = dao.listar();

        return Response.ok(missoes).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {

        Missao missao = dao.buscarPorId(id);

        if (missao == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Missão não encontrada")
                    .build();
        }

        return Response.ok(missao).build();
    }

    @POST
    public Response inserir(Missao missao) {

        if (missao.getNome() == null ||
                missao.getNome().isBlank()) {

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Nome da missão obrigatório")
                    .build();
        }
        if (missao.getDataLancamento() == null) {

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Data de lançamento obrigatória")
                    .build();
        }
        if (missao.getDuracaoDias() == null ||
                missao.getDuracaoDias() < 0){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Duração inválida")
                    .build();
        }
        if (dao.missaoExiste(missao.getId())) {

            return Response.status(Response.Status.CONFLICT)
                    .entity("Missão já cadastrada")
                    .build();
        }
        try {
            dao.inserir(missao);
            return Response.status(Response.Status.CREATED)
                    .entity(missao)
                    .build();

        } catch (Exception e) {

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao cadastrar missão")
                    .build();
        }
    }
    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id,Missao missao) {
        try {

            missao.setId(id);
            dao.atualizar(missao);
            return Response.ok(missao).build();

        } catch (Exception e) {

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao atualizar missão")
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
                    .entity("Erro ao excluir missão")
                    .build();
        }
    }
}