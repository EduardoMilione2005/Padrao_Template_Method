package br.com.remoto.modelo;

public abstract class ControleRemoto {

    private boolean ligado;
    private int volume;
    private int canal;

    protected static final int VOLUME_MIN = 0;
    protected static final int VOLUME_MAX = 100;
    protected static final int CANAL_MIN = 1;
    protected static final int CANAL_MAX = 999;

    public ControleRemoto() {
        this.ligado = false;
        this.volume = 10;
        this.canal = 1;
    }

    public final void ligar() {
        inicializarHardware();
        ativarTela();
        carregarConfiguracoes();
        exibirMensagemBemVindo();
        this.ligado = true;
    }

    public final void desligar() {
        salvarConfiguracoes();
        encerrarProcessos();
        desativarTela();
        this.ligado = false;
    }

    public final void mudarCanal(int novoCanal) {
        if (!ligado) {
            throw new IllegalStateException("Dispositivo está desligado.");
        }
        if (!canalValido(novoCanal)) {
            throw new IllegalArgumentException("Canal inválido: " + novoCanal);
        }
        int canalAnterior = this.canal;
        this.canal = novoCanal;
        aoMudarCanal(canalAnterior, novoCanal);
    }

    public final void ajustarVolume(int novoVolume) {
        if (!ligado) {
            throw new IllegalStateException("Dispositivo está desligado.");
        }
        if (novoVolume < VOLUME_MIN || novoVolume > VOLUME_MAX) {
            throw new IllegalArgumentException(
                "Volume inválido: " + novoVolume + ". Deve ser entre " + VOLUME_MIN + " e " + VOLUME_MAX);
        }
        int volumeAnterior = this.volume;
        this.volume = novoVolume;
        aoAjustarVolume(volumeAnterior, novoVolume);
    }


    protected abstract void inicializarHardware();
    protected abstract void ativarTela();
    protected abstract void carregarConfiguracoes();
    protected abstract void salvarConfiguracoes();
    protected abstract void encerrarProcessos();
    protected abstract void desativarTela();

    protected void exibirMensagemBemVindo() {
    }

    protected void aoMudarCanal(int canalAnterior, int novoCanal) {
    }

    protected void aoAjustarVolume(int volumeAnterior, int novoVolume) {
    }

    protected boolean canalValido(int canal) {
        return canal >= CANAL_MIN && canal <= CANAL_MAX;
    }

    public boolean isLigado() { return ligado; }
    public int getVolume()    { return volume; }
    public int getCanal()     { return canal; }

    public abstract String getNomeDispositivo();
}
