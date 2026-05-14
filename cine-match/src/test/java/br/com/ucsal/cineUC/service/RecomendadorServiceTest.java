package br.com.ucsal.cineUC.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.ucsal.cineUC.model.Filme;
import br.com.ucsal.cineUC.model.PerfilCinefilo;
import br.com.ucsal.cineUC.model.Recomendacao;
import br.com.ucsal.cineUC.model.Usuario;
import br.com.ucsal.cineUC.model.enums.ClassificacaoEtaria;
import br.com.ucsal.cineUC.model.enums.Genero;
import br.com.ucsal.cineUC.model.enums.Idioma;
import br.com.ucsal.cineUC.util.GeradorAleatorio;

@ExtendWith(MockitoExtension.class)
public class RecomendadorServiceTest {

    @Mock private CatalogoFilmesAPI catalogo;
    @Mock private HistoricoUsuarioRepository historico;
    @Mock private NotificadorPush notificador;
    @Mock private GeradorAleatorio gerador;
    
    @Spy private CalculadoraScore calculadora = new CalculadoraScore();
    @Spy private FiltroFilmes filtro = new FiltroFilmes();

    @InjectMocks private RecomendadorService service;
    
    private Usuario maria;
    private List<Filme> filmesFake;
    
    @BeforeEach
    public void setup() {
    	MockitoAnnotations.openMocks(this);
        PerfilCinefilo perfil = new PerfilCinefilo(60, 180, ClassificacaoEtaria.QUATORZE);
        
        perfil.adicionarPesoGenero(Genero.TERROR, 1.0);
        perfil.adicionarPesoGenero(Genero.ACAO, 0.5);
        perfil.adicionarIdioma(Idioma.PT);
 
        maria = new Usuario(1L, "Maria", 25, perfil, true);

        Filme f1 = new Filme("1", "O Iluminado", 1980, 146, List.of(Genero.TERROR), ClassificacaoEtaria.DEZESSEIS, Idioma.EN, 95);
        Filme f2 = new Filme("2", "Duro de Matar", 1988, 132, List.of(Genero.ACAO), ClassificacaoEtaria.QUATORZE, Idioma.EN, 90);
        Filme f3 = new Filme("3", "Toy Story", 1995, 81, List.of(Genero.ANIMACAO), ClassificacaoEtaria.LIVRE, Idioma.EN, 80);

        filmesFake = List.of(f1, f2, f3);
    }
    
    @Test
    @DisplayName("Deve retornar recomendações quando o catálogo for carregado")
    public void deveRecomendarComSucesso() {
        when(catalogo.buscarTodos()).thenReturn(filmesFake);

        List<Recomendacao> resultado = service.recomendar(maria, 2);

        // O filtro deve deixar passar filmes adequados à Maria (Terror/Ficção)
        assertFalse(resultado.isEmpty());
        assertTrue(resultado.size() <= 2);
    }
    
    @Test
    @DisplayName("Deve retornar lista vazia e não notificar quando a API falhar")
    public void deveTratarFalhaNoCatalogo() {
        when(catalogo.buscarTodos()).thenThrow(new RuntimeException("API Offline"));

        List<Recomendacao> resultado = service.recomendar(maria, 5);

        assertTrue(resultado.isEmpty());
        // 4. verify(...) com never() - Confirmar que não houve notificação na falha
        verify(notificador, never()).enviar(any(), anyString());
    }
    
    @Test
    @DisplayName("Deve usar stub sequencial para o critério de desempate")
    public void deveUsarStubSequencialNoDesempate() {
        when(catalogo.buscarTodos()).thenReturn(filmesFake);
        // Usado no modo surpreenda-me ou no desempate da ordenação
        when(gerador.sortearInteiro(anyInt(), anyInt())).thenReturn(2, 7, 0);

        service.recomendar(maria, 3);

        verify(gerador, atLeastOnce()).sortearInteiro(anyInt(), anyInt());
    }
    
    @Test
    @DisplayName("Deve capturar e validar os dados gravados no histórico")
    public void deveInspecionarRecomendacoesRegistradas() {
        when(catalogo.buscarTodos()).thenReturn(filmesFake);
        ArgumentCaptor<List<Recomendacao>> captor = ArgumentCaptor.forClass(List.class);

        service.recomendar(maria, 3);

        // Uso de Matchers: eq(maria) e captor para o segundo argumento
        verify(historico).registrarRecomendacao(eq(maria), captor.capture());
        
        List<Recomendacao> registradas = captor.getValue();

        assertAll(
            () -> assertEquals(3, registradas.size(), "Deve ter 3 recomendações"),
            () -> assertEquals("O Iluminado", registradas.get(0).getFilme().getTitulo()),
            () -> assertTrue(registradas.get(0).getScore() >= registradas.get(2).getScore())
        );
    }

    @Test
    @DisplayName("Deve sortear filme específico no modo aleatório")
    public void deveRecomendarAleatorio() {
        when(catalogo.buscarTodos()).thenReturn(filmesFake);
        // Sorteia o índice 1 da lista filtrada (A Chegada)
        when(gerador.sortearInteiro(anyInt(), anyInt())).thenReturn(1);

        Optional<Recomendacao> rec = service.recomendarAleatorio(maria);

        assertTrue(rec.isPresent());
        assertEquals("A Chegada", rec.get().getFilme().getTitulo());
    }

}
