package br.com.ucsal.cine_match.service;

import java.util.List;

import br.com.ucsal.cine_match.model.Filme;

public interface CatalogoFilmesAPI {
	
	List<Filme> buscarTodos();
	
}
