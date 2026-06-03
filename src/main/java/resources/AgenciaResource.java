package resources;

import dao.AgenciaDAO;
import entities.Agencia;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/agencias")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AgenciaResource {
    private final AgenciaDAO dao = new AgenciaDAO();
    @GET
    public Response listar() {
        List<Agencia> agencias = dao.listar();
        return Response.ok(agencias).build();
    }
    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        Agencia agencia = dao.buscarPorId(id);
        if (agencia == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Agência não encontrada")
                    .build();
        }
        return Response.ok(agencia).build();
    }
    @POST
    public Response inserir(Agencia agencia) {
        try {
            if (agencia.getNome() == null ||
                    agencia.getNome().isBlank()) {

                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Nome da agência é obrigatório")
                        .build();
            }
            if (agencia.getPais() == null ||
                    agencia.getPais().isBlank()) {

                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("País é obrigatório")
                        .build();
            }
            if (agencia.getAnoFundacao() == null ||
                    agencia.getAnoFundacao() < 1900) {

                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Ano de fundação inválido")
                        .build();
            }
            if (agencia.getSite() == null ||
                    agencia.getSite().isBlank()) {

                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Site é obrigatório")
                        .build();
            }
            dao.inserir(agencia);
            return Response.status(Response.Status.CREATED)
                    .entity(agencia)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao cadastrar agência")
                    .build();
        }
    }
    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, Agencia agencia) {
        try {
            if (dao.buscarPorId(id) == null) {

                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Agência não encontrada")
                        .build();
            }            dao.atualizar(agencia);
            return Response.ok(agencia).build();

        } catch (Exception e) {

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao atualizar agência")
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
                    .entity("Erro ao excluir agência")
                    .build();
        }
    }
}