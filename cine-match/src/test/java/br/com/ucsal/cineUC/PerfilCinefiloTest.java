package br.com.ucsal.cineUC;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import br.com.ucsal.cineUC.exception.DuracaoInvalidaException;
import br.com.ucsal.cineUC.exception.NotaInvalidaException;
import br.com.ucsal.cineUC.exception.PesoInvalidoException;
import br.com.ucsal.cineUC.model.Filme;
import br.com.ucsal.cineUC.model.PerfilCinefilo;
import br.com.ucsal.cineUC.model.enums.ClassificacaoEtaria;
import br.com.ucsal.cineUC.model.enums.Genero;
import br.com.ucsal.cineUC.model.enums.Idioma;

class PerfilCinefiloTest {

    private PerfilCinefilo perfil;
    private Filme filme;

    @BeforeEach
    void setUp() {
        perfil = new PerfilCinefilo(
                90,
                150,
                ClassificacaoEtaria.DEZESSEIS
        );

        filme = new Filme(
               "F01",
               "Duna: Parte Dois",
                2024,
                166,
                List.of(Genero.FICCAO_CIENTIFICA, Genero.DRAMA),
                ClassificacaoEtaria.QUATORZE,
                Idioma.EN,
                92
        );
    }
    
    @Test
    @DisplayName("o perfil deve ser criado com pesos entre 0 e 1.")
    void deve_CriarPerfilComPesosValidos_Quando_PesoEntreZeroEUm() {
        perfil.adicionarPesoGenero(Genero.FICCAO_CIENTIFICA, 0.8);
        assertEquals(0.8, perfil.getPesoPorGenero(Genero.FICCAO_CIENTIFICA), 0.001);
    }
    
    @Test
    @DisplayName("deve ser lançada uma PesoInvalidoException quando o peso for menor que zero")
    void deve_LancarExcecao_Quando_PesoMenorQueZero(){
    	assertThrows(PesoInvalidoException.class, () -> perfil.adicionarPesoGenero(Genero.DRAMA, -0.3));
    }
    
    @Test
    @DisplayName("deve ser lançada uma PesoInvalidoException quando o peso for maior que 1")
    void deve_LancarExcecao_Quando_PesoMaiorQueUm(){
    	assertThrows(PesoInvalidoException.class, () -> perfil.adicionarPesoGenero(Genero.DRAMA, 1.4));
    }
    
    @Test
    @DisplayName("deve ser lançada uma DuracaoInvalidaException quando a duração mínima for maior que a duração máxima")
    void deve_LancarExcecao_Quando_DuracaoMinimaMaiorQueDuracaoMaxima(){
    	assertThrows(DuracaoInvalidaException.class, () -> new PerfilCinefilo(
                200,
                100,
                ClassificacaoEtaria.DEZOITO));
    }
    
    @Test
    @DisplayName("deve ser lançada uma NotaInvalidaException quando for adicionada uma nota fora do intervalo de 1 a 5")
    void deve_LancarExcecao_Quando_NotaForaDoIntervalo(){
    	assertThrows(NotaInvalidaException.class, () -> perfil.adicionarNotaFilme(filme, 6));
    }
    
    @Test
    @DisplayName("deve adicionar filme ao historico quando marcado como assistido")
    void deve_AdicionarFilmeAoHistorico_Quando_MarcadoComoAssistido() {
        perfil.adicionarFilmeAssistido(filme);
        assertTrue(perfil.getHistorico().contains(filme));
    }
    
}
