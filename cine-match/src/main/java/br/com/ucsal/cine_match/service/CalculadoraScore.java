package br.com.ucsal.cine_match.service;

import java.util.List;

import br.com.ucsal.cine_match.model.Filme;
import br.com.ucsal.cine_match.model.PerfilCinefilo;
import br.com.ucsal.cine_match.model.enums.Genero;

public class CalculadoraScore {

	public double calcularScore(PerfilCinefilo perfil, Filme filme) {
		double score =  0.5 * scoreGenero(perfil,filme) + 0.2 * scoreDuracao(perfil,filme)
		+ 0.15 * scorePopularidade(filme) /*+ 0.15 * scoreAfinidade(perfil, filme)*/;
		
		return score;
	}
	
	private double scoreGenero(PerfilCinefilo perfil, Filme filme) {
		List<Genero> generosFilme = filme.getGeneros();
		
		if (filme.getGeneros().isEmpty()) {
			return 0;
		}
		
		double somaPesos = 0;
		
		for(Genero genero : generosFilme) {
			somaPesos += perfil.getPesoPorGenero(genero);
		}
		
		double scoreG = somaPesos / generosFilme.size();
		
		return scoreG * 100;
	}
	

	private double scoreDuracao(PerfilCinefilo perfil, Filme filme) {
		
		if(filme.getDuracao() >= perfil.getDuracaoMin() && filme.getDuracao() <= perfil.getDuracaoMax()) {
			return 100;
		}
		
		int diferencaDuracao;
		
		if(filme.getDuracao() > perfil.getDuracaoMax()) {
			diferencaDuracao = filme.getDuracao() - perfil.getDuracaoMax(); 
		} else {
			diferencaDuracao = perfil.getDuracaoMax() - filme.getDuracao();
		}
		
		/*REVISAR A LOGICA DA REDUCAO PROPORCIONAL*/
		double scoreD = 100 - diferencaDuracao;
		
		if(scoreD < 0) {
			return 0;
		}
		
		return scoreD;
	}
	

	private double scorePopularidade(Filme filme) {
		return filme.getPopularidade();
	}
	
	/*FALTA IMPLEMENTAR ESSE SCORE*/
	
	/*private double scoreAfinidade(PerfilCinefilo perfil, Filme filme) {
		
	}*/
}
