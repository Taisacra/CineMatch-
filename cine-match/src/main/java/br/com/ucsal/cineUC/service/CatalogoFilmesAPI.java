package br.com.ucsal.cineUC.service;

import java.util.List;

import br.com.ucsal.cineUC.model.Filme;

public interface CatalogoFilmesAPI {
	
	List<Filme> buscarTodos();
	
}
