package br.com.ucsal.cineUC.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
    void setup() {
        
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
    @DisplayName("Deve recomendar filmes com sucesso e registrar no histórico (Stub Básico e Verify)")
    void deveRecomendarComSucesso() {
        // 1. when(...).thenReturn(...) - Stub básico
        when(catalogo.buscarTodos()).thenReturn(filmesFake);
        
        // Execução
        List<Recomendacao> resultado = service.recomendar(maria, 2);

        // Asserts
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        
        // 4 e 5. verify(...) com Matchers - Confirmar chamadas
        // Verificamos se registrou no histórico para a maria e qualquer lista de recomendações
        verify(historico).registrarRecomendacao(eq(maria), anyList());
        
        // Verificar se notificou já que as notificações da maria estão ativadas
        verify(notificador, times(1)).enviar(eq(maria), anyString());
    }
    
    @Test
    @DisplayName("Deve retornar lista vazia e não notificar quando a API falhar (Simular Falha)")
    void deveTratarFalhaNoCatalogo() {
        // 2. when(...).thenThrow(...) - Simular falha de dependência externa
        when(catalogo.buscarTodos()).thenThrow(new RuntimeException("API Offline"));

        List<Recomendacao> resultado = service.recomendar(maria, 5);

        assertTrue(resultado.isEmpty());
        // verify(..., never()) - Garantir que comportamentos indesejados não ocorreram
        verify(notificador, never()).enviar(any(), anyString());
    }
    
    @Test
    @DisplayName("Deve usar stub sequencial para desempate aleatório na ordenação")
    void deveUsarStubSequencialNoDesempate() {
        when(catalogo.buscarTodos()).thenReturn(filmesFake);
        
        // 3. Stub sequencial - Simula múltiplos retornos para chamadas seguidas do gerador
        // Usado dentro do Comparator no método ordenarRecomendacoes
        when(gerador.sortearInteiro(0, 1)).thenReturn(0, 1, 0);

        List<Recomendacao> resultado = service.recomendar(maria, 3);
        
        assertFalse(resultado.isEmpty());
        verify(gerador, atLeastOnce()).sortearInteiro(0, 1);
    }
    
    @Test
    @DisplayName("Deve capturar a recomendação enviada para validar detalhes (ArgumentCaptor)")
    void deveInspecionarRecomendacoesRegistradas() {
        when(catalogo.buscarTodos()).thenReturn(filmesFake);
        
        // 6. ArgumentCaptor - Inspecionar o que foi passado ao mock
        ArgumentCaptor<List<Recomendacao>> captor = ArgumentCaptor.forClass(List.class);

        service.recomendar(maria, 1);

        verify(historico).registrarRecomendacao(eq(maria), captor.capture());
        List<Recomendacao> gravadas = captor.getValue();

        assertEquals(1, gravadas.size());
        assertEquals("O Iluminado", gravadas.get(0).getFilme().getTitulo());
    }

    @Test
    @DisplayName("Deve recomendar um filme aleatório sorteado pelo gerador")
    void deveRecomendarAleatorio() {
        when(catalogo.buscarTodos()).thenReturn(filmesFake);
        // Filtro deixará passar os 3 filmes. Sorteamos o índice 1 (Duro de Matar)
        when(gerador.sortearInteiro(0, 2)).thenReturn(1);

        Optional<Recomendacao> resultado = service.recomendarAleatorio(maria);

        assertTrue(resultado.isPresent());
        assertEquals("Duro de Matar", resultado.get().getFilme().getTitulo());
    }

}
