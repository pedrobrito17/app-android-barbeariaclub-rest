package com.pbtec.app.barbeariaclub.comunicadorwebservice;

import android.util.Log;
import com.pbtec.app.barbeariaclub.entidades.Cliente;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ClienteWS {

    private final String SERVER = "http://node159376-envpb.jelasticlw.com.br:8080/restbarbearia/"+
            "webresources/";

    public Cliente getClienteLogin(String email, String senha){
        final String resource = SERVER+"wscliente/cliente/"+email+"/"+senha;

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

            JSONObject object = new JSONObject(json);
            cliente = new Cliente();
            cliente.setNome(object.getString("nome"));
            cliente.setEmail(object.getString("email"));
            cliente.setDdd(object.getInt("ddd"));
            cliente.setTelefone(object.getString("telefone"));
            cliente.setSenha(object.getString("senha"));
        } catch (Exception e) {
            Log.e("Erro JSON Login","erro ao converter para JSONObject: "+e.toString());
        }

        return cliente;
    }

    public String insertNovoCliente(Cliente cliente){
        final String resource = SERVER+"wscliente/cliente/";
        String response = "";

        try {
            /* Estabelece a conexão com o recurso */
            URL url = new URL(resource);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestProperty("Accept", "application/json");
            httpURLConnection.setRequestProperty("Content-type", "application/json");
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            /* Cria o objeto json */
            JSONObject object = new JSONObject();
            object.accumulate("nome", cliente.getNome());
            object.accumulate("email", cliente.getEmail());
            object.accumulate("ddd", cliente.getDdd());
            object.accumulate("telefone", cliente.getTelefone());
            object.accumulate("senha", cliente.getSenha());

            /* Faz a escrita do objeto json na url do recurso */
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            bufferedWriter.write(object.toString());
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            httpURLConnection.connect();
            response = httpURLConnection.getResponseMessage();
            Log.i("JSON NOVO CLIENTE", "Resposta HTTP: "+response);
            if(response.equals("Created"))
                return "Cliente cadastrado com sucesso!";

        } catch (Exception e) {
            Log.e("ERRO JSON NOVO CLIENTE", e.toString());
        }

        return "Erro no cadastro";
    }

    public String updateClienteCadastrado(Cliente cliente){
        final String resource = SERVER+"wscliente/cliente/";
        String response = "";

        try {
            /* Estabelece a conexão com o recurso */
            URL url = new URL(resource);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestProperty("Accept", "application/json");
            httpURLConnection.setRequestProperty("Content-type", "application/json");
            httpURLConnection.setRequestMethod("PUT");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            /* Cria o objeto json */
            JSONObject object = new JSONObject();
            object.accumulate("nome", cliente.getNome());
            object.accumulate("email", cliente.getEmail());
            object.accumulate("ddd", cliente.getDdd());
            object.accumulate("telefone", cliente.getTelefone());
            object.accumulate("senha", cliente.getSenha());

            /* Faz a escrita do objeto json na url do recurso */
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            bufferedWriter.write(object.toString());
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            httpURLConnection.connect();
            response = httpURLConnection.getResponseMessage();
            Log.i("JSON UPDATE CLIENTE", "Resposta HTTP: "+response);
            if(response.equals("Created"))
                return "Cadastro atualizado";

        } catch (Exception e) {
            Log.e("ERRO UPDATE CLIENTE", e.toString());
        }

        return "Erro na atualização";
    }

}
