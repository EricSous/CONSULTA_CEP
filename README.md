<h1>Consulta Endereço via CEP</h1>
Essa API REST foi construída como solução para o desafio de construir uma API para consulta de endereço e cálculo de frete para um determinado CEP. O contrato da API é conforme especificado abaixo:

Endpoint: POST v1/consulta-endereco

<strong>Request:

```JSON
{
  "cep": "01001000"
}
  ```
<strong>Response HTTP 200:

```JSON
{
  "cep": "01001-000",
  "rua": "Praça da Sé",
  "complemento": "lado ímpar",
  "bairro": "Sé",
  "cidade": "São Paulo",
  "estado": "SP",
  "frete": 7.85
}
```

Para a busca do endereço do CEP, foi utilizada a API do ViaCEP. O valor do frete é fixo de acordo com as regiões do Brasil: Sudeste (R$ 7,85), Centro-Oeste (R$ 12,50), Nordeste (R$ 15,98), Sul (R$ 17,30) e Norte (R$ 20,83). O CEP é obrigatório e pode ser passado com ou sem máscara na entrada e se o CEP não for encontrado uma mensagem informativa é retornada para o cliente.

<h1>Requisitos para o desenvolvimento:</h1>
Java 11
Spring Boot
API REST Template
Documentação Swagger
Testes unitários JUnit5
Testes automatizados utilizando Cucumber
Instruções de instalação e execução:
Clone o repositório: git clone https://github.com/seu-usuario/seu-repositorio.git
Navegue para o diretório do projeto: cd seu-repositorio
Compile o projeto: ./mvnw clean install
Execute o projeto: ./mvnw spring-boot:run
A API estará rodando em http://localhost:8080.

<h1>Documentação da API:</h1>
A documentação da API foi gerada automaticamente pelo Swagger. Para acessá-la, basta executar a aplicação e acessar o seguinte link: http://localhost:8080/swagger-ui.html

<h1>Testes:</h1>
A aplicação possui testes unitários JUnit5 e testes automatizados utilizando Cucumber. Para executá-los, basta navegar até o diretório raiz do projeto e rodar o seguinte comando: ./mvnw clean test

<h1>Melhorias futuras:</h1>
Algumas melhorias que poderiam ser implementadas em uma próxima versão da API:

Adicionar autenticação e autorização;
Adicionar cache para reduzir o tempo de resposta da API;
Adicionar mais opções de frete e permitir que o usuário selecione a opção desejada;
Adicionar validação de outros campos, como por exemplo, o formato do CEP.
