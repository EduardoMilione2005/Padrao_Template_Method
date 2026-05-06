package br.com.remoto;

import br.com.remoto.modelo.ControleSomBar;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes - Controle Soundbar")
class ControleSomBarTest {

    private ControleSomBar som;

    @BeforeEach
    void setUp() {
        som = new ControleSomBar();
    }


    @Test
    @DisplayName("Deve ligar o som e executar todas as etapas do template")
    void deveExecutarTodasEtapasAoLigar() {
        som.ligar();

        assertTrue(som.isLigado());
        assertTrue(som.getLog().contains("SOM: Inicializando DAC e amplificador..."));
        assertTrue(som.getLog().contains("SOM: Ativando painel OLED frontal..."));
        assertTrue(som.getLog().contains("SOM: Carregando perfil de equalização e entrada: HDMI"));
        assertTrue(som.getLog().contains("SOM: Pronto! Entrada ativa: HDMI"));
    }

    @Test
    @DisplayName("Deve desligar o som e executar todas as etapas do template")
    void deveExecutarTodasEtapasAoDesligar() {
        som.ligar();
        som.desligar();

        assertFalse(som.isLigado());
        assertTrue(som.getLog().contains("SOM: Salvando perfil de áudio e entrada ativa..."));
        assertTrue(som.getLog().contains("SOM: Desativando processamento de áudio Dolby/DTS..."));
        assertTrue(som.getLog().contains("SOM: Apagando painel frontal..."));
    }


    @Test
    @DisplayName("Deve mudar para entrada HDMI (canal 1)")
    void deveMudarParaEntradaHDMI() {
        som.ligar();
        som.mudarCanal(1);

        assertEquals("HDMI", som.getEntradaAtual());
        assertTrue(som.getLog().contains("SOM: Entrada alterada de 1 para 1 (HDMI)"));
    }

    @Test
    @DisplayName("Deve mudar para entrada OPTICAL (canal 2)")
    void deveMudarParaEntradaOptical() {
        som.ligar();
        som.mudarCanal(2);

        assertEquals("OPTICAL", som.getEntradaAtual());
        assertTrue(som.getLog().contains("SOM: Entrada alterada de 1 para 2 (OPTICAL)"));
    }

    @Test
    @DisplayName("Deve mudar para entrada BLUETOOTH (canal 3)")
    void deveMudarParaEntradaBluetooth() {
        som.ligar();
        som.mudarCanal(3);

        assertEquals("BLUETOOTH", som.getEntradaAtual());
        assertTrue(som.getLog().contains("SOM: Entrada alterada de 1 para 3 (BLUETOOTH)"));
    }

    @Test
    @DisplayName("Deve mudar para entrada AUX (canal 4)")
    void deveMudarParaEntradaAux() {
        som.ligar();
        som.mudarCanal(4);

        assertEquals("AUX", som.getEntradaAtual());
        assertTrue(som.getLog().contains("SOM: Entrada alterada de 1 para 4 (AUX)"));
    }

    @Test
    @DisplayName("Deve rejeitar canal 5 (entrada inexistente)")
    void deveRejeitarCanal5() {
        som.ligar();
        assertThrows(IllegalArgumentException.class, () -> som.mudarCanal(5));
    }

    @Test
    @DisplayName("Deve rejeitar canal 0")
    void deveRejeitarCanalZero() {
        som.ligar();
        assertThrows(IllegalArgumentException.class, () -> som.mudarCanal(0));
    }

    @Test
    @DisplayName("Deve lançar exceção ao mudar entrada com som desligado")
    void deveLancarExcecaoMudarEntradaDesligado() {
        assertThrows(IllegalStateException.class, () -> som.mudarCanal(2));
    }

    @Test
    @DisplayName("Deve ajustar volume com log correto")
    void deveAjustarVolumeComLog() {
        som.ligar();
        som.ajustarVolume(40);

        assertEquals(40, som.getVolume());
        assertTrue(som.getLog().contains("SOM: Volume: 10 dB → 40 dB"));
    }

    @Test
    @DisplayName("Deve emitir aviso para volume acima de 80")
    void deveEmitirAvisoParaVolumeAlto() {
        som.ligar();
        som.ajustarVolume(85);

        assertTrue(som.getLog().contains("SOM: AVISO - Volume muito alto! Risco de dano auditivo."));
    }

    @Test
    @DisplayName("Não deve emitir aviso para volume igual a 80")
    void naoDeveEmitirAvisoParaVolume80() {
        som.ligar();
        som.ajustarVolume(80);

        assertFalse(som.getLog().contains("SOM: AVISO - Volume muito alto! Risco de dano auditivo."));
    }

    @Test
    @DisplayName("Deve lançar exceção para volume acima de 100")
    void deveLancarExcecaoParaVolumeAcima100() {
        som.ligar();
        assertThrows(IllegalArgumentException.class, () -> som.ajustarVolume(101));
    }

    @Test
    @DisplayName("Deve lançar exceção ao ajustar volume com som desligado")
    void deveLancarExcecaoAjustarVolumeDesligado() {
        assertThrows(IllegalStateException.class, () -> som.ajustarVolume(50));
    }

    @Test
    @DisplayName("Deve iniciar sem equalização")
    void deveIniciarSemEqualizacao() {
        assertFalse(som.isEqualizado());
    }

    @Test
    @DisplayName("Deve ativar equalização corretamente")
    void deveAtivarEqualizacao() {
        som.ativarEqualizacao();

        assertTrue(som.isEqualizado());
        assertTrue(som.getLog().contains("SOM: Equalização personalizada ativada."));
    }

    @Test
    @DisplayName("Deve iniciar com entrada HDMI")
    void deveIniciarComEntradaHDMI() {
        assertEquals("HDMI", som.getEntradaAtual());
    }

    @Test
    @DisplayName("Deve retornar nome correto do dispositivo")
    void deveRetornarNomeCorreto() {
        assertEquals("Soundbar Premium", som.getNomeDispositivo());
    }
}
