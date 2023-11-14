package br.com.game.entidades;

public class Municao {

    private int identificacao;
    private boolean utilizada = false;
    private int qtd_balas = 0;

    public Municao() {
        this.qtd_balas += 1;
        this.identificacao += 1;
    }
}
