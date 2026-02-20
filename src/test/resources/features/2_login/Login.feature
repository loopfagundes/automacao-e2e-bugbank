#language: pt

@Login
@Regressivo
Funcionalidade: Efetura Login

  Contexto:
    Dado que acesso o site do BugBank

  @Login_001
  Cenário: Fazer login para acessar a conta do primeiro usuário bancário
    Dado que preencho o campo de e-mail com o usuário "AS"
    E preencho o campo de senha com o usuário
    Quando clico no botão acessar
    Entao devo visualizar a tela de bem vindo
    E o nome do usuário "AS" deve ser exibido na tela
    E a conta e o dígito do usuario devem ser exibidos na tela
    E o saldo do usuário deve ser exibido na tela
    Quando clico no botão sair
    Entao valido que estou a tela de login

  @Login_002
  Cenário: Fazer login para acessar a conta do segundo usuário bancário
    Dado que preencho o campo de e-mail com o usuário "OW"
    E preencho o campo de senha com o usuário
    Quando clico no botão acessar
    Entao devo visualizar a tela de bem vindo
    E o nome do usuário "OW" deve ser exibido na tela
    E a conta e o dígito do usuario devem ser exibidos na tela
    E o saldo do usuário deve ser exibido na tela
    Quando clico no botão sair
    Entao valido que estou a tela de login