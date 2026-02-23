#language: pt

@Regressivo
Funcionalidade: Fazer cadastrar uma nova conta de usuário

  Contexto:
    Dado que acesso o site do BugBank

  Cenário: Realizar o registro do primeiro usuario
    Dado que clico o botao registrar
    Quando preencho o campo do email "AS"
    E preencho o campo do nome "AS"
    E preencho o campo da senha e confirmação de senha
    E clico o toggle para criar a conta com saldo
    Quando clico no botao cadastrar
    Entao armazeno a conta e o dígito do usuário "AS"
    E fecho o modal

  Cenário: Realizar o registro do segundo usuario
    Dado que clico o botao registrar
    Quando preencho o campo do email "OW"
    E preencho o campo do nome "OW"
    E preencho o campo da senha e confirmação de senha
    Quando clico no botao cadastrar
    Entao armazeno a conta e o dígito do usuário "OW"
    E fecho o modal