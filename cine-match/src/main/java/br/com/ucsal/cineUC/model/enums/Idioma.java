package br.com.ucsal.cineUC.model.enums;

public enum Idioma {

	PT(1L, "PT"),
    EN(2L, "EN");
	
	private Long idIdioma;
	private String idioma;
	
	private Idioma(Long idIdioma, String idioma) {
		this.idIdioma = idIdioma;
		this.idioma = idioma;
	}

	public Long getIdIdioma() {
		return idIdioma;
	}

	public String getIdioma() {
		return idioma;
	}
	
	@Override
	public String toString() {
		return this.idioma;
	}
}
