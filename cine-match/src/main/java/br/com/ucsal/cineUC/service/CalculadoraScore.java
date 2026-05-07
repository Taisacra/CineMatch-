package br.com.ucsal.cineUC.service;

import java.util.List;
import java.util.Map;

import br.com.ucsal.cineUC.model.Filme;
import br.com.ucsal.cineUC.model.PerfilCinefilo;
import br.com.ucsal.cineUC.model.enums.Genero;

public class CalculadoraScore {

	static final double PESO_GENERO       = 0.50;
	static final double PESO_DURACAO      = 0.20;
	static final double PESO_POPULARIDADE = 0.15;
	static final double PESO_AFINIDADE    = 0.15;
	
	public double calcularScore(PerfilCinefilo perfil, Filme filme) {
		double score =  PESO_GENERO * scoreGenero(perfil,filme) + PESO_DURACAO * scoreDuracao(perfil,filme)
		+ PESO_POPULARIDADE * scorePopularidade(filme) + PESO_AFINIDADE * scoreAfinidade(perfil, filme);
		
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
			diferencaDuracao = perfil.getDuracaoMin() - filme.getDuracao(); // se a duração for menor, a diferená é em relação ao minimo 
		}
		
		/*REVISAR A LOGICA DA REDUCAO PROPORCIONAL*/
		double scoreD = 100 - (diferencaDuracao * 1.25);
		
		if(scoreD < 0) {
			return 0;
		}
		
		return scoreD;
	}
	

	private double scorePopularidade(Filme filme) {
		return filme.getPopularidade();
	}
	
	
	private double scoreAfinidade(PerfilCinefilo perfil, Filme filme) {
	
		for (Map.Entry<Filme, Integer> entry : perfil.getNotas().entrySet()) {
			 Filme filmeAssistido = entry.getKey(); //pega o filme
			 int nota = entry.getValue(); //pega o valor

			 if (nota >= 4) {
		            for (Genero genero : filme.getGeneros()) {
		                if (filmeAssistido.getGeneros().contains(genero)) {
		                    return 100; //15% equivale a 100. Verificar 
		                }
		            }
		     }
	    }
		return 0;

	}
}
