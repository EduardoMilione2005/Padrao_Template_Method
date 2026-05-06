# 🎮 Controle Remoto — Padrão Template Method

## Sobre o Padrão Template Method

O **Template Method** é um padrão de projeto comportamental que define o esqueleto de um algoritmo em uma classe abstrata, delegando alguns passos para as subclasses. Ele permite que subclasses redefinam certas etapas sem alterar a estrutura geral do algoritmo.

### Intenção
> "Define o esqueleto de um algoritmo em uma operação, postergando a definição de alguns passos para subclasses."
> — Gang of Four (GoF)

---

## Estrutura do Projeto

```
controle-remoto/
├── pom.xml
├── diagrama-classes.mermaid
├── README.md
└── src/
    ├── main/java/br/com/remoto/
    │   └── modelo/
    │       ├── ControleRemoto.java          ← Classe abstrata (Template Method)
    │       ├── ControleTV.java              ← Implementação concreta
    │       ├── ControleArCondicionado.java  ← Implementação concreta
    │       └── ControleSomBar.java          ← Implementação concreta
    └── test/java/br/com/remoto/
        ├── ControleTVTest.java              ← 16 casos de teste
        ├── ControleArCondicionadoTest.java  ← 16 casos de teste
        ├── ControleSomBarTest.java          ← 16 casos de teste
        └── ControleRemotoIntegracaoTest.java← 12 testes parametrizados
```

---

## Como o Template Method foi aplicado

### Template Methods definidos na classe abstrata:

| Método | Descrição |
|--------|-----------|
| `ligar()` | Executa: `inicializarHardware → ativarTela → carregarConfiguracoes → exibirMensagemBemVindo` |
| `desligar()` | Executa: `salvarConfiguracoes → encerrarProcessos → desativarTela` |
| `mudarCanal(int)` | Valida estado, valida canal, atualiza e chama hook `aoMudarCanal` |
| `ajustarVolume(int)` | Valida estado, valida volume, atualiza e chama hook `aoAjustarVolume` |

### Passos abstratos (obrigatórios nas subclasses):
- `inicializarHardware()`, `ativarTela()`, `carregarConfiguracoes()`
- `salvarConfiguracoes()`, `encerrarProcessos()`, `desativarTela()`
- `getNomeDispositivo()`

### Hooks (opcionais — têm implementação padrão vazia):
- `exibirMensagemBemVindo()` — personalizável por cada dispositivo
- `aoMudarCanal(int, int)` — chamado após toda mudança de canal
- `aoAjustarVolume(int, int)` — chamado após todo ajuste de volume
- `canalValido(int)` — permite que o Ar-Condicionado valide temperatura e o Som valide entradas

---

## Executando os testes

```bash
mvn test

mvn test -Dtest=ControleTVTest

mvn test -Dtest=ControleRemotoIntegracaoTest
```

---

## Tecnologias
- **Java 17**
- **JUnit 5 (Jupiter)**
- **Maven 3.x**
