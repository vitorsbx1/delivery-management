# Delivery Management API

Sistema BackEnd desenvolvido em Java com Spring Boot para gerenciar entregas, clientes e endereços associados via REST APIs.

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)
![REST API](https://img.shields.io/badge/REST%20API-4CAF50?style=for-the-badge&logo=cloudflare&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-00758F?style=for-the-badge&logo=mysql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![H2 Database](https://img.shields.io/badge/h2_database-09476b?style=for-the-badge&logo=h2database&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=white)
![JUnit](https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=junit5&logoColor=white)
![Mockito](https://img.shields.io/badge/Mockito-8892BF?style=for-the-badge&logo=mockito&logoColor=white)
![MapStruct](https://img.shields.io/badge/MapStruct-F89820?style=for-the-badge&logo=mapstruct&logoColor=white)

---

## 📁 Índice

1.  [Visão Geral do Projeto](#-visão-geral-do-projeto)
2.  [Funcionalidades](#-funcionalidades)
3.  [Tecnologias Utilizadas](#-tecnologias-utilizadas)
4.  [Como Rodar o Projeto](#-como-rodar-o-projeto)
5.  [Como Executar os Testes](#-como-executar-os-testes)
6.  [Containerização com Docker](#-containerizacao-com-docker)
7.  [Documentação da API (Swagger)](#-documentacao-da-api-swagger)
8.  [Escolhas Arquiteturais e de Design](#-escolhas-arquiteturais-e-de-design)
9.  [Estrutura de Dados da Entrega](#-estrutura-de-dados-da-entrega)
10. [Repositório e Contato](#-repositorio-e-contato)

---

<a name="-visão-geral-do-projeto"></a>
## 📝 Visão Geral do Projeto

Este projeto tem como objetivo principal fornecer uma **API RESTful** para o **gerenciamento de entregas**. Ele permite a realização de operações CRUD (Create, Read, Update, Delete) para entregas, associando-as a informações de clientes e endereços de destino.

---

<a name="-funcionalidades"></a>
## ✨ Funcionalidades

A API permite ao usuário realizar as seguintes operações com entregas:

* **Cadastrar Entrega**: Cria um novo registro de entrega. Se o cliente ou endereço já existirem com base em critérios específicos (CPF para cliente; CEP, número e cliente para endereço), eles são reutilizados; caso contrário, são cadastrados automaticamente.
* **Atualizar Entrega**: Modifica os detalhes de uma entrega existente.
* **Consultar Entrega (por ID)**: Recupera informações detalhadas de uma entrega específica.
* **Excluir Entrega**: Remove uma entrega do sistema.

---

<a name="-observacoes"></a>
## 📄 Observações
> - Ao cadastrar uma entrega, o sistema verifica se o cliente e o endereço já existem. Se não existirem, eles são criados automaticamente.


--

<a name="-tecnologias-utilizadas"></a>
## 💻 Tecnologias Utilizadas

| Categoria           | Tecnologia                       | Versão Principal |
| :------------------ | :------------------------------- | :--------------- |
| Linguagem           | **Java** | 21               |
| Framework           | **Spring Boot** | 3.x              |
| Ferramenta de Build | **Apache Maven** |                  |
| Banco de Dados Principal | **MySQL** | 8.0              |
| Banco de Dados de Teste | **H2 Database** | (Em memória)     |
| Containerização     | **Docker** |                  |
| Documentação API    | **Springdoc OpenAPI (Swagger)** |                  |
| Testes              | **JUnit 5**, **Mockito** |                  |
| Mapeamento de Objetos | **MapStruct** |                  |

---

<a name="-como-rodar-o-projeto"></a>
## 🚀 Como Rodar o Projeto

A forma recomendada para rodar a aplicação é utilizando **Docker Compose**, que irá orquestrar tanto o banco de dados MySQL quanto a própria aplicação.

### Pré-requisitos:

* **Docker Desktop** (ou Docker Engine e Docker Compose) instalado na sua máquina.

### Passos para rodar com Docker Compose:

1.  Clone este repositório para sua máquina local.
2.  Navegue até a raiz do projeto no seu terminal.
3.  Execute o comando para construir e iniciar os serviços:

    ```bash
    docker-compose up --build
    ```

    Isso fará o seguinte:
    * Baixará a imagem do MySQL 8.0 (se ainda não tiver).
    * Construirá a imagem da sua aplicação Spring Boot a partir do `Dockerfile`.
    * Iniciará o contêiner do MySQL (porta `3306`).
    * Iniciará o contêiner da aplicação (porta `8080`), que se conectará automaticamente ao MySQL.

4.  A aplicação estará disponível em `http://localhost:8080`.

**Observação:** O banco de dados MySQL está configurado para **persistir os dados** através de um volume (`mysql-data`). Se precisar de um ambiente limpo, você pode derrubar os serviços e remover o volume:

```bash
docker-compose down -v
```

> A documentação das APIs está disponível via Swagger:

> `http://localhost:8080/swagger-ui/index.html`


Claro! Aqui está o restante do conteúdo que você pediu, formatado corretamente em Markdown:

Markdown

### Rodando localmente com H2 (apenas para testes ou desenvolvimento local sem Docker)

Caso você não consiga rodar o Docker ou queira testar a aplicação com o banco de dados H2 em memória:

1.  Certifique-se de ter **Java 21** e **Maven** instalados.
2.  Navegue até a raiz do projeto no seu terminal.
3.  Execute o comando para compilar e rodar a aplicação, ativando o perfil `h2`:

    ```bash
    mvn spring-boot:run -Dspring-boot.run.profiles=h2
    ```

    Neste modo, a aplicação utilizará um banco de dados H2 em memória, que será **limpo a cada reinício** da aplicação.

---

<a name="-como-executar-os-testes"></a>
## ⚙️ Como Executar os Testes

Os testes são cruciais para garantir a qualidade e robustez da aplicação. Este projeto possui **testes unitários** abrangentes para as camadas de Repository, Service e Controller, utilizando **JUnit 5** e **Mockito**.

Os testes de Repository utilizam um banco de dados H2 em memória para simular a persistência de forma rápida e isolada.

Para executar todos os testes:

```bash
mvn test
<a name="-containerizacao-com-docker"></a>

🐳 Containerização com Docker
A aplicação é totalmente containerizada utilizando Docker e Docker Compose, facilitando o ambiente de desenvolvimento e a implantação.

O arquivo docker-compose.yml define dois serviços:

mysql: Um contêiner com MySQL 8.0 que serve como banco de dados persistente para a aplicação. As credenciais e o nome do banco são configurados através de variáveis de ambiente no docker-compose.yml.

app: Um contêiner que executa a aplicação Spring Boot. Ele é construído a partir do Dockerfile na raiz do projeto, que utiliza uma abordagem multi-stage build para otimizar o tamanho final da imagem:

Fase de Build: Compila o projeto Java usando Maven.

Fase de Run: Cria uma imagem leve contendo apenas o JAR executável e a JRE necessária para rodar a aplicação.

Comandos Docker úteis:
Para subir os serviços:

Bash

docker-compose up --build
Para parar os serviços (mantendo os dados do MySQL):

Bash

docker-compose down
Para parar os serviços e remover os volumes de dados (útil para começar com um banco de dados limpo):

Bash

docker-compose down -v
<a name="-documentacao-da-api-swagger"></a>

📄 Documentação da API (Swagger)
A documentação interativa da API é gerada automaticamente pelo Springdoc OpenAPI (Swagger UI). Ela permite que você visualize todos os endpoints disponíveis, seus parâmetros de requisição, modelos de dados (DTOs) e possíveis respostas.

Após iniciar a aplicação (seja via Docker ou localmente), acesse a documentação em:

http://localhost:8080/swagger-ui.html
<a name="-escolhas-arquiteturais-e-de-design"></a>

💡 Escolhas Arquiteturais e de Design
Arquitetura em Camadas (MVC): O projeto segue a estrutura tradicional de Controller, Service e Repository, promovendo a separação de preocupações (Separation of Concerns).

Controller: Responsável por lidar com requisições HTTP e retornar respostas. Foca apenas na interação com a web.

Service: Contém a lógica de negócio principal. Orquestra as operações, valida regras de negócio e interage com a camada de repositório.

Repository: Abstrai a camada de persistência de dados, interagindo diretamente com o banco de dados.

Entidades e DTOs (Data Transfer Objects):

Entidades: Representam a estrutura dos dados no banco de dados.

DTOs: Utilizados para entrada (requisições) e saída (respostas) da API, desacoplando o contrato da API da estrutura interna das entidades.

MapStruct: Escolhido para realizar o mapeamento entre Entidades e DTOs. Isso reduz o boilerplate code, evita erros manuais e melhora a performance em comparação com mapeamentos manuais ou reflection.

Tratamento de Exceções: Exceções customizadas (DeliveryNotFoundException, CustomerNotFoundException, AddressDeliveryNotFoundException) foram criadas para cenários específicos. Um GlobalExceptionHandler (@ControllerAdvice) é utilizado para centralizar o tratamento dessas exceções, retornando respostas HTTP consistentes (ex: 404 Not Found, 400 Bad Request) para o cliente da API.

Validação de Dados: Utiliza a especificação Bean Validation (JSR 380) com anotações (@NotNull, @Valid, etc.) nos DTOs de requisição para garantir a integridade dos dados de entrada antes que cheguem à lógica de negócio.

Reutilização de Clientes e Endereços: A lógica findOrCreate nos serviços de Customer e AddressDelivery garante que, se um cliente com o mesmo CPF ou um endereço com o mesmo CEP, número e associado ao mesmo cliente já existirem, eles serão reutilizados em vez de criar novas entradas, evitando duplicidade e mantendo a consistência dos dados.

Controle de Logs: A aplicação utiliza o sistema de logging padrão do Spring Boot (SLF4J/Logback) para registrar eventos importantes, erros e informações de depuração, facilitando o monitoramento e a identificação de problemas.

<a name="-estrutura-de-dados-da-entrega"></a>

📦 Estrutura de Dados da Entrega
Cada entrega é composta pelos seguintes campos:

ID de Entrega: Identificador único da entrega.

Quantidade de Pacotes: Número de itens ou volumes na entrega.

Data Limite de Entrega: Prazo máximo para a entrega ser concluída.

Cliente:

Nome do Cliente

CPF do Cliente

Endereço de Destino da Entrega:

CEP

UF (Unidade Federativa)

Cidade

Bairro

Rua

Número

Complemento (Opcional)

<a name="-repositorio-e-contato"></a>

🔗 Repositório e Contato
O código-fonte completo do projeto está disponível no repositório público:

https://github.com/vitorsbx1/delivery-management

Para qualquer dúvida ou contribuição, sinta-se à vontade para entrar em contato.
