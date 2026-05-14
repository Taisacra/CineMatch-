package br.com.ucsal.cineUC.service;

import java.util.ArrayList;
import java.util.List;

import br.com.ucsal.cineUC.model.Filme;
import br.com.ucsal.cineUC.model.enums.ClassificacaoEtaria;
import br.com.ucsal.cineUC.model.enums.Genero;
import br.com.ucsal.cineUC.model.enums.Idioma;

public class CatalogoMock implements CatalogoFilmesAPI {

	    @Override
	    public List<Filme> buscarTodos() {

	        List<Filme> filmes = new ArrayList<>();

	        filmes.add(new Filme("F01", "Duna: Parte Dois", 2024, 166, List.of(Genero.FICCAO_CIENTIFICA, Genero.DRAMA), ClassificacaoEtaria.QUATORZE, Idioma.EN, 92));
	        filmes.add(new Filme("F02", "Ela (Her)", 2013, 126, List.of(Genero.FICCAO_CIENTIFICA, Genero.DRAMA, Genero.ROMANCE), ClassificacaoEtaria.DEZESSEIS, Idioma.EN, 78));
	        filmes.add(new Filme("F03", "O Iluminado", 1980, 146, List.of(Genero.TERROR), ClassificacaoEtaria.DEZOITO, Idioma.EN, 88));
	        filmes.add(new Filme("F04", "O Poderoso Chefão", 1972, 175, List.of(Genero.DRAMA, Genero.CRIME), ClassificacaoEtaria.QUATORZE, Idioma.EN, 100));
	        filmes.add(new Filme("F05", "Pulp Fiction", 1994, 154, List.of(Genero.CRIME, Genero.DRAMA), ClassificacaoEtaria.DEZOITO, Idioma.EN, 94));
	        filmes.add(new Filme("F06", "Interestelar", 2014, 169, List.of(Genero.FICCAO_CIENTIFICA, Genero.DRAMA), ClassificacaoEtaria.DEZ, Idioma.EN, 87));
	        filmes.add(new Filme("F07", "Parasita", 2019, 132, List.of(Genero.DRAMA, Genero.SUSPENSE), ClassificacaoEtaria.DEZESSEIS, Idioma.KO, 99));
	        filmes.add(new Filme("F08", "Cidade de Deus", 2002, 130, List.of(Genero.CRIME, Genero.DRAMA), ClassificacaoEtaria.DEZOITO, Idioma.PT, 91));
	        filmes.add(new Filme("F09", "Matrix", 1999, 136, List.of(Genero.FICCAO_CIENTIFICA, Genero.ACAO), ClassificacaoEtaria.DOZE, Idioma.EN, 88));
	        filmes.add(new Filme("F10", "O Auto da Compadecida", 2000, 104, List.of(Genero.COMEDIA, Genero.AVENTURA), ClassificacaoEtaria.LIVRE, Idioma.PT, 95));
	        filmes.add(new Filme("F11", "Batman: O Cavaleiro das Trevas", 2008, 152, List.of(Genero.ACAO, Genero.CRIME), ClassificacaoEtaria.DOZE, Idioma.EN, 94));
	        filmes.add(new Filme("F12", "A Viagem de Chihiro", 2001, 125, List.of(Genero.ANIMACAO, Genero.AVENTURA), ClassificacaoEtaria.LIVRE, Idioma.JA, 96));
	        filmes.add(new Filme("F13", "Clube da Luta", 1999, 139, List.of(Genero.DRAMA), ClassificacaoEtaria.DEZOITO, Idioma.EN, 88));
	        filmes.add(new Filme("F14", "O Labirinto do Fauno", 2006, 118, List.of(Genero.FANTASIA, Genero.DRAMA), ClassificacaoEtaria.DEZESSEIS, Idioma.ES, 95));
	        filmes.add(new Filme("F15", "Oppenheimer", 2023, 180, List.of(Genero.DRAMA, Genero.HISTORIA), ClassificacaoEtaria.DEZESSEIS, Idioma.EN, 93));
	        filmes.add(new Filme("F16", "Blade Runner 2049", 2017, 164, List.of(Genero.FICCAO_CIENTIFICA, Genero.DRAMA), ClassificacaoEtaria.QUATORZE, Idioma.EN, 82));
	        filmes.add(new Filme("F17", "Toy Story", 1995, 81, List.of(Genero.ANIMACAO, Genero.COMEDIA), ClassificacaoEtaria.LIVRE, Idioma.EN, 100));
	        filmes.add(new Filme("F18", "Mad Max: Estrada da Fúria", 2015, 120, List.of(Genero.ACAO, Genero.AVENTURA), ClassificacaoEtaria.QUATORZE, Idioma.EN, 97));
	        filmes.add(new Filme("F19", "Coringa", 2019, 122, List.of(Genero.DRAMA, Genero.CRIME), ClassificacaoEtaria.DEZOITO, Idioma.EN, 68));
	        filmes.add(new Filme("F20", "Vingadores: Ultimato", 2019, 181, List.of(Genero.ACAO, Genero.AVENTURA), ClassificacaoEtaria.DOZE, Idioma.EN, 94));
	        filmes.add(new Filme("F21", "O Show de Truman", 1998, 103, List.of(Genero.DRAMA, Genero.COMEDIA), ClassificacaoEtaria.LIVRE, Idioma.EN, 95));
	        filmes.add(new Filme("F22", "Seven: Os Sete Crimes Capitais", 1995, 127, List.of(Genero.CRIME, Genero.SUSPENSE), ClassificacaoEtaria.DEZESSEIS, Idioma.EN, 83));
	        filmes.add(new Filme("F23", "Central do Brasil", 1998, 113, List.of(Genero.DRAMA), ClassificacaoEtaria.DOZE, Idioma.PT, 94));
	        filmes.add(new Filme("F24", "Gladiador", 2000, 155, List.of(Genero.ACAO, Genero.DRAMA), ClassificacaoEtaria.QUATORZE, Idioma.EN, 80));
	        filmes.add(new Filme("F25", "Your Name", 2016, 106, List.of(Genero.ANIMACAO, Genero.DRAMA, Genero.ROMANCE), ClassificacaoEtaria.LIVRE, Idioma.JA, 98));
	        filmes.add(new Filme("F26", "A Lista de Schindler", 1993, 195, List.of(Genero.DRAMA, Genero.HISTORIA), ClassificacaoEtaria.QUATORZE, Idioma.EN, 98));
	        filmes.add(new Filme("F27", "Tudo em Todo o Lugar ao Mesmo Tempo", 2022, 139, List.of(Genero.FICCAO_CIENTIFICA, Genero.ACAO), ClassificacaoEtaria.QUATORZE, Idioma.EN, 93));
	        filmes.add(new Filme("F28", "Django Livre", 2012, 165, List.of(Genero.ACAO, Genero.DRAMA), ClassificacaoEtaria.DEZOITO, Idioma.EN, 87));
	        filmes.add(new Filme("F29", "O Silêncio dos Inocentes", 1991, 118, List.of(Genero.CRIME, Genero.SUSPENSE), ClassificacaoEtaria.DEZESSEIS, Idioma.EN, 95));
	        filmes.add(new Filme("F30", "Whiplash: Em Busca da Perfeição", 2014, 106, List.of(Genero.DRAMA, Genero.MUSICA), ClassificacaoEtaria.DOZE, Idioma.EN, 94));
			return filmes;
	    }

}
