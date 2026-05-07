package br.com.ucsal.cine_match;

import java.util.InputMismatchException;
import java.util.Scanner;

import br.com.ucsal.cine_match.model.PerfilCinefilo;
import br.com.ucsal.cine_match.model.Usuario;
import br.com.ucsal.cine_match.model.enums.ClassificacaoEtaria;
import br.com.ucsal.cine_match.model.enums.Genero;
import br.com.ucsal.cine_match.model.enums.Idioma;

public class App 
{
    public static void main( String[] args ){
    	 Scanner scan = new Scanner(System.in);
    	 Usuario usuario = cadastrarUsuario(scan);
    	 
    	
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
            System.out.print("\nDeseja ativar notificações? (true/false): ");
            while (!scan.hasNextBoolean()) {
                System.out.println("Erro: Responda com 'true' ou 'false'.");
                scan.next();
            }
            boolean notificacoes = scan.nextBoolean();

            return new Usuario(idUsuario, nome, idade, perfil, notificacoes);
    		
    	}catch(InputMismatchException e) {
    		System.out.println("\nEntrada de dados inesperada. O cadastro foi interrompido.");
            return null;
    	}
   	}
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	//Usuário
        /*System.out.print("ID do usuário: ");
        Long idUsuario = scan.nextLong();
        scan.nextLine();

        System.out.print("Nome: ");
        String nome = scan.nextLine();

        System.out.print("Idade: ");
        int idade = scan.nextInt();
        
        //Perfil Cinefilo
        System.out.print("Duração mínima preferida: ");
        int duracaoMin = scan.nextInt();

        System.out.print("Duração máxima preferida: ");
        int duracaoMax = scan.nextInt();
        
        //Escolher a classificação etária
        ClassificacaoEtaria classificacaoEscolhida = null;
        while (classificacaoEscolhida == null) {
        	
        	System.out.println("\nEscolha a classificação máxima:");

            for (ClassificacaoEtaria classificacao : ClassificacaoEtaria.values()) {
                System.out.println(classificacao.getIdClassificacao() + " - " + classificacao.getClassificacao());
            }
            System.out.print("Digite o ID desejado: ");
            
            try {
            	 Long idClassificacao = scan.nextLong();
            	 
            	 for(ClassificacaoEtaria c : ClassificacaoEtaria.values()) {
            		 if (c.getIdClassificacao().equals(idClassificacao)) {
                         classificacaoEscolhida = c;
                         break; 
                     } 
            	 }
            	 
            	 if(classificacaoEscolhida == null) {
            		 System.out.println("ID inexistente no sistema. Tente novamente.");
            	 }
            }catch(InputMismatchException e) {
            	// Se o utilizador digitar letras, o nextLong() falha
            	System.out.println("Erro: Entrada inválida! Por favor, digite apenas números.");
                scan.nextLine();
            }
        }
        
        PerfilCinefilo perfil = new PerfilCinefilo(
                duracaoMin,
                duracaoMax,
                classificacaoEscolhida
        );
        
        //Peso dos generos
        System.out.println("\n===== PESOS DOS GÊNEROS =====");

        for (Genero genero : Genero.values()) {

            System.out.print(
                    genero.getIdGenero()
                    + " - "
                    + genero.getGenero()
                    + " | Peso (0.0 a 1.0): "
            );

            double peso = scan.nextDouble();

            perfil.adicionarPesoGenero(genero, peso);
        }
        
        //Idiomas
        System.out.println("\n===== IDIOMAS =====");

        for (Idioma idioma : Idioma.values()) {

            System.out.println(
                    idioma.getIdIdioma()
                    + " - "
                    + idioma.getIdioma()
            );
        }
        
        System.out.print("Quantos idiomas deseja adicionar? ");
        int quantidadeIdiomas = scan.nextInt();

        for (int i = 0; i < quantidadeIdiomas; i++) {

            System.out.print("Digite o ID do idioma: ");

            Long idIdioma = scan.nextLong();

            for (Idioma idioma : Idioma.values()) {

                if (idioma.getIdIdioma().equals(idIdioma)) {

                    perfil.adicionarIdioma(idioma);
                    break;
                }
            }
        }
        
        System.out.print("\nDeseja ativar notificações? (true/false): ");
        boolean notificacoes = scan.nextBoolean();
        
        Usuario usuario = new Usuario(
                idUsuario,
                nome,
                idade,
                perfil,
                notificacoes
        );

        return usuario;
    }*/
}
