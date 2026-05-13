package br.com.ucsal.cineUC;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import br.com.ucsal.cineUC.model.Filme;
import br.com.ucsal.cineUC.model.enums.ClassificacaoEtaria;
import br.com.ucsal.cineUC.model.enums.Genero;
import br.com.ucsal.cineUC.model.enums.Idioma;

class FilmeTest {

    private Filme filme1;
    private Filme filme2;

    @BeforeEach
    void setUp() {

        filme1 = new Filme(
                "F02",
                "Ela(Her)",
                2013,
                126,
                List.of(Genero.FICCAO_CIENTIFICA, Genero.DRAMA, Genero.ROMANCE),
                ClassificacaoEtaria.DEZESSEIS,
                Idioma.EN,
                78
        );

        filme2 = new Filme(
        	    "F02",
        	    "Outro título",
        	    2020,
        	    100,
        	    List.of(Genero.ACAO),
        	    ClassificacaoEtaria.DEZ,
        	    Idioma.PT,
        	    50
        );
    }

    @Test
    @DisplayName("todos os atributos devem ser preenchidos ao criar um filme")
    void deve_CriarFilmeComTodosAtributos_Quando_DadosValidos() {
        assertAll(
                () -> assertEquals("F02", filme1.getIdFilme()),
                () -> assertEquals("Ela(Her)", filme1.getTitulo()),
                () -> assertEquals(126, filme1.getDuracao()),
                () -> assertEquals(2013, filme1.getAno()),
                () -> assertEquals(126, filme1.getDuracao()),
                () -> assertEquals(
                        List.of(
                            Genero.FICCAO_CIENTIFICA,
                            Genero.DRAMA,
                            Genero.ROMANCE
                        ),
                        filme1.getGeneros()
                ),
                () -> assertEquals(
                        ClassificacaoEtaria.DEZESSEIS,
                        filme1.getClassificacao()
                ),
                () -> assertEquals(
                        Idioma.EN,
                        filme1.getIdioma()
                ),
                () -> assertEquals(78, filme1.getPopularidade())
        );
    }

    @Test
    @DisplayName("filmes com Id's iguais devem ser considerado iguais")
    void deve_ConsiderarFilmesIguais_Quando_PossuemMesmoId() {
        assertEquals(filme1, filme2);
        assertEquals(filme1.hashCode(), filme2.hashCode());
    }
}