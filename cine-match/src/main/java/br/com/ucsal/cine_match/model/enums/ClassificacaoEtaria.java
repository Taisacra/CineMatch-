package br.com.ucsal.cine_match.model.enums;

public enum ClassificacaoEtaria {

	LIVRE(1L, "LIVRE", 0),
    DEZ(2L, "DEZ", 10),
    DOZE(3L, "DOZE", 12),
    QUATORZE(4L, "QUATORZE", 14),
    DEZESSEIS(5L, "DEZESSEIS", 16),
    DEZOITO(6L, "DEZOITO", 18);
	
	private final Long idClassificacao;
    private final String classificacao;
    private final int valorClassificacao;
    
	private ClassificacaoEtaria(Long idClassificacao, String classificacao, int valorClassificacao) {
		this.idClassificacao = idClassificacao;
		this.classificacao = classificacao;
		this.valorClassificacao = valorClassificacao;
	}

	public Long getIdClassificacao() {
		return idClassificacao;
	}

	public String getClassificacao() {
		return classificacao;
	}
    
	public int getValorClassificacao() {
		return valorClassificacao;
	}


}
