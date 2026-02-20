#language: pt

@Transferencia
@Regressivo
Funcionalidade: Realizada a transferencia de saldo para outro usuário

  Cenário: Fazer a transferência de saldo com sucesso
    Dado que estou na tela logado
    E o nome do usuário "AS" deve ser exibido na tela
    E o saldo do usuário deve ser exibido na tela
    Quando eu clico no botão "Transferência"
    E informo o número da conta do usuário "OW"
    E preencho o campo de valor da transferência
    E preencho o campo de descrição
    E clico no botão Transferir agora
    Então devo visualizar o modal com a mensagem "Transferência realizada com sucesso"
    Quando clico no botão "Fechar" do modal
    E clico no botão para voltar ao meu perfil
    Então o saldo do usuário deve estar atualizado com o valor descontado
    Quando clico no botão Extrato
    Então devo visualizar o saldo e o registro da transferência enviada
    Quando clico no botão para voltar ao meu perfil
    Então clico no botão sair para realizar o logout