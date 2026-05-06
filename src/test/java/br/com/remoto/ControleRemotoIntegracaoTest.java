package br.com.remoto;

import br.com.remoto.modelo.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes de Integração - Contrato do Template Method")
class ControleRemotoIntegracaoTest {

    static Stream<ControleRemoto> todosOsControles() {
        return Stream.of(
                new ControleTV(),
                new ControleArCondicionado(),
                new ControleSomBar()
        );
    }

    @ParameterizedTest(name = "{0} deve iniciar desligado")
    @MethodSource("todosOsControles")
    @DisplayName("Todos os dispositivos devem iniciar desligados")
    void todosDevemIniciarDesligados(ControleRemoto controle) {
        assertFalse(controle.isLigado(),
                controle.getNomeDispositivo() + " deve iniciar desligado");
    }

    @ParameterizedTest
    @MethodSource("todosOsControles")
    @DisplayName("Todos devem iniciar com volume padrão 10")
    void todosDevemIniciarComVolumePadrao(ControleRemoto controle) {
        assertEquals(10, controle.getVolume());
    }

    @ParameterizedTest
    @MethodSource("todosOsControles")
    @DisplayName("Todos devem iniciar com canal padrão 1")
    void todosDevemIniciarComCanalPadrao(ControleRemoto controle) {
        assertEquals(1, controle.getCanal());
    }

    @ParameterizedTest
    @MethodSource("todosOsControles")
    @DisplayName("Todos devem estar ligados após chamar ligar()")
    void todosDevemLigarCorretamente(ControleRemoto controle) {
        controle.ligar();
        assertTrue(controle.isLigado());
    }

    @ParameterizedTest
    @MethodSource("todosOsControles")
    @DisplayName("Todos devem estar desligados após chamar desligar()")
    void todosDevemDesligarCorretamente(ControleRemoto controle) {
        controle.ligar();
        controle.desligar();
        assertFalse(controle.isLigado());
    }

    @ParameterizedTest
    @MethodSource("todosOsControles")
    @DisplayName("Todos devem aceitar volume 50 quando ligados")
    void todosDevemAceitarVolume50(ControleRemoto controle) {
        controle.ligar();
        assertDoesNotThrow(() -> controle.ajustarVolume(50));
        assertEquals(50, controle.getVolume());
    }

    @ParameterizedTest
    @MethodSource("todosOsControles")
    @DisplayName("Todos devem rejeitar volume negativo")
    void todosDevemRejeitarVolumeNegativo(ControleRemoto controle) {
        controle.ligar();
        assertThrows(IllegalArgumentException.class, () -> controle.ajustarVolume(-1));
    }

    @ParameterizedTest
    @MethodSource("todosOsControles")
    @DisplayName("Todos devem rejeitar volume acima de 100")
    void todosDevemRejeitarVolumeAcima100(ControleRemoto controle) {
        controle.ligar();
        assertThrows(IllegalArgumentException.class, () -> controle.ajustarVolume(101));
    }

    @ParameterizedTest
    @MethodSource("todosOsControles")
    @DisplayName("Todos devem lançar exceção ao ajustar volume desligado")
    void todosDevemLancarExcecaoVolumeDesligado(ControleRemoto controle) {
        assertThrows(IllegalStateException.class, () -> controle.ajustarVolume(30));
    }

    @ParameterizedTest
    @MethodSource("todosOsControles")
    @DisplayName("Todos devem lançar exceção ao mudar canal desligado")
    void todosDevemLancarExcecaoCanalDesligado(ControleRemoto controle) {
        assertThrows(IllegalStateException.class, () -> controle.mudarCanal(2));
    }

    @ParameterizedTest
    @MethodSource("todosOsControles")
    @DisplayName("Todos devem retornar nome do dispositivo não nulo e não vazio")
    void todosDevemRetornarNomeValido(ControleRemoto controle) {
        String nome = controle.getNomeDispositivo();
        assertNotNull(nome);
        assertFalse(nome.isBlank(), "Nome do dispositivo não pode ser vazio");
    }

    @ParameterizedTest
    @MethodSource("todosOsControles")
    @DisplayName("Todos devem suportar ciclo completo ligar > usar > desligar")
    void todosDevemSuportarCicloCompleto(ControleRemoto controle) {
        assertDoesNotThrow(() -> {
            controle.ligar();
            controle.ajustarVolume(30);
            controle.desligar();
        });
        assertFalse(controle.isLigado());
    }

    @Test
    @DisplayName("ControleTV, Ar e Som devem ter comportamentos independentes")
    void dispositivosDevemTerEstadosIndependentes() {
        ControleTV tv = new ControleTV();
        ControleArCondicionado ar = new ControleArCondicionado();
        ControleSomBar som = new ControleSomBar();

        tv.ligar();
        tv.ajustarVolume(80);

        assertFalse(ar.isLigado(), "Ar deve continuar desligado");
        assertFalse(som.isLigado(), "Som deve continuar desligado");
        assertEquals(10, ar.getVolume(), "Volume do Ar não deve mudar");
        assertEquals(10, som.getVolume(), "Volume do Som não deve mudar");
    }
}
