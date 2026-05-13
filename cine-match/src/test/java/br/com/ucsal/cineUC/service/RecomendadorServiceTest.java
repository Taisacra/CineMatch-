package br.com.ucsal.cineUC.service;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.ucsal.cineUC.model.Filme;
import br.com.ucsal.cineUC.model.PerfilCinefilo;
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

        // 3. Preparar lista de filmes fake (Objetos de domínio reais)
        Filme filme1 = new Filme(
                "1", 
                "O Iluminado", 
                1980, 
                146, 
                List.of(Genero.TERROR), 
                ClassificacaoEtaria.DEZESSEIS, 
                Idioma.EN, 
                95
            );

            Filme filme2 = new Filme(
                "2", 
                "Toy Story", 
                1995, 
                81, 
                List.of(Genero.ACAO, Genero.ROMANCE), 
                ClassificacaoEtaria.LIVRE, 
                Idioma.EN, 
                90
            );

            filmesFake = List.of(filme1, filme2);
    }

}
