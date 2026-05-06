package br.com.remoto.modelo;

import java.util.ArrayList;
import java.util.List;

public class ControleSomBar extends ControleRemoto {

    private List<String> log;
    private String entradaAtual;
    private boolean equalizado;

    public ControleSomBar() {
        super();
        this.log = new ArrayList<>();
        this.entradaAtual = "HDMI";
        this.equalizado = false;
    }

    @Override
    protected void inicializarHardware() {
        log.add("SOM: Inicializando DAC e amplificador...");
    }

    @Override
    protected void ativarTela() {
        log.add("SOM: Ativando painel OLED frontal...");
    }

    @Override
    protected void carregarConfiguracoes() {
        log.add("SOM: Carregando perfil de equalização e entrada: " + entradaAtual);
    }

    @Override
    protected void salvarConfiguracoes() {
        log.add("SOM: Salvando perfil de áudio e entrada ativa...");
    }

    @Override
    protected void encerrarProcessos() {
        log.add("SOM: Desativando processamento de áudio Dolby/DTS...");
    }

    @Override
    protected void desativarTela() {
        log.add("SOM: Apagando painel frontal...");
    }

    @Override
    protected void exibirMensagemBemVindo() {
        log.add("SOM: Pronto! Entrada ativa: " + entradaAtual);
    }

    @Override
    protected void aoMudarCanal(int anterior, int novo) {
        String[] entradas = {"", "HDMI", "OPTICAL", "BLUETOOTH", "AUX"};
        String nomeEntrada = (novo >= 1 && novo <= 4) ? entradas[novo] : "DESCONHECIDA";
        entradaAtual = nomeEntrada;
        log.add("SOM: Entrada alterada de " + anterior + " para " + novo + " (" + nomeEntrada + ")");
    }

    @Override
    protected void aoAjustarVolume(int anterior, int novo) {
        log.add("SOM: Volume: " + anterior + " dB → " + novo + " dB");
        if (novo > 80) log.add("SOM: AVISO - Volume muito alto! Risco de dano auditivo.");
    }

    @Override
    protected boolean canalValido(int canal) {
        return canal >= 1 && canal <= 4; // Apenas 4 entradas disponíveis
    }

    public void ativarEqualizacao() {
        this.equalizado = true;
        log.add("SOM: Equalização personalizada ativada.");
    }

    public boolean isEqualizado()   { return equalizado; }
    public String getEntradaAtual() { return entradaAtual; }
    public List<String> getLog()    { return log; }

    @Override
    public String getNomeDispositivo() { return "Soundbar Premium"; }
}
