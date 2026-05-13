package br.com.ucsal.cineUC.service;

import java.util.List;
import java.util.Set;

import br.com.ucsal.cineUC.model.Filme;
import br.com.ucsal.cineUC.model.PerfilCinefilo;
import br.com.ucsal.cineUC.model.enums.Genero;
import br.com.ucsal.cineUC.model.enums.Idioma;

public class FiltroFilmes {
	
	public List<Filme> filtrar (PerfilCinefilo perfil, List<Filme> filmes){
		
		 if (filmes == null || filmes.isEmpty()) {
		        return List.of(); //retorna a lista vazia
		    }
		
		filmes.removeIf(f ->
        	validarAssistido(perfil, f)
        	|| !validarIdioma(perfil, f)
        	|| !validarGenero(perfil, f)
        	|| !validarClassificacao(perfil, f)
		);
		
		return filmes;
	}
	
	boolean validarAssistido(PerfilCinefilo perfil, Filme filme) {
		Set<Filme> historico = perfil.getHistorico();
		for (Filme f : historico ) {
			if(f.equals(filme)) {
				return true; //retorna true se já tiver assistido
			}
		}
		return false;
	}


	boolean validarIdioma(PerfilCinefilo perfil, Filme filme) {
		Set<Idioma> idiomas = perfil.getIdiomas();
		for(Idioma i : idiomas) {
			if (i == filme.getIdioma()) {
				return true; //retorna true se o idioma for um dos aceitos
			}
		}
		return false;
	}
	
	boolean validarGenero(PerfilCinefilo perfil, Filme filme){
		List<Genero> generos = filme.getGeneros();
		for(Genero g : generos) {
			if(perfil.getPesoPorGenero(g) == 0.0) {
				return false; // retorna false se tiver algum peso igual a 0
			}
		}
		return true;
	}

	boolean validarClassificacao(PerfilCinefilo perfil, Filme filme){
		if(filme.getClassificacao().getValorClassificacao() <= perfil.getClassificacaoMaxima().getValorClassificacao()) {
			return true; //retorna true que indica classificacao aceita
		}
		return false;
	}
}
