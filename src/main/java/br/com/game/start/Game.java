package br.com.game.start;

import br.com.game.entidades.*;
import br.com.game.sistema.Localizacao;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;


public class Game extends JFrame {

    // CRIA UM LANÇADOR INICIAL.
    private Lancador lancador = new Lancador();

    private ArrayList<AlvoMovel> alvos_caminho1 = new ArrayList<AlvoMovel>();
    private ArrayList<AlvoMovel> alvos_caminho2 = new ArrayList<AlvoMovel>();
    // AQUI É ONDE SERÃO PRODUZIDAS AS IMAGENS
    public static BufferedImage backBuffer;
    // TEMPO DE LOCOMOÇÃO DOS OBJETOS
    static double locomocao = 3.0;
    // TAMANHO DA JANELA
    int janelaW = 500;
    int janelaH = 500;
    //
    static double pontoY = 440;
    static double pontoX = 215;
    static double contaX = 0;
    static double contaY = 0;
    static double contaW = 0;
    static double pontoW = 440;
    int id;

    // INFORMA SE O ALVO CHEGOU AO DESTINO E O SISTEMA PERDEU
    public void exibeTexto() {
        Graphics bbg = backBuffer.getGraphics();
        if (alvos_caminho1 != null) {
            if (alvos_caminho1.get(0).getChegadaNoDestino()) {
                bbg.setColor(Color.RED);
                bbg.drawString("Você perdeu!", 200, 100);
            }
        }
    }

