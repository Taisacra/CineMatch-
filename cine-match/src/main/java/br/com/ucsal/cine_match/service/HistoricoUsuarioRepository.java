package br.com.ucsal.cine_match.service;

import java.util.List;
import java.util.Map;

import br.com.ucsal.cine_match.model.Filme;
import br.com.ucsal.cine_match.model.Recomendacao;
import br.com.ucsal.cine_match.model.Usuario;

public interface HistoricoUsuarioRepository {
	
	void registrarRecomendacao(Usuario user, List<Recomendacao> recomendacoes);
	
	List<Filme> buscarHistorico(Usuario user);

	Map<Filme, Integer> buscarNotas(Usuario user);
}
