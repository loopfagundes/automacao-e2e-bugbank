#language: pt

@Login
@Regressivo
Funcionalidade: Login

  Contexto:
    Dado que acesso o site do BugBank

  @Login_001
  Cenário: Efetura login
    Quando preencho o campo do email

  @Login_002
  Cenario: Senha
    Quando preencho o campo da senha