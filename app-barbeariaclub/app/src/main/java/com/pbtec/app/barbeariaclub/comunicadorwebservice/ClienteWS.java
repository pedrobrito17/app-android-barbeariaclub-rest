package com.pbtec.app.barbeariaclub.comunicadorwebservice;

import android.util.Log;
import com.pbtec.app.barbeariaclub.entidades.Cliente;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ClienteWS {

    public Cliente getClienteLogin(String email, String senha){
        String resource = "http://node159191-envts.jelasticlw.com.br:8080/restbarbearia/"+
                "webresources/wscliente/cliente/"+email+"/"+senha;

        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;
        String json = "";
        Cliente cliente = null;

        try {
            /* Classe responsável por criar um túnel de comunicação com a URL do recurso */
            URL url = new URL(resource);

            /* Retorna uma conexão com a URL(URLConncetion), porém estamos trabalhando com
             * um webservice REST, então precisamos obter o retorno HTTPURLConnection.
             * Na ultima linha é estabelecido a conexão com o recurso.*/
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            /* Retorna um fluxo de bytes a partir da conexão aberta */
            InputStream inputStream = httpURLConnection.getInputStream();
            /* InputStreamReader retorna um fluxo de caracteres a partir de um fluxo de bytes
            *  BufferedReader faz a leitura do fluxo de caracteres utilizando buffer*/
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String linha;
            StringBuffer stringBuffer = new StringBuffer();
            while((linha = bufferedReader.readLine()) != null){
                stringBuffer.append(linha);
            }

            inputStream.close();
            json = stringBuffer.toString();
            Log.i("JSON RECEBIDO",json);

        } catch (Exception e) {
            Log.e("Erro no JSON","erro ao ler o json: "+e.toString());
        }

        try {
            JSONObject object = new JSONObject(json);
            cliente = new Cliente();
            cliente.setNome(object.getString("nome"));
            cliente.setEmail(object.getString("email"));
            cliente.setDdd(object.getInt("ddd"));
            cliente.setTelefone(object.getString("telefone"));
            cliente.setSenha(object.getString("senha"));
        } catch (JSONException e) {
            Log.e("Erro no JSON","erro ao converter para JSONObject: "+e.toString());
        }

        return cliente;
    }

}
