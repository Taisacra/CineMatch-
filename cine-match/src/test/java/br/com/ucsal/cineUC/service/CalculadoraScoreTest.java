package br.com.ucsal.cineUC.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import br.com.ucsal.cineUC.model.Filme;
import br.com.ucsal.cineUC.model.PerfilCinefilo;
import br.com.ucsal.cineUC.model.enums.ClassificacaoEtaria;
import br.com.ucsal.cineUC.model.enums.Genero;
import br.com.ucsal.cineUC.model.enums.Idioma;

class CalculadoraScoreTest {

    private CalculadoraScore calculadora;
    private PerfilCinefilo perfil;

    @BeforeEach
    void setUp() {
        calculadora = new CalculadoraScore();

        perfil = new PerfilCinefilo(
                90,
                120,
                ClassificacaoEtaria.DEZOITO
        );
    }
    
    @Test
    @DisplayName("o score do componente genero deve ser 100 se filme tiver todos os generos com peso 1.0")
    void deve_RetornarScoreGeneroMaximo_Quando_TodosGenerosPossuemPesoUm() {

        perfil.adicionarPesoGenero(Genero.FICCAO_CIENTIFICA, 1.0);
        perfil.adicionarPesoGenero(Genero.DRAMA, 1.0);

        Filme filme = new Filme(
                "F01",
                "Interestelar",
                2014,
                100,
                List.of(Genero.FICCAO_CIENTIFICA, Genero.DRAMA),
                ClassificacaoEtaria.DEZ,
                Idioma.EN,
                90
        );

        double score = calculadora.scoreGenero(perfil, filme);

        assertEquals(100, score);
    }
    
    @Test
    @DisplayName("deve retornar um score abaixo de 50 quando o genero nao for preferido (peso genero = 0)")
    void deve_RetornarScoreBaixo_Quando_GeneroNaoPreferido() {

        perfil.adicionarPesoGenero(Genero.TERROR, 0.0);

        Filme filme = new Filme(
                "F02",
                "Terror Aleatório",
                2020,
                100,
                List.of(Genero.TERROR),
                ClassificacaoEtaria.DEZ,
                Idioma.EN,
                80
        );

        double score = calculadora.calcularScore(perfil, filme);
        
        assertTrue(score < 50);
    }
    
    @Test
    @DisplayName("o score do componente duracao deve ser 100 quando o filme estiver dentro da duração preferida.")
    void deve_RetornarScoreDuracaoMaximo_Quando_FilmeDentroDaDuracaoPreferida() {

        Filme filme = new Filme(
                "F03",
                "Filme Ideal",
                2020,
                100,
                List.of(Genero.DRAMA),
                ClassificacaoEtaria.DEZ,
                Idioma.EN,
                70
        );

        double score = calculadora.scoreDuracao(perfil, filme);

        assertTrue(score == 100);
    }
    
    @Test
    @DisplayName("deve reduzir a duracao quando o filme estiver 30 min acima da preferencia.")
    void deve_ReduzirScore_Quando_DuracaoTrintaMinAcima() {

        Filme filme = new Filme(
                "F04",
                "Filme Longo",
                2020,
                150,
                List.of(Genero.DRAMA),
                ClassificacaoEtaria.DEZ,
                Idioma.EN,
                70
        );

        double score = calculadora.scoreDuracao(perfil, filme);

        assertEquals(62.5, score);
    }
    
    @Test
    @DisplayName("o score nunca deve passar de 100")
    void deve_RetornarScoreEntreZeroECem_Quando_Calcular(){
    	 
    	Filme filme = new Filme(
    	            "F05",
    	            "Teste Limite",
    	            2020,
    	            500,
    	            List.of(Genero.TERROR),
    	            ClassificacaoEtaria.DEZOITO,
    	            Idioma.EN,
    	            10
    	    );
    	double score = calculadora.calcularScore(perfil, filme);

        assertTrue(score >= 0);
        assertTrue(score <= 100);
    }
    
    @ParameterizedTest
    @CsvSource({
        "1.0, 1.0, 1.0, 100",
        "0.5, 0.5, 0.5,  50",
        "0.0, 0.0, 0.0,   0"
    })
    @DisplayName("score de gênero é a média ponderada dos pesos")
    void deve_CalcularScoreDeGenero_ConformePesos(double a, double b, double c, int esperado) { 
    	perfil.adicionarPesoGenero(Genero.COMEDIA, a);
        perfil.adicionarPesoGenero(Genero.ROMANCE, b);
        perfil.adicionarPesoGenero(Genero.DRAMA, c);
        
        Filme filme = new Filme(
	            "F05",
	            "Filme Comedia Romantica Dramatica",
	            2020,
	            500,
	            List.of(Genero.COMEDIA, Genero.ROMANCE, Genero.DRAMA),
	            ClassificacaoEtaria.DEZOITO,
	            Idioma.EN,
	            10
	    );
        
        double score = calculadora.scoreGenero(perfil, filme);
        
        assertEquals(esperado, score);
    }

}