    //Verifica se o alvo chegou no destino (ponto inferior do mapa Y = 420)
    public boolean chegou(AlvoMovel alvoMovel) {
        if (alvoMovel != null) {
            Localizacao loc = alvoMovel.getLocalizacaoAtual();
            if (loc.getVarY() >= 420) {
                alvoMovel.setChegadaNoDestino(true);
                return true;

            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    //CHAMA O MÉTODO CHEGOU PARA VER SE CHEGOU.
    public void atualizar() {
        if (alvos_caminho1 != null || alvos_caminho2 != null   )
            for (int i = 0; i < alvos_caminho1.size(); i++) {
                alvos_caminho1.get(i).setChegadaNoDestino(chegou(alvos_caminho1.get(i)));
            }
        for (int i = 0; i < alvos_caminho2.size(); i++) {
            alvos_caminho2.get(i).setChegadaNoDestino(chegou(alvos_caminho2.get(i)));
        }


    }

    //DESENHA TODOS OS GRÁFICOS
    public void desenharGraficos() {
        Graphics g = getGraphics(); // ISSO JÃ� ESTAVA AQUI
        Graphics bbg = backBuffer.getGraphics();// ISSO TAMBÃ‰M JÃ� ESTAVA AQUI...
        // ==================================================================================
        bbg.setColor(Color.WHITE);
        bbg.fillRect(0, 0, janelaW, janelaH);// DESENHA UM FUNDO BRANCO NA TELA!

        // EXIBE UM TEXTO CASO O OBJETO COLIDA!
        exibeTexto();

        //DESENHANDO AS ENTIDADES
        desenharLancador(bbg);
       desenharAlvoMovel(bbg);
        desenharTiro(bbg);
        // -----------------------

        colidiram(alvos_caminho1, lancador.getTiro());
        // ==================================================================================
        g.drawImage(backBuffer, 0, 0, this);// OBS: ISSO DEVE FICAR SEMPRE NO
        // FINAL!

    }


    public void desenharTiro(Graphics bbg){
       if(lancador.getTiro().isContatoAlvo() == false) {
            ImageIcon image = lancador.getTiro().getImageTiro();
            bbg.drawImage(image.getImage(), lancador.getTiro().getLocalizacaoAtual().getVarX(),
                    lancador.getTiro().getLocalizacaoAtual().getVarY(), this);
        }
    }


    public void desenharLancador(Graphics bbg) {
            ImageIcon image = lancador.getImageLancador();
            bbg.drawImage(image.getImage(), 215, 440, this);
    }

    public void desenharAlvoMovel(Graphics bbg) {
        for (int i = 0; i < alvos_caminho1.size(); i++) {
            if (alvos_caminho1.get(i) != null && !alvos_caminho1.get(i).getAtingido() && !alvos_caminho1.get(i).getChegadaNoDestino()) {
                ImageIcon image = alvos_caminho1.get(i).getImageAlvo();
                bbg.drawImage(image.getImage(), alvos_caminho1.get(i).getLocalizacaoAtual().getVarX(),
                        alvos_caminho1.get(i).getLocalizacaoAtual().getVarY(), this);
            }
            else if(alvos_caminho1.get(i).getAtingido() || alvos_caminho1.get(i).getChegadaNoDestino()){
                alvos_caminho1.remove(i);
            }
        }
        for(int i = 0; i < alvos_caminho2.size(); i++) {
            if (alvos_caminho2.get(i) != null && !alvos_caminho2.get(i).getAtingido() && !alvos_caminho2.get(i).getChegadaNoDestino()) {
                ImageIcon image = alvos_caminho2.get(i).getImageAlvo();
                bbg.drawImage(image.getImage(), alvos_caminho2.get(i).getLocalizacaoAtual().getVarX(),
                        alvos_caminho2.get(i).getLocalizacaoAtual().getVarY(), this);
            }
            else if(alvos_caminho2.get(i).getAtingido() || alvos_caminho2.get(i).getChegadaNoDestino()){
                alvos_caminho2.remove(i);
            }
        }
    }
    public void criaAlvo(ArrayList<AlvoMovel> Alvosesq,ArrayList<AlvoMovel> Alvosdir){
        Alvosesq.add(new AlvoMovel(1,new Date().getTime()));
        Alvosdir.add(new AlvoMovel(2,new Date().getTime()));
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Thread interrompida!");
        }

    }
    public void colidiram(ArrayList<AlvoMovel> alvo, Tiro tiro) { //ARRUMAR COLISAO
        Graphics bbg = backBuffer.getGraphics();
        for (int i = 0; i < alvo.size(); i++){
        if (colisao(alvo.get(i), tiro)) {
            alvo.remove(i);
            bbg.setColor(Color.RED);
            bbg.drawString("Tiro Acertado.", 200, 100);
            alvo.get(i).setAtingido(true);
            lancador.getTiro().setContatoAlvo(true);
            }
        }

    }
    // VERIFICA SE HOUVE A COLISÃO
    public static boolean colisao(AlvoMovel alvoMovel, Tiro tiro){
        boolean colidiu = false;

        colidiu = tiro.getRetangulo().intersects(alvoMovel.getRentangulo());

        return colidiu;
    }

    // ELE CALCULA CADA POSIÇÃO QUE O ALVO TERÁ QUE SE MOVER, DADO A VELOCIDAE DE LOMOÇÃO
    // EO PONTO DE ORIGEM DO TIRO E O PONTO FINAL (ONDE ELE IRÁ COLIDIR COM O ALVO)
    public static Localizacao geraTrajetoria(Localizacao pontoOrigem, Localizacao pontoFinal) {

            contaY = pontoFinal.getVarY() - pontoOrigem.getVarY();
            contaX = pontoFinal.getVarX() - pontoOrigem.getVarX();

        if (contaY == 0 && pontoX < pontoFinal.getVarY()) {
            contaX = locomocao;
            pontoX += contaX;
           pontoY -= contaY;
          //  pontoW -= contaW;
        } else if (contaY < 0) {
            contaX = (contaX / contaY) * locomocao;
           contaY = locomocao;
           // contaW = locomocao;
            pontoX -= contaX;
          pontoY -= locomocao;
        } else {
          //  contaY = (contaW / contaX) * locomocao;
            contaY = (contaY / contaX) * locomocao;
            contaX = locomocao;
            pontoY += contaY;
            //pontoW += contaW;
            pontoX += contaX;
        }
        System.out.println("PontoX = " + pontoX +
                "\nPontoY = " + /*pontoY*/ + pontoY);
        return new Localizacao((int) Math.round(pontoX), (int) Math.round(pontoY));
    }
//----------------------------------------------------------------------------------------
public void inicializar() {


    alvos_caminho1.add(new AlvoMovel(1, new Date().getTime()));
    alvos_caminho1.add(new AlvoMovel(2, new Date().getTime()));

    setTitle("Teste Caminho");
    setSize(janelaW, janelaH);
    setResizable(false);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLayout(null);
    setVisible(true);
    backBuffer = new BufferedImage(janelaW, janelaH,
            BufferedImage.TYPE_INT_RGB);
}
//-----------------------------------------------------------------------------
    public void run() {

        inicializar();

        while (true) {
            criaAlvo(alvos_caminho1,alvos_caminho2);
         //  lancador.calcularTrajetoria(alvos_caminho1.get(2),lancador.getTiro());
            atualizar();
            desenharGraficos();
            try {
                Thread.sleep(30);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Thread interrompida!");
            }
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.run();
    }
}
