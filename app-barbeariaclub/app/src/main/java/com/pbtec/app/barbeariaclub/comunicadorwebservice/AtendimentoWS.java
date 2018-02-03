package com.pbtec.app.barbeariaclub.comunicadorwebservice;

import android.util.Log;

import com.pbtec.app.barbeariaclub.entidades.Atendimento;
import com.pbtec.app.barbeariaclub.entidades.Cliente;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by root on 12/08/16.
 */
public class AtendimentoWS {

    private final String SERVER = "http://node159376-envpb.jelasticlw.com.br:8080/restbarbearia/"+
            "webresources/";

    public boolean insertAtendimento(Atendimento atendimento){
        return false;
    }

    public List<Atendimento> getAtendimentosAgendadosDoCliente(String email_cliente){
        final String resource = SERVER+"wsatendimento/atendimentos/cliente/"+email_cliente;
        String json = "";
        Atendimento atendimento = null;
        List<Atendimento> atendimentos;

        try {
            URL url = new URL(resource);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String linha;
            StringBuffer sbuffer = new StringBuffer();
            while((linha = bufferedReader.readLine()) != null){
                sbuffer.append(linha);
                Log.i("JSON GETATENDIMENTOS", linha);
            }
            inputStream.close();
            json = sbuffer.toString();

            JSONArray jsonArray = new JSONArray(json);
            atendimentos = new ArrayList<>();
            for (int i = 0 ; i < jsonArray.length() ; i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                atendimento = new Atendimento();
                atendimento.setData_atendimento(jsonObject.getString("data_atendimento"));
                atendimento.setDesc_servico(jsonObject.getString("desc_serv"));
                atendimento.setEmail_cliente(jsonObject.getString("email_cliente"));
                atendimento.setEmail_funcionario(jsonObject.getString("email_func"));
                atendimento.setHora_atendimento(jsonObject.getString("hora_atendimento"));
                atendimentos.add(atendimento);
            }
            return atendimentos;

        } catch (Exception e) {
            Log.e("ERRO JSON ATENDIMENTO", e.toString());
        }

        return null;
    }

    public List<Atendimento> selectAtendimentoAgendados(){

        return null;
    }
    
    public List<String> selectHorasAgendadas(String email_funcionario, String data_atendimento){

        return null; 
    }

    public boolean deletarAtendimento(String data, String hora){

        return false; 
    }
}


