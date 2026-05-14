package br.com.ucsal.cineUC;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import br.com.ucsal.cineUC.model.PerfilCinefilo;
import br.com.ucsal.cineUC.model.Recomendacao;
import br.com.ucsal.cineUC.model.Usuario;
import br.com.ucsal.cineUC.model.enums.ClassificacaoEtaria;
import br.com.ucsal.cineUC.model.enums.Genero;
import br.com.ucsal.cineUC.model.enums.Idioma;
import br.com.ucsal.cineUC.service.CalculadoraScore;
import br.com.ucsal.cineUC.service.CatalogoFilmesAPI;
import br.com.ucsal.cineUC.service.CatalogoMock;
import br.com.ucsal.cineUC.service.FiltroFilmes;
import br.com.ucsal.cineUC.service.HistoricoUsuarioRepository;
import br.com.ucsal.cineUC.service.HistoricoUsuarioRepositoryImpl;
import br.com.ucsal.cineUC.service.NotificadorPush;
import br.com.ucsal.cineUC.service.NotificadorPushImpl;
import br.com.ucsal.cineUC.service.RecomendadorService;
import br.com.ucsal.cineUC.util.GeradorAleatorio;
import br.com.ucsal.cineUC.util.GeradorAleatorioImpl;

public class App 
{
    public static void main( String[] args ){
    	 Scanner scan = new Scanner(System.in);
    	 Usuario usuario = cadastrarUsuario(scan);
    	 CatalogoFilmesAPI catalogo = new CatalogoMock();
    	 
    	 HistoricoUsuarioRepository historico = new HistoricoUsuarioRepositoryImpl();
    	 NotificadorPush notificador = new NotificadorPushImpl();
    	 GeradorAleatorio gerador = new GeradorAleatorioImpl();
    	 CalculadoraScore calculadora = new CalculadoraScore();
    	 FiltroFilmes filtro = new FiltroFilmes();
    	 
    	 RecomendadorService recomendador = new RecomendadorService(
    			 catalogo, historico, notificador, gerador, calculadora, filtro
    	);
    	 
    	 if (usuario != null) {
             System.out.println("\nUsuário " + usuario.getNome() + " cadastrado com sucesso!");
             exibirPerfil(usuario);
             
             exibirRecomendacoes(scan, usuario, recomendador);
         }
    	
         scan.close();
    }
    
