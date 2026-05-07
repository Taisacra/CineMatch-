package br.com.ucsal.cineUC.model.enums;

public enum Genero {

	ACAO(1L, "ACAO"),
    COMEDIA(2L, "COMEDIA"),
    DRAMA(3L, "DRAMA"),
    FICCAO_CIENTIFICA(4L, "FICCAO_CIENTIFICA"),
    ROMANCE(5L, "ROMANCE"),
    TERROR(6L, "TERROR"),
    DOCUMENTARIO(7L, "DOCUMENTARIO");

	private final Long idGenero;
    private final String genero;
    
	private Genero(Long idGenero, String genero) {
		this.idGenero = idGenero;
		this.genero = genero;
	}

	public Long getIdGenero() {
		return idGenero;
	}

	public String getGenero() {
		return genero;
	}
    
    
	
}
