package br.com.remoto;

import br.com.remoto.modelo.ControleArCondicionado;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes - Controle Ar-Condicionado")
class ControleArCondicionadoTest {

    private ControleArCondicionado ar;

    @BeforeEach
    void setUp() {
        ar = new ControleArCondicionado();
    }


    @Test
    @DisplayName("Deve ligar o ar e executar todas as etapas do template")
    void deveExecutarTodasEtapasAoLigar() {
        ar.ligar();

        assertTrue(ar.isLigado());
        assertTrue(ar.getLog().contains("AR: Inicializando compressor e placa de controle..."));
        assertTrue(ar.getLog().contains("AR: Ativando display de LED..."));
        assertTrue(ar.getLog().contains("AR: Carregando temperatura e modo salvos..."));
        assertTrue(ar.getLog().contains("AR: Sistema pronto. Modo: AUTO"));
    }

    @Test
    @DisplayName("Deve desligar o ar e executar todas as etapas do template")
    void deveExecutarTodasEtapasAoDesligar() {
        ar.ligar();
        ar.desligar();

        assertFalse(ar.isLigado());
        assertTrue(ar.getLog().contains("AR: Salvando temperatura e modo atuais..."));
        assertTrue(ar.getLog().contains("AR: Aguardando ciclo do compressor finalizar..."));
        assertTrue(ar.getLog().contains("AR: Desligando display..."));
    }


    @Test
    @DisplayName("Deve aceitar temperatura mínima válida (16°C)")
    void deveAceitarTemperaturaMinima() {
        ar.ligar();
        assertDoesNotThrow(() -> ar.mudarCanal(16));
        assertEquals(16, ar.getTemperatura());
    }

    @Test
    @DisplayName("Deve aceitar temperatura máxima válida (30°C)")
    void deveAceitarTemperaturaMaxima() {
        ar.ligar();
        assertDoesNotThrow(() -> ar.mudarCanal(30));
        assertEquals(30, ar.getTemperatura());
    }

    @Test
    @DisplayName("Deve registrar log de mudança de temperatura")
    void deveRegistrarLogMudancaTemperatura() {
        ar.ligar();
        ar.mudarCanal(22);

        assertTrue(ar.getLog().contains("AR: Temperatura alterada de 1°C para 22°C"));
    }

    @Test
    @DisplayName("Deve rejeitar temperatura abaixo de 16°C")
    void deveRejeitarTemperaturaAbaixoMinimo() {
        ar.ligar();
        assertThrows(IllegalArgumentException.class, () -> ar.mudarCanal(15));
    }

    @Test
    @DisplayName("Deve rejeitar temperatura acima de 30°C")
    void deveRejeitarTemperaturaAcimaMaximo() {
        ar.ligar();
        assertThrows(IllegalArgumentException.class, () -> ar.mudarCanal(31));
    }

    @Test
    @DisplayName("Deve rejeitar temperatura zero")
    void deveRejeitarTemperaturaZero() {
        ar.ligar();
        assertThrows(IllegalArgumentException.class, () -> ar.mudarCanal(0));
    }

    @Test
    @DisplayName("Deve lançar exceção ao mudar temperatura com ar desligado")
    void deveLancarExcecaoMudarTemperaturaDesligado() {
        assertThrows(IllegalStateException.class, () -> ar.mudarCanal(22));
    }


    @Test
    @DisplayName("Deve ajustar velocidade do ventilador")
    void deveAjustarVelocidadeVentilador() {
        ar.ligar();
        ar.ajustarVolume(60);

        assertEquals(60, ar.getVolume());
        assertTrue(ar.getLog().contains("AR: Velocidade do ventilador: 10 → 60"));
    }

    @Test
    @DisplayName("Deve aceitar volume mínimo zero (silêncio)")
    void deveAceitarVolumeZero() {
        ar.ligar();
        assertDoesNotThrow(() -> ar.ajustarVolume(0));
    }


    @Test
    @DisplayName("Deve iniciar no modo AUTO")
    void deveIniciarNoModoAuto() {
        assertEquals("AUTO", ar.getModo());
    }

    @Test
    @DisplayName("Deve mudar para modo FRIO")
    void deveMudarParaModoFrio() {
        ar.setModo("FRIO");

        assertEquals("FRIO", ar.getModo());
        assertTrue(ar.getLog().contains("AR: Modo alterado para FRIO"));
    }

    @Test
    @DisplayName("Deve mudar para modo QUENTE")
    void deveMudarParaModoQuente() {
        ar.setModo("QUENTE");
        assertEquals("QUENTE", ar.getModo());
    }

    @Test
    @DisplayName("Deve mudar para modo VENTILACAO")
    void deveMudarParaModoVentilacao() {
        ar.setModo("VENTILACAO");
        assertEquals("VENTILACAO", ar.getModo());
    }

    @Test
    @DisplayName("Deve retornar nome correto do dispositivo")
    void deveRetornarNomeCorreto() {
        assertEquals("Ar-Condicionado Split", ar.getNomeDispositivo());
    }

    @Test
    @DisplayName("getTemperatura deve retornar o mesmo que getCanal")
    void deveRetornarTemperaturaIgualAoCanal() {
        ar.ligar();
        ar.mudarCanal(24);
        assertEquals(ar.getCanal(), ar.getTemperatura());
    }
}
