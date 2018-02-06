package com.pbtec.app.barbeariaclub.comunicadorwebservice;

import android.util.Log;

import com.pbtec.app.barbeariaclub.entidades.Atendimento;
import com.pbtec.app.barbeariaclub.entidades.Cliente;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by root on 12/08/16.
 */
public class AtendimentoWS {

    private final String SERVER = "http://node160005-envpb.jelasticlw.com.br:8080/wsrest/"+
            "webresources/";

    public boolean insertAtendimento(Atendimento atendimento){
        final String resource = SERVER+"wsatendimento/atendimento";

        try {
            URL url = new URL(resource);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestProperty("Accept", "application/json");
            httpURLConnection.setRequestProperty("Content-type", "application/json");
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("email_cliente", atendimento.getEmail_cliente());
            jsonObject.accumulate("email_func", atendimento.getEmail_funcionario());
            jsonObject.accumulate("data_atendimento", atendimento.getData_atendimento());
            jsonObject.accumulate("hora_atendimento", atendimento.getHora_atendimento());
            jsonObject.accumulate("desc_serv", atendimento.getDesc_servico());

            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            bufferedWriter.write(jsonObject.toString());
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            httpURLConnection.connect();

            Log.i("RESPOSTA DO SERVIDOR", httpURLConnection.getResponseMessage());
            if(httpURLConnection.getResponseMessage().equals("Created"))
                return true;

        } catch (Exception e) {
            Log.e("ERRO INSERT ATENDIMENTO", e.toString());
        }

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
                atendimento.setId_atendimento(jsonObject.getInt("id_atendimento"));
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

    public boolean deletarAtendimento(String id){
        final String resource = SERVER+"wsatendimento/atendimento/"+id;

        try {
            URL url = new URL(resource);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("DELETE");
            httpURLConnection.connect();

            String response = httpURLConnection.getResponseMessage();
            if(response.equals("Ok"))
                return true;

        } catch (Exception e) {
            Log.e("ERRO DELETE ATENDIMENTO", e.toString());
        }
        return false; 
    }

    public List<String> getHorariosIndisponiveis(String email_funcionario, String data){

        String dia = String.valueOf(data.charAt(0))+String.valueOf(data.charAt(1));
        String mes = String.valueOf(data.charAt(3))+String.valueOf(data.charAt(4));
        String ano = data.substring(6,10);

        List<String> lista = new ArrayList<>();

        final String resource = SERVER+"wsatendimento/horarioindisponivel/"+email_funcionario+"/"+
                dia+"/"+mes+"/"+ano;

        try {
            URL url = new URL(resource);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String linha = "";
            StringBuffer stringBuffer = new StringBuffer();
            while((linha = bufferedReader.readLine()) != null){
                stringBuffer.append(linha);
            }

            JSONArray jsonArray = new JSONArray(stringBuffer.toString());
            for(int i = 0 ; i < jsonArray.length() ; i++){
                lista.add(jsonArray.get(i).toString());
            }

            inputStream.close();
            return lista;

        } catch (Exception e) {
            Log.e("ERRO HORARIOS JSON", e.toString());
        }

        return lista;
    }
}


