package br.com.ucsal.cineUC.model;

public class Usuario {
	
	private Long id;
	private String nome;
	private int idade;
	private PerfilCinefilo perfilCinefilo;
	private boolean notificacoesAtivadas;
	
	public Usuario(Long id, String nome, int idade,
			PerfilCinefilo perfilCinefilo, boolean notificacoesAtivadas) {
		
		this.id = id;
		this.nome = nome;
		this.idade = idade;
		this.perfilCinefilo = perfilCinefilo;
		this.notificacoesAtivadas = notificacoesAtivadas;
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public int getIdade() {
		return idade;
	}

	public PerfilCinefilo getPerfilCinefilo() {
		return perfilCinefilo;
	}
	
	public boolean isNotificacoesAtivadas() {
		return notificacoesAtivadas;
	}
	
	public void ativarNotificacoes() {
		this.notificacoesAtivadas = true;
	}
	
	public void desativarNotificacoes() {
		this.notificacoesAtivadas = false;
	}


	
}
