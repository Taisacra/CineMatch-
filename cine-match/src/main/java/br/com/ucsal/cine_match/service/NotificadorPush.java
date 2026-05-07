package br.com.ucsal.cine_match.service;

import br.com.ucsal.cine_match.model.Usuario;

public interface NotificadorPush {
	
	void enviar(Usuario user, String mensagem);
}
