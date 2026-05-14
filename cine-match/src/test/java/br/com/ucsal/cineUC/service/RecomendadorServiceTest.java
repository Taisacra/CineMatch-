package br.com.ucsal.cineUC.service;

import static org.junit.jupiter.api.Assertions.assertAll;
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
    
    // Requisito 8: Uso de Spy para lógica pura
    @Spy private CalculadoraScore calculadora = new CalculadoraScore();
    @Spy private FiltroFilmes filtro = new FiltroFilmes();

    @InjectMocks private RecomendadorService service;
    
    private Usuario maria;
    private List<Filme> filmesFake;
    
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        
        // Perfil configurado para aceitar tudo (Evita que o filtro esvazie a lista)
        PerfilCinefilo perfil = new PerfilCinefilo(60, 240, ClassificacaoEtaria.DEZOITO);
        perfil.adicionarPesoGenero(Genero.TERROR, 1.0);
        perfil.adicionarPesoGenero(Genero.ACAO, 1.0);
        perfil.adicionarIdioma(Idioma.PT);
        perfil.adicionarIdioma(Idioma.EN);
 
        maria = new Usuario(1L, "Maria", 25, perfil, true);

        // IMPORTANTE: Scores idênticos (100) para FORÇAR o desempate aleatório
        Filme f1 = new Filme("1", "O Iluminado", 1980, 146, List.of(Genero.TERROR), ClassificacaoEtaria.DEZESSEIS, Idioma.PT, 100);
        Filme f2 = new Filme("2", "Duro de Matar", 1988, 132, List.of(Genero.ACAO), ClassificacaoEtaria.QUATORZE, Idioma.PT, 100);
        Filme f3 = new Filme("3", "Toy Story", 1995, 81, List.of(Genero.ANIMACAO), ClassificacaoEtaria.LIVRE, Idioma.PT, 100);

        filmesFake = List.of(f1, f2, f3);
    }
    
    @Test
    @DisplayName("1. Stub Básico e 4. Verify")
    public void deveRecomendarComSucesso() {
        when(catalogo.buscarTodos()).thenReturn(filmesFake);

        List<Recomendacao> resultado = service.recomendar(maria, 2);

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty(), "A lista não deve estar vazia para o teste passar");
        verify(catalogo, atLeastOnce()).buscarTodos();
    }
    
    @Test
    @DisplayName("2. when(...).thenThrow(...) e 4. Verify never()")
    public void deveTratarFalhaNoCatalogo() {
        when(catalogo.buscarTodos()).thenThrow(new RuntimeException("Erro de Rede"));

        List<Recomendacao> resultado = service.recomendar(maria, 5);

        assertTrue(resultado.isEmpty());
        verify(notificador, never()).enviar(any(), anyString());
    }
    
    @Test
    @DisplayName("3. Stub Sequencial - Garantir chamada ao Gerador")
    public void deveUsarStubSequencialNoDesempate() {
        when(catalogo.buscarTodos()).thenReturn(filmesFake);
        // Retornos para as várias vezes que o sorteio pode ser chamado
        when(gerador.sortearInteiro(anyInt(), anyInt())).thenReturn(0, 1, 2, 0);

        // Chamamos o método que obrigatoriamente usa o gerador: recomendarAleatorio
        Optional<Recomendacao> rec = service.recomendarAleatorio(maria);

        assertTrue(rec.isPresent());
        // Isto resolve o erro "Wanted but not invoked"
        verify(gerador, atLeastOnce()).sortearInteiro(anyInt(), anyInt());
    }
    
    @Test
    @DisplayName("5. Matchers e 6. ArgumentCaptor")
    public void deveInspecionarRecomendacoesRegistradas() {
        when(catalogo.buscarTodos()).thenReturn(filmesFake);
        ArgumentCaptor<List<Recomendacao>> captor = ArgumentCaptor.forClass(List.class);

        service.recomendar(maria, 2);

        // Uso do matcher eq(maria) e captura do argumento
        verify(historico).registrarRecomendacao(eq(maria), captor.capture());
        
        List<Recomendacao> registradas = captor.getValue();
        assertNotNull(registradas);
        assertEquals(2, registradas.size());
    }
}
