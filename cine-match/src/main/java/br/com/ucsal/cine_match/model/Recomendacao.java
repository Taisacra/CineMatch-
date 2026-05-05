package br.com.ucsal.cine_match.model;

public class Recomendacao {
	
	 private Long idRecomendacao;
	 private Filme filme;
	 private double score;
	 private String justificativa;

	 public Recomendacao(Long idRecomendacao, Filme filme, double score, String justificativa) {
		
		 this.idRecomendacao = idRecomendacao;
	     this.filme = filme;
	     this.score = score;
	     this.justificativa = justificativa;
	 }

	 public Long getIdRecomendacao() {
		 return idRecomendacao;
	 }

	 public Filme getFilme() {
		 return filme;
	 }

	 public double getScore() {
		 return score;
	 }

	 public String getJustificativa() {
		 return justificativa;
	 }

}
