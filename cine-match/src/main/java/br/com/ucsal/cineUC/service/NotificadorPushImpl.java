package br.com.ucsal.cineUC.service;

import br.com.ucsal.cineUC.model.Usuario;

public class NotificadorPushImpl implements NotificadorPush {

    @Override
    public void enviar(Usuario user, String mensagem) {
        System.out.println(mensagem);
    }
}
