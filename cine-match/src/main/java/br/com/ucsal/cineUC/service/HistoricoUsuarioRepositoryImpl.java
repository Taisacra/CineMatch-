package br.com.ucsal.cineUC.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.ucsal.cineUC.model.Filme;
import br.com.ucsal.cineUC.model.Recomendacao;
import br.com.ucsal.cineUC.model.Usuario;

public class HistoricoUsuarioRepositoryImpl implements HistoricoUsuarioRepository {

    @Override
    public void registrarRecomendacao(Usuario user, List<Recomendacao> recomendacoes) {
    }

    @Override
    public List<Filme> buscarHistorico(Usuario user) {
        return new ArrayList<>();
    }

    @Override
    public Map<Filme, Integer> buscarNotas(Usuario user) {
        return new HashMap<>();
    }
}