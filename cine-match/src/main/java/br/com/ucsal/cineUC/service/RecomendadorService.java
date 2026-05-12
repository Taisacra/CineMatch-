package br.com.ucsal.cineUC.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import br.com.ucsal.cineUC.model.Filme;
import br.com.ucsal.cineUC.model.PerfilCinefilo;
import br.com.ucsal.cineUC.model.Recomendacao;
import br.com.ucsal.cineUC.model.Usuario;
import br.com.ucsal.cineUC.util.GeradorAleatorio;

public class RecomendadorService {
	
	private CatalogoFilmesAPI catalogo;
	private HistoricoUsuarioRepository historico;
	private NotificadorPush notificador;
	private GeradorAleatorio gerador;
	private CalculadoraScore calculadora;
	private FiltroFilmes filtro;
	
	public RecomendadorService(CatalogoFilmesAPI catalogo, HistoricoUsuarioRepository historico,
			NotificadorPush notificador, GeradorAleatorio gerador, CalculadoraScore calculadora, FiltroFilmes filtro) {
		this.catalogo = catalogo;
		this.historico = historico;
		this.notificador = notificador;
		this.gerador = gerador;
		this.calculadora = calculadora;
		this.filtro = filtro;
	}
	
	public List<Recomendacao> recomendar(Usuario usuario, int topN){
		
		try {
		
			PerfilCinefilo perfil = usuario.getPerfilCinefilo();

			List<Filme> catalogoFilmes = catalogo.buscarTodos();
        
			if (catalogoFilmes == null || catalogoFilmes.isEmpty()) {
				return Collections.emptyList();
			}

			List<Filme> filmesFiltrados = filtro.filtrar(perfil, catalogoFilmes);
        
			if (filmesFiltrados.isEmpty()) {
				return Collections.emptyList();
			}
        
			List<Recomendacao> recomendacoes = gerarRecomendacoes(perfil, filmesFiltrados);
	
			ordenarRecomendacoes(recomendacoes);
        
			List<Recomendacao> topRecomendacoes = recomendacoes.stream()
					.limit(topN)
					.collect(Collectors.toList()); //o toList nao funcionou sem o collect

			historico.registrarRecomendacao(usuario,topRecomendacoes);

			if (usuario.isNotificacoesAtivadas()) {
				notificador.enviar(usuario,"Saiu sua recomendação do dia!");
			}
        
			return topRecomendacoes;
			
		} catch(Exception e) {
    	
			return Collections.emptyList();
			
		}
	}
	
	
	

	private List<Recomendacao> gerarRecomendacoes(PerfilCinefilo perfil, List<Filme> filmes){
		 
		List<Recomendacao> recomendacoes = new ArrayList();
		
		for( Filme f : filmes) {
			
			double score = calculadora.calcularScore(perfil, f);
			
			String justificativa = criarJustificativa(f);
			
			recomendacoes.add(new Recomendacao(null, f, score, justificativa));
		}
		
		return recomendacoes;
	
	}
	
	
	
	/*FALTA IMPLEMENTAR AQUI*/
	private String criarJustificativa(Filme f) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/*FALTA IMPLEMENTAR O DESEMPATE VIA GERADOR ALEATORIO*/
	private void ordenarRecomendacoes(List<Recomendacao> recomendacoes) {
		recomendacoes.sort(
				Comparator
					.comparingDouble(Recomendacao::getScore)
					.reversed()
				
					.thenComparing(
							(r1, r2) -> Integer.compare(
									r2.getFilme().getPopularidade(),
									r1.getFilme().getPopularidade()
							)
					)
		);
		
	}


	
	
}
