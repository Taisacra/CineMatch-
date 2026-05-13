package br.com.ucsal.cineUC.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import br.com.ucsal.cineUC.exception.DuracaoInvalidaException;
import br.com.ucsal.cineUC.exception.NotaInvalidaException;
import br.com.ucsal.cineUC.exception.PesoInvalidoException;
import br.com.ucsal.cineUC.model.enums.ClassificacaoEtaria;
import br.com.ucsal.cineUC.model.enums.Genero;
import br.com.ucsal.cineUC.model.enums.Idioma;

public class PerfilCinefilo {

	private Map<Genero,Double> pesoPorGenero;
	private int duracaoMin;
	private int duracaoMax;
	private ClassificacaoEtaria classificacaoMaxima;
	private Set<Idioma> idiomas;
	private Set<Filme> historico;
	private Map<Filme, Integer> notas;
	
	

	public PerfilCinefilo(int duracaoMin, int duracaoMax,ClassificacaoEtaria classificacaoMaxima) {
		
		if (duracaoMin > duracaoMax) {
			throw new DuracaoInvalidaException("A duracao mínima deve ser menor que a duração máxima.");
		}
		
		this.duracaoMin = duracaoMin;
		this.duracaoMax = duracaoMax;
		this.classificacaoMaxima = classificacaoMaxima;
	
		this.pesoPorGenero = new HashMap<>();
		this.idiomas = new HashSet<>();
		this.historico = new HashSet<>();
		this.notas = new HashMap<>();
		
	}

	public int getDuracaoMin() {
		return duracaoMin;
	}

	public int getDuracaoMax() {
		return duracaoMax;
	}

	public ClassificacaoEtaria getClassificacaoMaxima() {
		return classificacaoMaxima;
	}
	
	public void adicionarPesoGenero(Genero genero, double peso) {
        if (peso < 0 || peso > 1) {
            throw new PesoInvalidoException("Peso deve estar entre 0 e 1.");
        }
        pesoPorGenero.put(genero, peso);
    }

    public void adicionarIdioma(Idioma idioma) {
        idiomas.add(idioma);
    }

    public void adicionarFilmeAssistido(Filme filme) {
        historico.add(filme);
    }

    public void adicionarNotaFilme(Filme filme, int nota) {
        if (nota < 1 || nota > 5) {
            throw new NotaInvalidaException("Nota deve estar entre 1 e 5.");
        }

        notas.put(filme, nota);
    }
    
    public Double getPesoPorGenero(Genero genero) {
        return pesoPorGenero.getOrDefault(genero, 0.0);
    }

    public Integer getNotaFilme(Filme filme) {
        return notas.get(filme);
    }
    
    public Set<Idioma> getIdiomas() {
        return idiomas;
    }

    public Set<Filme> getHistorico() {
        return historico;
    }

    public Map<Filme, Integer> getNotas() {
        return notas;
    }

    
}
