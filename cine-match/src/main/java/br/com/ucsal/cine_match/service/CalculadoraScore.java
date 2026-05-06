package br.com.ucsal.cine_match.service;

import java.util.List;
import java.util.Map;

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
	
	/*FALTA IMPLEMENTAR ESSE SCORE*/
	
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
