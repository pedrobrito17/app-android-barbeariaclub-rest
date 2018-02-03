package com.pbtec.app.barbeariaclub.interfaces;

import com.pbtec.app.barbeariaclub.entidades.Cliente;

/**
 * Created by root on 14/08/16.
 */
public interface OnLoginCompletedListener {

    void onLoginCompleted(Cliente result);
    public boolean verificarInternet();
}
