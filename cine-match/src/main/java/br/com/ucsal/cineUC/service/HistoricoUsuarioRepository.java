package br.com.ucsal.cineUC.service;

import java.util.List;
import java.util.Map;

import br.com.ucsal.cineUC.model.Filme;
import br.com.ucsal.cineUC.model.Recomendacao;
import br.com.ucsal.cineUC.model.Usuario;

public interface HistoricoUsuarioRepository {
	
	void registrarRecomendacao(Usuario usuario, List<Recomendacao> recomendacoes);
	
	List<Filme> buscarHistorico(Usuario usuario);

	Map<Filme, Integer> buscarNotas(Usuario usuario);
}
