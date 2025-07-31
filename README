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



