package br.com.ucsal.cine_match;

import java.util.Scanner;

import br.com.ucsal.cine_match.model.Usuario;
import br.com.ucsal.cine_match.model.enums.ClassificacaoEtaria;

public class App 
{
    public static void main( String[] args ){
    	 Scanner scan = new Scanner(System.in);
    	 Usuario usuario = cadastrarUsuario(scan);
    	 
    	
         scan.close();
    }
    
    public static Usuario cadastrarUsuario(Scanner scan) {
    	System.out.println("******************* CADASTRO USUÁRIO*******************");
    	
    	//Usuário
        System.out.print("ID do usuário: ");
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
        System.out.println("\nEscolha a classificação máxima:");

        for (ClassificacaoEtaria classificacao : ClassificacaoEtaria.values()) {

            System.out.println(
                    classificacao.getIdClassificacao()
                    + " - "
                    + classificacao.getClassificacao()
            );
        }
        
        Long idClassificacao = scan.nextLong();

        
		return null;
    }
}
