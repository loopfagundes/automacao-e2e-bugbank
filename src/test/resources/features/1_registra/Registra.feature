#language: pt

@Registra
@Regressivo
Funcionalidade: Login

  Contexto:
    Dado que acesso o site do BugBank

  @Registra_001
  Cenário: Realizar o registro do primeiro usuario
    Dado que clico o botao registrar
    Quando preencho o campo do email "AS"
    E preencho o campo do nome "AS"
    E preencho o campo da senha "AS"
    E preencho novamente a senha no campo de confirmação de senha "AS"
    Quando clico o toggle para criar a conta com saldo
    Entao clico no botao cadastrar
    E fecho o modal

  @Registra_002
  Cenário: Realizar o registro do segundo usuario
    Dado que clico o botao registrar
    Quando preencho o campo do email "OW"
    E preencho o campo do nome "OW"
    E preencho o campo da senha "OW"
    E preencho novamente a senha no campo de confirmação de senha "OW"
    Entao clico no botao cadastrar
    E fecho o modal
