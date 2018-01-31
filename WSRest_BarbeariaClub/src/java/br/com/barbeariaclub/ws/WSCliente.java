package br.com.barbeariaclub.ws;

import br.com.barbeariaclub.dao.ClienteDAO;
import br.com.barbeariaclub.model.Cliente;
import java.sql.SQLException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author pedro
 */
@Path("wscliente")
public class WSCliente {
    
    @Path("/cliente")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertCliente(Cliente cliente) {        
        try {
            new ClienteDAO().insertCliente(cliente);
            return Response.status(Response.Status.CREATED).build();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
       
    @Path("/cliente/{email}/{senha}") //????
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Cliente getCliente(@PathParam("email") String email, 
            @PathParam("senha") String senha){
        
        Cliente cliente = null;
        
        try {
            cliente = new ClienteDAO().getCliente(email,senha);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return cliente;
    }
    
    @Path("/cliente")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCliente(Cliente cliente){
        try {
            new ClienteDAO().updateCliente(cliente);
            return Response.status(Response.Status.CREATED).build();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /*
    * Método para teste
    */
    @Path("/teste/{teste}")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String teste(@PathParam("teste") String teste){
        return teste;
    }
    
}
