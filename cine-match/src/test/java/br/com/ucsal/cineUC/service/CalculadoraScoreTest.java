package br.com.ucsal.cineUC.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import br.com.ucsal.cineUC.model.Filme;
import br.com.ucsal.cineUC.model.PerfilCinefilo;
import br.com.ucsal.cineUC.model.enums.ClassificacaoEtaria;
import br.com.ucsal.cineUC.model.enums.Genero;
import br.com.ucsal.cineUC.model.enums.Idioma;
import br.com.ucsal.cineUC.service.CalculadoraScore;

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
    @DisplayName("deve retornar um score baixo quando o genero nao for preferido")
    void deve_RetornarScoreBaixo_Quando_GeneroNaoPreferido() {

        perfil.adicionarPesoGenero(Genero.ROMANCE, 0.0);

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
}