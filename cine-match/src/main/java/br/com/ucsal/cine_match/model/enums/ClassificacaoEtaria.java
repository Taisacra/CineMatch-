package br.com.ucsal.cine_match.model.enums;

public enum ClassificacaoEtaria {

    LIVRE(1L, "LIVRE"),
    DEZ(2L, "DEZ"),
    DOZE(3L, "DOZE"),
    QUATORZE(4L, "QUATORZE"),
    DEZESSEIS(5L, "DEZESSEIS"),
    DEZOITO(6L, "DEZOITO");

	private final Long idClassificacao;
    private final String classificacao;
    
	private ClassificacaoEtaria(Long idClassificacao, String classificacao) {
		this.idClassificacao = idClassificacao;
		this.classificacao = classificacao;
	}

	public Long getIdClassificacao() {
		return idClassificacao;
	}

	public String getClassificacao() {
		return classificacao;
	}
    


}
