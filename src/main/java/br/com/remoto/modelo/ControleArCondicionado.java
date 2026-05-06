package br.com.remoto.modelo;

import java.util.ArrayList;
import java.util.List;

public class ControleArCondicionado extends ControleRemoto {

    private static final int TEMP_MIN = 16;
    private static final int TEMP_MAX = 30;

    private List<String> log;
    private String modo; // FRIO, QUENTE, VENTILACAO, AUTO

    public ControleArCondicionado() {
        super();
        this.log = new ArrayList<>();
        this.modo = "AUTO";
    }

    @Override
    protected void inicializarHardware() {
        log.add("AR: Inicializando compressor e placa de controle...");
    }

    @Override
    protected void ativarTela() {
        log.add("AR: Ativando display de LED...");
    }

    @Override
    protected void carregarConfiguracoes() {
        log.add("AR: Carregando temperatura e modo salvos...");
    }

    @Override
    protected void salvarConfiguracoes() {
        log.add("AR: Salvando temperatura e modo atuais...");
    }

    @Override
    protected void encerrarProcessos() {
        log.add("AR: Aguardando ciclo do compressor finalizar...");
    }

    @Override
    protected void desativarTela() {
        log.add("AR: Desligando display...");
    }


    @Override
    protected void exibirMensagemBemVindo() {
        log.add("AR: Sistema pronto. Modo: " + modo);
    }

    @Override
    protected void aoMudarCanal(int anterior, int novo) {
        log.add("AR: Temperatura alterada de " + anterior + "°C para " + novo + "°C");
    }

    @Override
    protected void aoAjustarVolume(int anterior, int novo) {
        log.add("AR: Velocidade do ventilador: " + anterior + " → " + novo);
    }

    @Override
    protected boolean canalValido(int temperatura) {
        return temperatura >= TEMP_MIN && temperatura <= TEMP_MAX;
    }

    public void setModo(String modo) {
        this.modo = modo;
        log.add("AR: Modo alterado para " + modo);
    }

    public String getModo()         { return modo; }
    public List<String> getLog()    { return log; }
    public int getTemperatura()     { return getCanal(); }

    @Override
    public String getNomeDispositivo() { return "Ar-Condicionado Split"; }
}
