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

            agencia.setId(id);
            dao.atualizar(agencia);
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