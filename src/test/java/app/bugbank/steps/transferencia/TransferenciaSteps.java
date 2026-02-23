package app.bugbank.steps.transferencia;

import app.bugbank.interactions.transferencia.TransferenciaInteractions;
import io.cucumber.java.pt.Entao;

public class TransferenciaSteps {

    TransferenciaInteractions transferenciaInteractions = new TransferenciaInteractions();

    @Entao("clico no botão Transferência")
    public void clicoNoBotaoTransferencia() {
        transferenciaInteractions.clicaTransferencia();
    }

    @Entao("informo o número da conta e o digito do usuário {string}")
    public void informoONumeroDaContaEDigito(String usuario) {
        transferenciaInteractions.preenchaOsCamposContaEDigito(usuario);
    }

    @Entao("inseri o campo de valor da transferência")
    public void inseriOCampoDeValorDaTransferencia() {
        transferenciaInteractions.inserirOValorDeTransferencia();
    }

    @Entao("preencho o campo de descrição")
    public void preenchoOCampoDeDescricao() {
        transferenciaInteractions.preenchaCampoDeDescricao();
    }

    @Entao("clico no botão Transferir agora")
    public void clicoNoBotaoTranserirAgora() {
        transferenciaInteractions.realizaTransferir();
    }

    @Entao("devo visualizar o modal com a mensagem Transferência realizada com sucesso")
    public void devoVisualizarOModalTransferenciaComSucesso() {
        transferenciaInteractions.validaTransferenciaComSucesso();
    }

    @Entao("clico no botão fechar do modal")
    public void clicaFecharDoModal() {
        transferenciaInteractions.fechaOModalSucesso();
    }

    @Entao("clico no botão para voltar ao meu perfil")
    public void clicaVoltarAoMeuPerfil() {
        transferenciaInteractions.clicaVoltarNaTelaDoMeuPerfil();
    }

    @Entao("o saldo do usuário deve estar atualizado com o valor descontado")
    public void saldoDeveAtualizadoComValorDescontado() {
        transferenciaInteractions.validaOSaldoDesconta();
    }

    @Entao("clico no botão Extrato")
    public void acessaNoExtrato() {
        transferenciaInteractions.clicaExtratoParaAcessar();
    }

    @Entao("devo visualizar o saldo e o registro da transferência enviada")
    public void devoVisualizarOSaldoERegistroDaTransferencia() {
        transferenciaInteractions.validarOSaldoERegistroDaTransferenciaEnviada();
    }

    @Entao("clico no botão sair para realizar o logout")
    public void realizarOLogout() {
        transferenciaInteractions.realizarLogout();
    }
}