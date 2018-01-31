package br.com.barbeariaclub.dao;

import br.com.barbeariaclub.util.ConnectionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import br.com.barbeariaclub.model.Cliente;

/**
 *
 * @author pedro-brito
 */
public class ClienteDAO {
    
    private Cliente cliente = null;
    private Connection conexaoBD = null;
    
    public void insertCliente(Cliente cliente) throws SQLException{
        String insertSQL = "INSERT INTO Cliente(nome,email,ddd,telefone,senha) "
                + "VALUES(?,?,?,?,?)";
        
        String email_repetido = "SELECT * FROM Cliente WHERE email=?";

        conexaoBD = new ConnectionBD().conectarBD();
        if(conexaoBD!=null){
            PreparedStatement prd = conexaoBD.prepareStatement(email_repetido);
            prd.setString(1, cliente.getEmail());
            ResultSet result = prd.executeQuery();
            if(result.next()){
                throw new SQLException("Erro no SQL: E-mail já cadastrado");
            }else{              
                prd = conexaoBD.prepareStatement(insertSQL);
                prd.setString(1, cliente.getNome());
                prd.setString(2, cliente.getEmail());
                prd.setInt(3, cliente.getDdd());
                prd.setString(4, cliente.getTelefone());
                prd.setString(5, cliente.getSenha());
                prd.execute();  
            }
            conexaoBD.close();
        }else{
            throw new SQLException("Conexão não pôde ser feita com o banco de dados");
        }
    }
    
    public Cliente getCliente(String email, String senha) throws SQLException{
        String selectSQL = "select * from Cliente where email=? and senha=?";
        conexaoBD = new ConnectionBD().conectarBD();
        if(conexaoBD!=null){
            PreparedStatement prd = conexaoBD.prepareStatement(selectSQL);            
            prd.setString(1, email);
            prd.setString(2, senha);
            ResultSet result = prd.executeQuery();
            if(result.next()){
                cliente = new Cliente();
                cliente.setNome(result.getString("nome"));
                cliente.setEmail(result.getString("email"));
                cliente.setDdd(result.getInt("ddd"));
                cliente.setTelefone(result.getString("telefone"));
                cliente.setSenha(result.getString("senha"));
            } 
            conexaoBD.close();
        }else{
            throw new SQLException("Conexão não pôde ser feita com o banco de dados");
        }
        return cliente;
    }
    
    public void updateCliente(Cliente cliente) throws SQLException{
        String sql = "UPDATE Cliente set senha=? , ddd=? , telefone=? WHERE email=?";
        
        conexaoBD = new ConnectionBD().conectarBD();
        if(conexaoBD!=null){
            PreparedStatement prd = conexaoBD.prepareStatement(sql);
            prd.setString(1,cliente.getSenha());
            prd.setInt(2,cliente.getDdd());
            prd.setString(3,cliente.getTelefone());
            prd.setString(4,cliente.getEmail());
            prd.executeUpdate();
            conexaoBD.close();
        }else{
            throw new SQLException("Conexão não pôde ser feita com o banco de dados");
        }
    }

}
