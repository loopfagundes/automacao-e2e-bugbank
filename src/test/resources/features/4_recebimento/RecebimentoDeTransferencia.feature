#language: pt

@Regressivo
Funcionalidade: Recebimento de transferência de saldo

  Contexto:
    Dado que acesso o site do BugBank
    E valido que estou a tela de login
    Quando que preencho o campo de e-mail com o usuário "OW"
    E preencho o campo de senha com o usuário
    E clico no botão acessar
    Entao devo visualizar a tela de bem vindo

  Cenário: Fazer a transferência de saldo com sucesso
    E o nome do usuário "OW" deve ser exibido na tela
    E o saldo do usuário "OW" deve ser exibido na tela
    Quando clico no botão Extrato
    Então devo visualizar o saldo atualizado
    E o extrato deve exibir o registro da transferência recebida
    Quando clico no botão para voltar ao meu perfil
    Então clico no botão sair para realizar o logout