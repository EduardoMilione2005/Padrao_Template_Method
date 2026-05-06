package br.com.remoto;

import br.com.remoto.modelo.ControleTV;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes - Controle TV")
class ControleTVTest {

    private ControleTV tv;

    @BeforeEach
    void setUp() {
        tv = new ControleTV();
    }



    @Test
    @DisplayName("Deve ligar a TV e registrar todas as etapas do template")
    void deveExecutarTodasEtapasAoLigar() {
        tv.ligar();

        assertTrue(tv.isLigado(), "TV deve estar ligada");
        assertTrue(tv.getLog().contains("TV: Inicializando hardware do painel LCD/OLED..."),
                "Deve inicializar hardware");
        assertTrue(tv.getLog().contains("TV: Ativando backlight e painel de vídeo..."),
                "Deve ativar tela");
        assertTrue(tv.getLog().contains("TV: Carregando perfil de imagem e lista de canais..."),
                "Deve carregar configurações");
        assertTrue(tv.getLog().contains("TV: Bem-vindo! Canal favorito: 1"),
                "Deve exibir mensagem de boas-vindas");
    }

    @Test
    @DisplayName("Deve desligar a TV e registrar todas as etapas do template")
    void deveExecutarTodasEtapasAoDesligar() {
        tv.ligar();
        tv.desligar();

        assertFalse(tv.isLigado(), "TV deve estar desligada");
        assertTrue(tv.getLog().contains("TV: Salvando canal atual e configurações de imagem..."),
                "Deve salvar configurações");
        assertTrue(tv.getLog().contains("TV: Encerrando sinal de entrada e decodificador..."),
                "Deve encerrar processos");
        assertTrue(tv.getLog().contains("TV: Desligando backlight..."),
                "Deve desativar tela");
    }

    @Test
    @DisplayName("TV deve iniciar desligada")
    void deveIniciarDesligada() {
        assertFalse(tv.isLigado());
    }


    @Test
    @DisplayName("Deve mudar de canal com sucesso quando ligada")
    void deveMudarCanalComSucesso() {
        tv.ligar();
        tv.mudarCanal(50);

        assertEquals(50, tv.getCanal());
        assertTrue(tv.getLog().contains("TV: Mudando do canal 1 para o canal 50"));
    }

    @Test
    @DisplayName("Deve aceitar canal mínimo (1)")
    void deveAceitarCanalMinimo() {
        tv.ligar();
        assertDoesNotThrow(() -> tv.mudarCanal(1));
        assertEquals(1, tv.getCanal());
    }

    @Test
    @DisplayName("Deve aceitar canal máximo (999)")
    void deveAceitarCanalMaximo() {
        tv.ligar();
        assertDoesNotThrow(() -> tv.mudarCanal(999));
        assertEquals(999, tv.getCanal());
    }

    @Test
    @DisplayName("Deve lançar exceção ao mudar canal com TV desligada")
    void deveLancarExcecaoMudarCanalDesligada() {
        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> tv.mudarCanal(5));
        assertEquals("Dispositivo está desligado.", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção para canal inválido (0)")
    void deveLancarExcecaoParaCanalZero() {
        tv.ligar();
        assertThrows(IllegalArgumentException.class, () -> tv.mudarCanal(0));
    }

    @Test
    @DisplayName("Deve lançar exceção para canal inválido (1000)")
    void deveLancarExcecaoParaCanal1000() {
        tv.ligar();
        assertThrows(IllegalArgumentException.class, () -> tv.mudarCanal(1000));
    }

    @Test
    @DisplayName("Deve lançar exceção para canal negativo")
    void deveLancarExcecaoParaCanalNegativo() {
        tv.ligar();
        assertThrows(IllegalArgumentException.class, () -> tv.mudarCanal(-10));
    }

    @Test
    @DisplayName("Deve ajustar volume com sucesso")
    void deveAjustarVolumeComSucesso() {
        tv.ligar();
        tv.ajustarVolume(50);

        assertEquals(50, tv.getVolume());
        assertTrue(tv.getLog().contains("TV: Volume ajustado de 10 para 50"));
    }

    @Test
    @DisplayName("Deve registrar log de mudo ao volume zero")
    void deveRegistrarMudoAoVolumeZero() {
        tv.ligar();
        tv.ajustarVolume(0);

        assertEquals(0, tv.getVolume());
        assertTrue(tv.getLog().contains("TV: Mudo ativado."));
    }

    @Test
    @DisplayName("Deve aceitar volume máximo (100)")
    void deveAceitarVolumeMaximo() {
        tv.ligar();
        assertDoesNotThrow(() -> tv.ajustarVolume(100));
        assertEquals(100, tv.getVolume());
    }

    @Test
    @DisplayName("Deve lançar exceção para volume acima do máximo")
    void deveLancarExcecaoParaVolumeAcimaMaximo() {
        tv.ligar();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> tv.ajustarVolume(101));
        assertTrue(ex.getMessage().contains("Volume inválido"));
    }

    @Test
    @DisplayName("Deve lançar exceção para volume negativo")
    void deveLancarExcecaoParaVolumeNegativo() {
        tv.ligar();
        assertThrows(IllegalArgumentException.class, () -> tv.ajustarVolume(-1));
    }

    @Test
    @DisplayName("Deve lançar exceção ao ajustar volume com TV desligada")
    void deveLancarExcecaoAjustarVolumeDesligada() {
        assertThrows(IllegalStateException.class, () -> tv.ajustarVolume(30));
    }


    @Test
    @DisplayName("Deve ativar modo HDR corretamente")
    void deveAtivarModoHDR() {
        assertFalse(tv.isModoHDR());
        tv.ativarModoHDR();
        assertTrue(tv.isModoHDR());
        assertTrue(tv.getLog().contains("TV: Modo HDR ativado."));
    }

    @Test
    @DisplayName("Deve retornar nome correto do dispositivo")
    void deveRetornarNomeCorreto() {
        assertEquals("Televisão Smart", tv.getNomeDispositivo());
    }

    @Test
    @DisplayName("Deve manter volume inicial padrão de 10")
    void deveManterVolumeInicialPadrao() {
        assertEquals(10, tv.getVolume());
    }

    @Test
    @DisplayName("Deve manter canal inicial padrão de 1")
    void deveManterCanalInicialPadrao() {
        assertEquals(1, tv.getCanal());
    }
}
