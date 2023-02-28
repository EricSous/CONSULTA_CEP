Feature: ConsultaController feature

  Scenario: Consulta de endereço com sucesso
    Given um CEP válido
    When enviar uma requisição para a consulta de endereço com o CEP "72155613"
    Then o serviço retorna o endereço e o valor do frete

  Scenario: Consulta de endereço com CEP inválido
    Given um CEP inválido
    When enviar uma requisição para a consulta de endereço com o CEP "312"
    Then o serviço retorna um erro informando que o CEP não foi encontrado

