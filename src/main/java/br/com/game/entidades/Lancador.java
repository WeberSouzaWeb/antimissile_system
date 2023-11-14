package br.com.game.entidades;

import br.com.game.sistema.Localizacao;
import br.com.game.start.Game;

import javax.swing.*;
import java.awt.*;
import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Stack;

public class Lancador extends Thread {

    //private ArrayList<Tiro> tiro = new ArrayList<>();
    private Tiro tiro;
    private Tiro tiro2;
    private AlvoMovel alvo;
    private AlvoMovel alvo2;
    private Stack<Municao> carregador = new Stack<Municao>();
    public ImageIcon imageLancador =  new ImageIcon("src/main/resources/sprites/Lançador.png");


    public Lancador(){
        this.carregador.add(new Municao());
        this.carregador.add(new Municao());
        this.carregador.add(new Municao());
        this.carregador.add(new Municao());
        this.carregador.add(new Municao());
        this.alvo = new AlvoMovel(1,new Date().getTime());
        this.alvo = new AlvoMovel(2,new Date().getTime());
       this.tiro = new Tiro();
       start();
    }
    public void setImageLancador(ImageIcon imageLancador) {
        this.imageLancador = imageLancador;
    }

    public ImageIcon getImageLancador() {
        return imageLancador;
    }

    public Tiro getTiro(){
        return this.tiro;
    }

    public void carregar(){
        carregador.pop();
        if (carregador.empty()) {
            carregador.push(new Municao());
            }
        calcula_destino_tiro(this.alvo.getPontoOrigem(),this.alvo.getTimestamp());
        }

    public void preparar(){
       //calcularTrajetoria(alvo,tiro);
        try {
            sleep(30);
            ;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
    //---------------------------------------------------------------------------------
   /* public void preparar(AlvoMovel alvo){
            this.tiro.setPontoDestino(calcularTrajetoria(alvo, this.tiro));
    }*/

    //Dispara o tiro
    /*public void atirar(){
        if(tiro.getLocalizacaoAtual() == null){
            tiro.setLocalizacaoAtual(tiro.getPontoOrigem());
        }
        if(tiro.getLocalizacaoAtual().getVarX()
                <= 500 && tiro.getLocalizacaoAtual().getVarX()
                >= 0 && tiro.getLocalizacaoAtual().getVarY()
                <= 500 && tiro.getLocalizacaoAtual().getVarY() >= 0){
            try {
                tiro.setLocalizacaoAtual(
                        Game.geraTrajetoria(
                                tiro.getPontoOrigem(),tiro.getPontoDestino()));
            } catch (Exception e){
                e.printStackTrace();
                System.out.println("Não converteu");
            }
        }
    }*/
    //---------------------------------------------------------------------------------
    public void atirar(){
        if(tiro.getState().toString().equals("NEW")) {
            tiro.start();
        }
    }
    //---------------------------------------------------------------------------------
    //Calcula a localização final onde o alvo e o tiro irão se encontrar.

    public void calcula_destino_tiro(Localizacao origem,long tempo_alvo){
        long tempo_atual = new Date().getTime();
       long deltaT = (tempo_atual) - tempo_alvo + 95;
      // long deltaT = tempo_atual + 3000;
         System.out.println("Tempo atual " + tempo_atual + " Alvo " + tempo_alvo);
         System.out.println("Tempo delta" + deltaT);
        int atualizacoes = (int) deltaT/30;
        int destiny = atualizacoes*2;
        System.out.println("Ponto destino " + origem.getVarX() +"," +destiny);
        tiro.setPontoDestino(new Localizacao(origem.getVarX(),destiny));

        /* Tendo o tempo que o alvo percorreu eh possivel determinar quantas atualizaçoes na posicao teve
        *  então multiplicando pela velocidade de locomoção é possivel determinar onde estara na atualizacao
        * seguinte e encontrar o PONTO Y, visto que X eh fixo*/

    }
    //---------------------------------------------------------------------------------
    /*public Localizacao calcularTrajetoria(AlvoMovel alvoMovel, Tiro tiro){
        // CALCULA O TRIANGULO RETANGULO TOTAL
        // distancia do pontoOrigem do tiro até o eixo x 80
        int catetoOposto = tiro.getPontoOrigem().getVarX() - alvoMovel.getPontoOrigem().getVarX();
        // distancia do final da visão do lancador até o y 0
        int catetoAdjascente = tiro.getPontoOrigem().getVarY();
        // distancia reta entre a saída do alvo e a saída do tiro
        double hipotenusa = Math.sqrt(Math.pow(catetoAdjascente,2) + Math.pow(catetoOposto,2));
        // tangente do grau
        double tan = ((double) catetoOposto/ (double) catetoAdjascente);
        double tangente = Math.tanh(tan);

        // CALCULA O TRIANGULO EQUILÁTERO PARA PEGAR A DISTANCIA A PERCORRER
        double hipotenusa2 = (hipotenusa/2);
        double cttOposto = hipotenusa2 * tangente;

        double hip = Math.sqrt((Math.pow(cttOposto,2)+Math.pow(hipotenusa2,2)));

        // CALCULA COM BASE NAS DIMENSÕES DAS ENTIDADES.
        int iconH = tiro.getImageTiro().getIconHeight()/2;
        hip -= iconH;
        Localizacao loca = new Localizacao(alvoMovel.getPontoOrigem().getVarX(), (int) Math.round(hip));

        tiro.setPontoDestino(loca);

        return loca;
    }*/
    public void run() {
        while (true) {
              //  Tiro tiro = new Tiro();
                System.out.println("Tiro: " + tiro.getId());
                carregar();
                preparar();
                atirar();

        }
    }
}
