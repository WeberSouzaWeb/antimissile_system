package br.com.game.entidades;

import br.com.game.sistema.Localizacao;

import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;
import java.util.Date;

public class AlvoMovel extends Thread {

    private Rectangle rentangulo;
    private Integer identificacao;
    private Localizacao pontoOrigem;
    public Integer caminho;
    private Localizacao pontoDestino;
    private Localizacao localizacaoAtual;
    private Tamanho tamanho = new Tamanho(50,50);
    public ImageIcon imageAlvo = new ImageIcon("src/main/resources/sprites/Alvo.png");
    private long timestamp ;
    //private Date aux = new Date();
    private long frequenciaAttPosicao;
    private boolean chegadaNoDestino = false;
    private boolean atingido = false;
    private boolean visibilidade;
    double Yaleat = Math.random()/0.01;
    int Yfinal = (int) Math.round(Yaleat);
    private static int totalAlvos = 0;

    public AlvoMovel(Integer caminho, long timestamp) {
        AlvoMovel.totalAlvos++;
        this.identificacao = AlvoMovel.totalAlvos;
       // this.timestamp = aux.getTime();
        this.timestamp = timestamp;
        this.caminho = caminho;
        this.visibilidade = true;
        this.frequenciaAttPosicao = 100;
        if(caminho == 1){
            this.pontoOrigem = new Localizacao(80,Yfinal);
            this.pontoDestino = new Localizacao(80,500);
        } else if(caminho == 2){
            this.pontoOrigem = new Localizacao(380,0);
            this.pontoDestino = new Localizacao(380,500);
        }
        this.localizacaoAtual = this.pontoOrigem;
        this.setRentangulo();
        start();
    }

    public boolean getAtingido(){

        return atingido;
    }
    public ImageIcon getImageAlvo() {

        return imageAlvo;
    }


    public Integer getIdentificacao() {

        return identificacao;
    }

    public long getTimestamp() {

        return timestamp;
    }
    public Localizacao getPontoOrigem() {

        return pontoOrigem;
    }

    public long getFrequenciaAttPosicao() {
        return frequenciaAttPosicao;
    }

    public void setFrequenciaAttPosicao(long frequenciaAttPosicao) {
        this.frequenciaAttPosicao = frequenciaAttPosicao;
    }

    public boolean getChegadaNoDestino() {
        return chegadaNoDestino;
    }

    public void setChegadaNoDestino(boolean chegadaNoDestino) {
        this.chegadaNoDestino = chegadaNoDestino;
    }

    public void setAtingido(boolean atingido) {
        this.atingido = atingido;

    }


    public Localizacao getPontoDestino() {
        return pontoDestino;
    }

    public Localizacao getLocalizacaoAtual() {
        return localizacaoAtual;
    }

    public void setLocalizacaoAtual(Localizacao localizacaoAtual) {
        this.localizacaoAtual = localizacaoAtual;
        this.rentangulo.x = localizacaoAtual.getVarX();
        this.rentangulo.y = localizacaoAtual.getVarY();
    }

    public Rectangle getRentangulo(){
        return this.rentangulo;
    }

    private void setRentangulo(){
        this.rentangulo = new Rectangle();
        this.rentangulo.setBounds(localizacaoAtual.getVarX(),localizacaoAtual.getVarY(),this.tamanho.getVarW(),this.tamanho.getVarH());
    }

    public void moveAlvo() {
        if (this.getLocalizacaoAtual().getVarY() < getPontoDestino().getVarY()) {
            this.setLocalizacaoAtual(new Localizacao(this.getPontoOrigem().getVarX(), this.getLocalizacaoAtual().getVarY() + 2));
            System.out.println(this.getLocalizacaoAtual().getVarY());
            if (this.getLocalizacaoAtual().getVarY() > 500) {
                this.setLocalizacaoAtual(new Localizacao(80, 0));
            }
        }
    }

    public void run() {
        while (true) {
            try {
                sleep(getFrequenciaAttPosicao());
                moveAlvo();
                if (chegadaNoDestino || atingido) {
                    this.interrupt();
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
