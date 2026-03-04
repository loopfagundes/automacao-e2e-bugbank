package app.bugbank.steps.recebimento;

import app.bugbank.interactions.recebimento.RecebimentoInteractions;
import io.cucumber.java.pt.Entao;

public class RecebimentoSteps {

    RecebimentoInteractions recebimentoInteractions = new RecebimentoInteractions();

    @Entao("devo visualizar o saldo atualizado")
    public void devoVisualizarOSaldoAtualizado() {
        recebimentoInteractions.validarOSaldoDisponivelAtualizado();
    }

    @Entao("o extrato deve exibir o registro da transferência recebida")
    public void extratoDeveExibirORegistroDaTransferenciaRecebida() {
        recebimentoInteractions.validarTransferenciaRecebida();
    }
}