    public static Usuario cadastrarUsuario(Scanner scan) {
    	System.out.println("******************* CADASTRO USUÁRIO*******************");
    	
    	try {
    		
    		 // dados básicos
            System.out.print("ID do usuário: ");
            Long idUsuario = scan.nextLong();
            scan.nextLine(); // Limpa buffer

            System.out.print("Nome: ");
            String nome = scan.nextLine();

            System.out.print("Idade: ");
            int idade = scan.nextInt();
            scan.nextLine(); // Limpa buffer

            //perfil cinefilo
            System.out.print("Duração mínima preferida (minutos): ");
            int duracaoMin = scan.nextInt();

            System.out.print("Duração máxima preferida (minutos): ");
            int duracaoMax = scan.nextInt();
            scan.nextLine(); // Limpa buffer
            
            //  classificação etária (VALIDAÇÃO DE ID) 
            ClassificacaoEtaria classificacaoEscolhida = null;
            while (classificacaoEscolhida == null) {
                System.out.println("\nEscolha a classificação máxima:");
                for (ClassificacaoEtaria c : ClassificacaoEtaria.values()) {
                    System.out.println(c.getIdClassificacao() + " - " + c.getClassificacao());
                }
                System.out.print("Digite o ID desejado: ");

                try {
                    Long idClass = scan.nextLong();
                    for (ClassificacaoEtaria c : ClassificacaoEtaria.values()) {
                        if (c.getIdClassificacao().equals(idClass)) {
                            classificacaoEscolhida = c;
                            break;
                        }
                    }
                    if (classificacaoEscolhida == null) {
                        System.out.println("Erro: ID inexistente. Tente novamente.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Erro: Entrada inválida! Digite apenas o número do ID.");
                    scan.nextLine(); // Limpa buffer
                }
            }

            PerfilCinefilo perfil = new PerfilCinefilo(duracaoMin, duracaoMax, classificacaoEscolhida);
            
            // peso generos (VALIDAÇÃO DE ENTRADA) 
            System.out.println("\n===== PESOS DOS GÊNEROS (0.0 a 1.0) =====");
            for (Genero genero : Genero.values()) {
                boolean pesoValido = false;
                while (!pesoValido) {
                    try {
                        System.out.print(genero.getIdGenero() + " - " + genero.getGenero() + " | Peso: ");
                        double peso = scan.nextDouble();
                        
                        if (peso < 0.0 || peso > 1.0) {
                            System.out.println("Erro: O peso deve estar entre 0.0 e 1.0.");
                        } else {
                            perfil.adicionarPesoGenero(genero, peso);
                            pesoValido = true;
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Erro: Use números decimais (ex: 0,5).");
                        scan.nextLine(); // Limpa buffer
                    }
                }
            }
            
         //  idiomas (VALIDAÇÃO DE ID) 
            System.out.println("\n===== IDIOMAS DISPONÍVEIS =====");
            for (Idioma idioma : Idioma.values()) {
                System.out.println(idioma.getIdIdioma() + " - " + idioma.getIdioma());
            }

            int qtd = 0;
            boolean qtdValida = false;
            while (!qtdValida) {
                try {
                    System.out.print("Quantos idiomas deseja adicionar? ");
                    qtd = scan.nextInt();
                    if (qtd < 0) {
                        System.out.println("Erro: A quantidade não pode ser negativa.");
                    } else {
                        qtdValida = true;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Erro: Digite um número inteiro.");
                    scan.nextLine();
                }
            }
            
            for (int i = 0; i < qtd; i++) {
                boolean idiomaAdicionado = false;
                while (!idiomaAdicionado) {
                    try {
                        System.out.print("Digite o ID do idioma (" + (i + 1) + "/" + qtd + "): ");
                        Long idId = scan.nextLong();
                        
                        for (Idioma idm : Idioma.values()) {
                            if (idm.getIdIdioma().equals(idId)) {
                                perfil.adicionarIdioma(idm);
                                idiomaAdicionado = true;
                                break;
                            }
                        }
                        
                        if (!idiomaAdicionado) {
                            System.out.println("Erro: ID de idioma inválido! Tente novamente.");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Erro: Digite apenas o número do ID.");
                        scan.nextLine();
                    }
                }
            }
            
            //notificação
            System.out.print("\nDeseja ativar notificações? (sim/nao): ");
            
            String resposta = scan.next();
            boolean notificacoes = false;
            
            if(resposta.equals("sim")) {
            	notificacoes = true;
            }else if(resposta.equals("nao")) {
            	notificacoes = false;
            }else {
            	System.out.println("Erro: Responda com 'sim' ou 'nao'.");
                scan.next();
            }
                     
            return new Usuario(idUsuario, nome, idade, perfil, notificacoes);
    		
    	}catch(InputMismatchException e) {
    		System.out.println("\nEntrada de dados inesperada. O cadastro foi interrompido.");
            return null;
    	}
   	}
    
    public static void exibirPerfil(Usuario usuario) {
    	PerfilCinefilo perfil = usuario.getPerfilCinefilo();
    	
    	System.out.println("Nome: " + usuario.getNome());
        System.out.println("Idade: " + usuario.getIdade());
        
        System.out.println("Preferências de gênero (peso de 0.0 a 1.0):");
        for (Genero g : Genero.values()) {
            double peso = perfil.getPesoPorGenero(g);
            String infoExtra = (peso == 0.0) ? "  (não gosta)" : "";
            System.out.println(g.getGenero() + " : " + peso + infoExtra);
        }
        
        System.out.println("\nDuração preferida: " + perfil.getDuracaoMin() + " a " + perfil.getDuracaoMax() + " minutos");
        
        System.out.println("Classificação máxima: " + perfil.getClassificacaoMaxima() + " anos");
        
        System.out.println("Idiomas aceitos: " + perfil.getIdiomas());

    }
    	
    public static void exibirRecomendacoes(
            Scanner scan,
            Usuario usuario,
            RecomendadorService recomendador) {

        System.out.print("\nQuantas recomendações deseja receber? ");
        int topN = scan.nextInt();

        System.out.println("\n===== RECOMENDAÇÕES =====");

        List<Recomendacao> recomendacoes =
                recomendador.recomendar(usuario, topN);

        if (recomendacoes.isEmpty()) {
            System.out.println("Nenhuma recomendação encontrada.");
        } else {
            for (Recomendacao r : recomendacoes) {
                System.out.println("Filme: " + r.getFilme().getTitulo());
                System.out.println("Score: " + r.getScore());
                System.out.println("Justificativa: " + r.getJustificativa());
                System.out.println("---------------------------");
            }
        }
    }
}
