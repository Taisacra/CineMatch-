package br.com.ucsal.cineUC.util;

import java.util.Random;

public class GeradorAleatorioImpl implements GeradorAleatorio {

    private Random random = new Random();

    @Override
    public int sortearInteiro(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }
}