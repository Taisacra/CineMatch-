package br.com.ucsal.cine_match.model;

import java.util.List;

import br.com.ucsal.cine_match.model.enums.ClassificacaoEtaria;
import br.com.ucsal.cine_match.model.enums.Genero;
import br.com.ucsal.cine_match.model.enums.Idioma;

public class Filme {
	
	private Long idFilme;
	private String titulo;
	private int ano;
	private int duracao;
	private List<Genero> generos;
	private ClassificacaoEtaria classificacao;
	private Idioma idioma;
	private int popularidade;
	
	public Filme(Long idFilme, String titulo, int ano, int duracao, List<Genero> generos,
			ClassificacaoEtaria classificacao, Idioma idioma, int popularidade) {
		this.idFilme = idFilme;
		this.titulo = titulo;
		this.ano = ano;
		this.duracao = duracao;
		this.generos = generos;
		this.classificacao = classificacao;
		this.idioma = idioma;
		this.popularidade = popularidade;
	}

	public Long getIdFilme() {
		return idFilme;
	}

	public String getTitulo() {
		return titulo;
	}

	public int getAno() {
		return ano;
	}

	public int getDuracao() {
		return duracao;
	}

	public List<Genero> getGeneros() {
		return generos;
	}

	public ClassificacaoEtaria getClassificacao() {
		return classificacao;
	}
			
	public Idioma getIdioma() {
		return idioma;
	}

	public int getPopularidade() {
		return popularidade;
	}
	
	

}
