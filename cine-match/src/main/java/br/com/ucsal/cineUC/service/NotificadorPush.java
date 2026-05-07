package br.com.ucsal.cineUC.service;

import br.com.ucsal.cineUC.model.Usuario;

public interface NotificadorPush {
	
	void enviar(Usuario user, String mensagem);
}
