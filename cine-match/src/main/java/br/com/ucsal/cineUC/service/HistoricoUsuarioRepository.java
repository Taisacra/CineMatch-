package br.com.ucsal.cineUC.service;

import java.util.List;
import java.util.Map;

import br.com.ucsal.cineUC.model.Filme;
import br.com.ucsal.cineUC.model.Recomendacao;
import br.com.ucsal.cineUC.model.Usuario;

public interface HistoricoUsuarioRepository {
	
	void registrarRecomendacao(Usuario user, List<Recomendacao> recomendacoes);
	
	List<Filme> buscarHistorico(Usuario user);

	Map<Filme, Integer> buscarNotas(Usuario user);
}
