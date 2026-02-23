#language: pt

@Regressivo
Funcionalidade: Realizada a transferencia de saldo para outro usuário

  Contexto:
    Dado que acesso o site do BugBank
    E valido que estou a tela de login
    Quando que preencho o campo de e-mail com o usuário "AS"
    E preencho o campo de senha com o usuário
    E clico no botão acessar
    Entao devo visualizar a tela de bem vindo

  Cenário: Fazer a transferência de saldo com sucesso
    E o nome do usuário "AS" deve ser exibido na tela
    E o saldo do usuário "AS" deve ser exibido na tela
    Quando clico no botão Transferência
    E informo o número da conta e o digito do usuário "OW"
    E inseri o campo de valor da transferência
    E preencho o campo de descrição
    E clico no botão Transferir agora
    Então devo visualizar o modal com a mensagem Transferência realizada com sucesso
    Quando clico no botão fechar do modal
    E clico no botão para voltar ao meu perfil
    Então o saldo do usuário deve estar atualizado com o valor descontado
    Quando clico no botão Extrato
    Então devo visualizar o saldo e o registro da transferência enviada
    Quando clico no botão para voltar ao meu perfil
    Então clico no botão sair para realizar o logout