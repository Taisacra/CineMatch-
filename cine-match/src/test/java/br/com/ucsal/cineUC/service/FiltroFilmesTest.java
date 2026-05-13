package br.com.ucsal.cineUC.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import br.com.ucsal.cineUC.model.Filme;
import br.com.ucsal.cineUC.model.PerfilCinefilo;
import br.com.ucsal.cineUC.model.enums.ClassificacaoEtaria;
import br.com.ucsal.cineUC.model.enums.Genero;
import br.com.ucsal.cineUC.model.enums.Idioma;

class FiltroFilmesTest {

	private FiltroFilmes filtro;
	private PerfilCinefilo perfil;

	@BeforeEach
	void setUp() {
		filtro = new FiltroFilmes();

		perfil = new PerfilCinefilo(90, 150, ClassificacaoEtaria.DEZESSEIS);
		perfil.adicionarIdioma(Idioma.EN);
	    perfil.adicionarPesoGenero(Genero.ROMANCE, 1.0);
	    perfil.adicionarPesoGenero(Genero.DRAMA, 1.0);
	}
	
	
	@Test
	@DisplayName("deve remover filmes que já foram assistidos")
	void deve_RemoverFilme_Quando_JaFoiAssistido() {
		
        Filme filme = new Filme(
                "F01",
                "Duna: Parte Dois",
                 2024,
                 166,
                 List.of(Genero.FICCAO_CIENTIFICA, Genero.DRAMA),
                 ClassificacaoEtaria.QUATORZE,
                 Idioma.PT,
                 92
         );
        
        perfil.adicionarFilmeAssistido(filme);
        
        List<Filme> catalogo = new ArrayList<>();
        catalogo.add(filme);
        
        List<Filme> catalogoFiltrado = filtro.filtrar(perfil, catalogo);
        
        assertFalse(catalogoFiltrado.contains(filme));
	}
	
	@Test
	@DisplayName("deve remover filme com classificação acima da permitida")
	void deve_RemoverFilme_Quando_ClassificacaoForMaior() {

	    Filme filme = new Filme(
	            "F02",
	            "Filme Proibido",
	            2020,
	            110,
	            List.of(Genero.DRAMA),
	            ClassificacaoEtaria.DEZOITO,
	            Idioma.EN,
	            80
	    );

	    List<Filme> catalogo = new ArrayList<>();
	    catalogo.add(filme);

	    List<Filme> catalogoFiltrado = filtro.filtrar(perfil, catalogo);

	    assertFalse(catalogoFiltrado.contains(filme));
	}
	
	@Test
	@DisplayName("deve remover filme quando idioma nao for aceito")
	void deve_RemoverFilme_Quando_IdiomaNaoAceito() {

	    Filme filme = new Filme(
	            "F03",
	            "Filme Inglês",
	            2021,
	            100,
	            List.of(Genero.DRAMA),
	            ClassificacaoEtaria.DOZE,
	            Idioma.EN,
	            70
	    );

	    List<Filme> catalogo = new ArrayList<>();
	    catalogo.add(filme);

	    List<Filme> catalogoFiltrado = filtro.filtrar(perfil, catalogo);

	    assertFalse(catalogoFiltrado.contains(filme));
	}
	
	@Test
	@DisplayName("deve remover filme quando o peso do seu gênero for zero")
	void deve_RemoverFilme_Quando_GeneroTemPesoZero() {

		perfil.adicionarPesoGenero(Genero.TERROR, 0.0);
		
	    Filme filme = new Filme(
	    		"F04",
	            "Filme Terror",
	            2022,
	            100,
	            List.of(Genero.TERROR),
	            ClassificacaoEtaria.DEZESSEIS,
	            Idioma.EN,
	            60
	    );

	    List<Filme> catalogo = new ArrayList<>();
	    catalogo.add(filme);

	    List<Filme> catalogoFiltrado = filtro.filtrar(perfil, catalogo);

	    assertFalse(catalogoFiltrado.contains(filme));
	}
	
	@Test
	@DisplayName("deve retornar lista vazia quando catalogo estiver vazio")
	void deve_RetornarListaVazia_Quando_CatalogoVazio() {

	    List<Filme> catalogo = new ArrayList<>();

	    List<Filme> catalogoVazio = filtro.filtrar(perfil, catalogo);

	    assertNotNull(catalogoVazio);
	    assertTrue(catalogoVazio.isEmpty());
	}
}
