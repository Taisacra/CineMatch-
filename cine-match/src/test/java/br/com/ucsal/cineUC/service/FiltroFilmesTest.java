package br.com.ucsal.cineUC.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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
        
        List<Filme> resultado = filtro.filtrar(perfil, catalogo);
        
        assertFalse(resultado.contains(filme));
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

	    List<Filme> resultado = filtro.filtrar(perfil, catalogo);

	    assertFalse(resultado.contains(filme));
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

	    List<Filme> resultado = filtro.filtrar(perfil, catalogo);

	    assertFalse(resultado.contains(filme));
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

	    List<Filme> resultado = filtro.filtrar(perfil, catalogo);

	    assertFalse(resultado.contains(filme));
	}
	
	@Test
	@DisplayName("deve retornar lista vazia quando catalogo estiver vazio")
	void deve_RetornarListaVazia_Quando_CatalogoVazio() {

	    List<Filme> catalogo = new ArrayList<>();

	    List<Filme> resultado = filtro.filtrar(perfil, catalogo);

	    assertNotNull(resultado);
	    assertTrue(resultado.isEmpty());
	}
	
	@ParameterizedTest
	@CsvSource({
	    "DEZ, DOZE, true",
	    "DEZESSEIS, DEZESSEIS, true",
	    "DEZOITO, DEZESSEIS, false"
	})
	@DisplayName("filme é aceito quando classificação menor ou igual a máxima do perfil")
	void deve_AceitarFilme_QuandoClassificacaoEhCompativel(String classificacaoFilme, String classificacaoPerfil, boolean esperado) {

	    PerfilCinefilo perfil = new PerfilCinefilo(
	            90,
	            150,
	            ClassificacaoEtaria.valueOf(classificacaoPerfil)
	    );

	    Filme filme = new Filme(
	            "F10",
	            "Filme Teste",
	            2020,
	            120,
	            List.of(Genero.DRAMA),
	            ClassificacaoEtaria.valueOf(classificacaoFilme),
	            Idioma.EN,
	            80
	    );

	    FiltroFilmes filtro = new FiltroFilmes();

	    boolean resultado = filtro.validarClassificacao(perfil, filme);

	    assertEquals(esperado, resultado);
	}
}
