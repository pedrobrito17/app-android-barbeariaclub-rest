package br.com.barbeariaclub.dao;

import br.com.barbeariaclub.util.ConnectionBD;
import br.com.barbeariaclub.model.Atendimento;
import br.com.barbeariaclub.util.Formatador;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pedro-brito
 */
public class AtendimentoDAO {
    private Connection conexaoBD;
    private String comandoSQL;
    private PreparedStatement prd;
    private ResultSet resultSet;
    private List<Atendimento> lista;
    private List<String> horariosIndisponiveis;
    
    public void insertAtendimento(Atendimento atendimento) throws SQLException{
        comandoSQL = "INSERT INTO Atendimento(email_cliente,email_func,desc_serv,data_atendimento,hora_atendimento) VALUES (?,?,?,?,?)";
        
        conexaoBD = new ConnectionBD().conectarBD();
        if(conexaoBD!=null){
            prd = conexaoBD.prepareStatement(comandoSQL);
            prd.setString(1, atendimento.getEmail_cliente());
            prd.setString(2, atendimento.getEmail_func());
            prd.setString(3, atendimento.getDesc_serv());
            prd.setDate(4, new Formatador().getDateSQL(atendimento.getData_atendimento()));
            prd.setTime(5, new Formatador().getTime(atendimento.getHora_atendimento()));
            prd.execute();
            conexaoBD.close();
        }else{
            throw new SQLException("Conexão não pôde ser feita com o banco de dados");
        }
    }
    
    public List<String> getHorariosIndisponiveis(String email_funcionario, 
            String data_atendimento) throws SQLException{
        
        comandoSQL = "SELECT hora_atendimento FROM Atendimento WHERE data_atendimento=? "
                + "AND email_func=?";
        
        conexaoBD = new ConnectionBD().conectarBD();
        if(conexaoBD!=null){
            prd = conexaoBD.prepareStatement(comandoSQL);
            prd.setDate(1, new Formatador().getDateSQL(data_atendimento));
            prd.setString(2, email_funcionario);
            resultSet = prd.executeQuery();
            
            horariosIndisponiveis = new ArrayList();
            while(resultSet.next()){
                horariosIndisponiveis.add(new SimpleDateFormat("HH : mm").
                            format(resultSet.getTime("hora_atendimento")));
            }
            conexaoBD.close();
        }else{
            throw new SQLException("Conexão não pôde ser feita com o banco de dados");
        }
        return horariosIndisponiveis;
    }
    
    public void deleteAtendimento(int id) throws SQLException{
        comandoSQL = "DELETE FROM Atendimento WHERE id_atendimento=?";
        
        conexaoBD = new ConnectionBD().conectarBD();
        if(conexaoBD!=null){
            prd = conexaoBD.prepareStatement(comandoSQL);
            prd.setInt(1, id);
            prd.execute();
            conexaoBD.close();
        }else{
            throw new SQLException("Conexão não pôde ser feita com o banco de dados");
        }
    }

    public List<Atendimento> getAtendimentosByEmailCliente(String email_cliente) throws SQLException {
        comandoSQL = "SELECT * FROM Atendimento WHERE email_cliente=?";
        
        conexaoBD = new ConnectionBD().conectarBD();
        if(conexaoBD!=null){
            prd = conexaoBD.prepareStatement(comandoSQL);
            prd.setString(1, email_cliente);
            resultSet = prd.executeQuery();
            
            lista = new ArrayList();
            Atendimento atendimento;
            while(resultSet.next()){
                atendimento = new Atendimento();
                atendimento.setId_atendimento(resultSet.getInt("id_atendimento"));
                atendimento.setEmail_cliente(resultSet.getString("email_cliente"));
                Time time = new Time(resultSet.getTime("hora_atendimento").getTime());  
                atendimento.setHora_atendimento(time.toString());
                atendimento.setEmail_func(resultSet.getString("email_func"));
                atendimento.setDesc_serv(resultSet.getString("desc_serv"));
                atendimento.setData_atendimento(new Formatador().stringDate(resultSet.getDate("data_atendimento")));
                lista.add(atendimento);
            }
        }else{
            throw new SQLException("Conexão não pôde ser feita com o banco de dados");
        }
        conexaoBD.close();
        return lista;
    }
    
}
