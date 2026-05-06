package br.com.remoto.modelo;

import java.util.ArrayList;
import java.util.List;

public class ControleTV extends ControleRemoto {

    private List<String> log;
    private boolean modoHDR;

    public ControleTV() {
        super();
        this.log = new ArrayList<>();
        this.modoHDR = false;
    }

    @Override
    protected void inicializarHardware() {
        log.add("TV: Inicializando hardware do painel LCD/OLED...");
    }

    @Override
    protected void ativarTela() {
        log.add("TV: Ativando backlight e painel de vídeo...");
    }

    @Override
    protected void carregarConfiguracoes() {
        log.add("TV: Carregando perfil de imagem e lista de canais...");
    }

    @Override
    protected void salvarConfiguracoes() {
        log.add("TV: Salvando canal atual e configurações de imagem...");
    }

    @Override
    protected void encerrarProcessos() {
        log.add("TV: Encerrando sinal de entrada e decodificador...");
    }

    @Override
    protected void desativarTela() {
        log.add("TV: Desligando backlight...");
    }


    @Override
    protected void exibirMensagemBemVindo() {
        log.add("TV: Bem-vindo! Canal favorito: " + getCanal());
    }

    @Override
    protected void aoMudarCanal(int anterior, int novo) {
        log.add("TV: Mudando do canal " + anterior + " para o canal " + novo);
    }

    @Override
    protected void aoAjustarVolume(int anterior, int novo) {
        log.add("TV: Volume ajustado de " + anterior + " para " + novo);
        if (novo == 0) log.add("TV: Mudo ativado.");
    }

    public void ativarModoHDR() {
        this.modoHDR = true;
        log.add("TV: Modo HDR ativado.");
    }

    public boolean isModoHDR()      { return modoHDR; }
    public List<String> getLog()    { return log; }

    @Override
    public String getNomeDispositivo() { return "Televisão Smart"; }
}
