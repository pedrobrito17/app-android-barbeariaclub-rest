package com.pbtec.app.barbeariaclub.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.pbtec.app.barbeariaclub.R;
import com.pbtec.app.barbeariaclub.entidades.Cliente;
import com.pbtec.app.barbeariaclub.threds.ThreadAtualizarPerfil;

public class MudarSenha extends DialogFragment {
    private final String MY_PREF = "PreferênciasApp";
    private EditText nova_senha;
    private EditText conf_nova_senha;
    private View view;
    private Cliente cliente;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        view = layoutInflater.inflate(R.layout.content_mudar_senha,null);
        dialog.setView(view);

        cliente = getClinteByBundle();

        nova_senha = (EditText) view.findViewById(R.id.mudar_senha);
        conf_nova_senha = (EditText)view.findViewById(R.id.conf_mudar_senha);

        dialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String n1 = nova_senha.getText().toString();
                String n2 = conf_nova_senha.getText().toString();

                if (n1.length() >= 6 && n1.equals(n2)) {
                    cliente.setSenha(n1);

                    ThreadAtualizarPerfil thread = new ThreadAtualizarPerfil(getContext());
                    thread.execute(cliente);

                } else if(!n1.equals(n2)) {
                    Toast.makeText(getContext(),"Senha não confere", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getContext(),"Senha inválida", Toast.LENGTH_LONG).show();
                }

            }
        })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismiss();
                    }
                });
        return dialog.create();
    }

    public Cliente getClinteByBundle(){
        Cliente cliente = new Cliente();
        cliente.setNome(getArguments().getString("nome"));
        cliente.setEmail(getArguments().getString("email"));
        cliente.setDdd(getArguments().getInt("ddd"));
        cliente.setTelefone(getArguments().getString("telefone"));

        return cliente;
    }

}
