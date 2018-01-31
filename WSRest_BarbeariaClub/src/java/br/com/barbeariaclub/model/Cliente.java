package br.com.barbeariaclub.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author pedro-brito
 */
@XmlRootElement
public class Cliente implements Serializable{
    
    private String nome;
    private String email;
    private int ddd;
    private String telefone;
    private String senha;
    
    public Cliente(){
        this.nome ="";
        this.email = "";
        this.ddd = 0;
        this.telefone = "";
        this.senha = "";
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public int getDdd() {
        return ddd;
    }

    public void setDdd(int ddd) {
        this.ddd = ddd;
    }
    
    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
