package br.com.barbeariaclub.ws;

import br.com.barbeariaclub.dao.AtendimentoDAO;
import br.com.barbeariaclub.model.Atendimento;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author pedro
 */
@Path("/wsatendimento")
public class WSAtendimento {
    
    //CONCLUIDO///////
    @Path("/atendimentos/cliente/{email_cliente}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Atendimento> getAtendimentosByEmailCliente(
            @PathParam("email_cliente") String email_cliente) {
        
        List<Atendimento> lista = null;
        try {
            lista = new AtendimentoDAO().getAtendimentosByEmailCliente(email_cliente);
        } catch (SQLException ex) {
            Logger.getLogger(WSAtendimento.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;        
    }
    
    //CONCLUÍDO////////////////
    @Path("/atendimento")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertAtendimento(Atendimento atendimento){
        try {
            new AtendimentoDAO().insertAtendimento(atendimento);
            return Response.status(Response.Status.CREATED).build();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    //CONCLUÍDO////////////
    @Path("/horarioindisponivel/{emailfunc}/{dia}/{mes}/{ano}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getHorasAgendadas(@PathParam("emailfunc") 
            String email_funcionario, @PathParam("dia") String dia, @PathParam("mes") 
                String mes, @PathParam("ano") String ano) {
        
        List<String> horarioIndisponivel = null;
        try {
            horarioIndisponivel =  new AtendimentoDAO().getHorariosIndisponiveis
                (email_funcionario, dia+"/"+mes+"/"+ano);
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return horarioIndisponivel;
    }
    
    //CONCLUÍDO////////////////////
    @Path("/atendimento/{id}")
    @DELETE
    public Response deleteAtendimento(@PathParam("id") int id) {
        
        try {
            new AtendimentoDAO().deleteAtendimento(id);

            return Response.status(Response.Status.OK).build();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    
}
