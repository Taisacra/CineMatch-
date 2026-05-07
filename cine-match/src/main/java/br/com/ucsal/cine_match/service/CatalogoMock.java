package br.com.ucsal.cine_match.service;

import java.util.ArrayList;
import java.util.List;

import br.com.ucsal.cine_match.model.Filme;
import br.com.ucsal.cine_match.model.enums.ClassificacaoEtaria;
import br.com.ucsal.cine_match.model.enums.Genero;
import br.com.ucsal.cine_match.model.enums.Idioma;

public class CatalogoMock implements CatalogoFilmesAPI {

	    @Override
	    public List<Filme> buscarTodos() {

	        List<Filme> filmes = new ArrayList<>();

	        filmes.add(new Filme(
	                (long) 01,
	                "Duna: Parte Dois",
	                2024,
	                166,
	                List.of(Genero.FICCAO_CIENTIFICA, Genero.DRAMA),
	                ClassificacaoEtaria.QUATORZE,
	                Idioma.EN,
	                92
	        ));
	        
	        filmes.add(new Filme(
	                (long) 02,
	                "Ela (Her)",
	                2013,
	                126,
	                List.of(Genero.FICCAO_CIENTIFICA, Genero.DRAMA, Genero.ROMANCE),
	                ClassificacaoEtaria.DEZESSEIS,
	                Idioma.EN,
	                78
	        ));
	        
	        filmes.add(new Filme(
	                (long) 03,
	                "O Iluminado",
	                1980,
	                146,
	                List.of(Genero.TERROR),
	                ClassificacaoEtaria.DEZOITO,
	                Idioma.EN,
	                88
	        ));
	        
			return filmes;
	    }

}
