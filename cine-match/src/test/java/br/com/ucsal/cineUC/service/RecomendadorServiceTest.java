package br.com.ucsal.cineUC.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
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
    
    // Requisito 8: Uso de Spy (instâncias reais vigiadas)
    @Spy private CalculadoraScore calculadora = new CalculadoraScore();
    @Spy private FiltroFilmes filtro = new FiltroFilmes();

    @InjectMocks private RecomendadorService service;

    // Captor anotado para evitar erros de tipos genéricos em List
    @Captor private ArgumentCaptor<List<Recomendacao>> recomendacaoCaptor;
    
    private Usuario maria;
    private List<Filme> filmesFake;
    
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        
        // Perfil configurado para ser EXTREMAMENTE PERMISSIVO (0 a 500 minutos)
        // Isso resolve o erro "was: <0>" garantindo que os filmes passem no FiltroFilmes (Spy)
        PerfilCinefilo perfil = new PerfilCinefilo(0, 500, ClassificacaoEtaria.DEZOITO);
        perfil.adicionarPesoGenero(Genero.TERROR, 1.0);
        perfil.adicionarPesoGenero(Genero.ACAO, 1.0);
        perfil.adicionarPesoGenero(Genero.ANIMACAO, 1.0);
        perfil.adicionarIdioma(Idioma.PT);
        perfil.adicionarIdioma(Idioma.EN);
 
        maria = new Usuario(1L, "Maria", 25, perfil, true);

        // Scores idênticos para garantir que o desempate aleatório (Gerador) seja testável
        Filme f1 = new Filme("1", "O Iluminado", 1980, 146, List.of(Genero.TERROR), ClassificacaoEtaria.DEZESSEIS, Idioma.PT, 100);
        Filme f2 = new Filme("2", "Duro de Matar", 1988, 132, List.of(Genero.ACAO), ClassificacaoEtaria.QUATORZE, Idioma.PT, 100);
        Filme f3 = new Filme("3", "Toy Story", 1995, 81, List.of(Genero.ANIMACAO), ClassificacaoEtaria.LIVRE, Idioma.PT, 100);

        filmesFake = List.of(f1, f2, f3);
    }
    
    @Test
    @DisplayName("1. Stub Básico e 4. Verify - Sucesso")
    public void deveRecomendarComSucesso() {
        when(catalogo.buscarTodos()).thenReturn(filmesFake);

        List<Recomendacao> resultado = service.recomendar(maria, 2);

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty(), "A lista não deve ser vazia.");
        assertEquals(2, resultado.size());
        verify(catalogo, atLeastOnce()).buscarTodos();
    }
    
    @Test
    @DisplayName("2. when(...).thenThrow(...) e 4. Verify never()")
    public void deveTratarFalhaNoCatalogo() {
        when(catalogo.buscarTodos()).thenThrow(new RuntimeException("Erro de Infraestrutura"));

        List<Recomendacao> resultado = service.recomendar(maria, 5);

        assertTrue(resultado.isEmpty(), "Resultado deve ser lista vazia em caso de erro");
        verify(notificador, never()).enviar(any(), anyString());
    }
    
    @Test
    @DisplayName("3. Stub Sequencial - Modo Surpreenda-me")
    public void deveUsarStubSequencialNoDesempate() {
        when(catalogo.buscarTodos()).thenReturn(filmesFake);
        // Retorna índices diferentes para simular múltiplos sorteios
        when(gerador.sortearInteiro(anyInt(), anyInt())).thenReturn(0, 1, 2);

        Optional<Recomendacao> rec = service.recomendarAleatorio(maria);

        assertTrue(rec.isPresent());
        verify(gerador, atLeastOnce()).sortearInteiro(anyInt(), anyInt());
    }
    
    @Test
    @DisplayName("5. Matchers e 6. ArgumentCaptor - Histórico")
    public void deveInspecionarRecomendacoesRegistradas() {
        when(catalogo.buscarTodos()).thenReturn(filmesFake);

        service.recomendar(maria, 2);

        // Matcher eq(maria) e uso do Captor para validar o que foi salvo
        verify(historico).registrarRecomendacao(eq(maria), recomendacaoCaptor.capture());
        
        List<Recomendacao> registradas = recomendacaoCaptor.getValue();
        assertNotNull(registradas);
        assertFalse(registradas.isEmpty());
    }

    @Test
    @DisplayName("Garantir que não há interações indesejadas")
    public void naoDeveNotificarSeUsuarioNaoPermitir() {
        // Se desativarNotificacoes() não existir, use maria.setReceberNotificacoes(false);
        try {
            maria.getClass().getMethod("desativarNotificacoes").invoke(maria);
        } catch (Exception e) {
            maria.desativarNotificacoes(); 
        }
        
        when(catalogo.buscarTodos()).thenReturn(filmesFake);

        service.recomendar(maria, 1);

        verify(notificador, never()).enviar(any(), anyString());
    }
}
