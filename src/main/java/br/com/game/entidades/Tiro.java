package br.com.game.entidades;

import br.com.game.sistema.Localizacao;
import br.com.game.start.Game;

import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;

public class Tiro extends Thread {

    private Rectangle retangulo;
    private int identificacao;
    private Municao municao;
    private Tamanho tamanho = new Tamanho(30,32);
    private ImageIcon imageTiro =  new ImageIcon("src/main/resources/sprites/Tiro.png");
    private Localizacao pontoOrigem = new Localizacao(215,440);
    private Localizacao pontoDestino;
    private Localizacao localizacaoAtual;
    private Timestamp timestamp;
    private long freqAttPosicao;
    private boolean contatoAlvo;
    private boolean visibilidade;

    public Tiro(){
        this.setRentangulo();
        this.visibilidade = true;
        this.identificacao = 1;
        start();
    }


    public ImageIcon getImageTiro() {
        return imageTiro;
    }

    public void setImageTiro(ImageIcon imageTiro) {
        this.imageTiro = imageTiro;
    }

    public Integer getIdentificacao() {
        return identificacao;
    }

    public void setIdentificacao(Integer identificacao) {
        this.identificacao = identificacao;
    }

    public Municao getMunicao() {
        return municao;
    }

    public void setMunicao(Municao municao) {
        this.municao = municao;
    }

    public Localizacao getPontoOrigem() {
        return pontoOrigem;
    }

    public void setPontoOrigem(Localizacao pontoOrigem) {
        this.pontoOrigem = pontoOrigem;
    }

    public Localizacao getPontoDestino() {
        return this.pontoDestino;
    }

    public void setPontoDestino(Localizacao pontoDestino) {
        this.pontoDestino = pontoDestino;
    }

    public Localizacao getLocalizacaoAtual() {
        return localizacaoAtual;
    }

    public void setLocalizacaoAtual(Localizacao localizacaoAtual) {
        this.localizacaoAtual = localizacaoAtual;
        this.retangulo.x = localizacaoAtual.getVarX();
        this.retangulo.y = localizacaoAtual.getVarY();
    }

    public void setVisible(boolean visibilidade) {
        this.visibilidade = visibilidade;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public long getFreqAttPosicao() {
        return freqAttPosicao;
    }

    public void setFreqAttPosicao(long freqAttPosicao) {
        this.freqAttPosicao = freqAttPosicao;
    }

    public boolean isContatoAlvo() {
        return this.contatoAlvo;
    }

    public void setContatoAlvo(boolean contatoAlvo) {
        this.contatoAlvo = contatoAlvo;
    }

    public Rectangle getRetangulo(){return this.retangulo;}

    private void setRentangulo(){
        this.retangulo = new Rectangle();
        this.retangulo.setBounds(0,0,this.tamanho.getVarW(),this.tamanho.getVarH());
    }
    public void move_tiro(){
        if(this.getLocalizacaoAtual() == null){
            this.setLocalizacaoAtual(this.getPontoOrigem());
        }
        if(this.getLocalizacaoAtual().getVarX()
                <= 500 && this.getLocalizacaoAtual().getVarX()
                >= 0 && this.getLocalizacaoAtual().getVarY()
                <= 500 && this.getLocalizacaoAtual().getVarY() >= 0){
            try {
                this.setLocalizacaoAtual(
                        Game.geraTrajetoria(
                                this.getPontoOrigem(),this.getPontoDestino()));
            } catch (Exception e){
                e.printStackTrace();
                System.out.println("Não converteu");
            }
        }
    }
    public void run() {

        while (true) {
            try {
                sleep(30);
                move_tiro();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


