
# 📦 Delivery Management API

Sistema **Back-End** desenvolvido em **Java** com **Spring Boot** para gerenciar entregas, clientes e endereços associados via **REST APIs**.

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

1. [Visão Geral do Projeto](#visão-geral-do-projeto)  
2. [Funcionalidades](#funcionalidades)  
3. [Tecnologias Utilizadas](#tecnologias-utilizadas)  
4. [Como Rodar o Projeto](#como-rodar-o-projeto)  
5. [Como Executar os Testes](#como-executar-os-testes)  
6. [Containerização com Docker](#containerização-com-docker)  
7. [Documentação da API (Swagger)](#documentação-da-api-swagger)  
8. [Escolhas Arquiteturais e de Design](#escolhas-arquiteturais-e-de-design)  
9. [Estrutura de Dados da Entrega](#estrutura-de-dados-da-entrega)  
10. [Repositório e Contato](#repositório-e-contato)  

---

## 📝 Visão Geral do Projeto

Projeto que oferece uma **API RESTful** para o **gerenciamento de entregas**, com suporte completo a operações **CRUD**. Clientes e endereços são gerenciados automaticamente de forma inteligente, evitando duplicidades.

---

## ✨ Funcionalidades

- **Cadastrar Entrega**  
- **Atualizar Entrega**  
- **Consultar Entrega por ID**  
- **Excluir Entrega**  

> 📌 **Observação:**  
> Ao cadastrar uma entrega, o sistema verifica a existência do cliente (por CPF) e do endereço (por CEP, número e cliente). Se não existirem, serão criados automaticamente.

---

## 💻 Tecnologias Utilizadas

| Categoria               | Tecnologia                       | Versão/Observação         |
|------------------------|----------------------------------|---------------------------|
| Linguagem              | Java                             | 21                        |
| Framework              | Spring Boot                      | 3.x                       |
| Build                  | Apache Maven                     | -                         |
| Banco de Dados         | MySQL                            | 8.0                       |
| Banco para Testes      | H2 (em memória)                  | -                         |
| Documentação da API    | Springdoc OpenAPI (Swagger UI)   | -                         |
| Testes                 | JUnit 5, Mockito                 | -                         |
| Mapeamento de Objetos  | MapStruct                        | -                         |
| Containerização        | Docker, Docker Compose           | -                         |

---

## 🚀 Como Rodar o Projeto

### Pré-requisitos:
- Docker e Docker Compose instalados.

### Passos:
```bash
git clone https://github.com/vitorsbx1/delivery-management
cd delivery-management
docker-compose up --build
```

- A aplicação ficará disponível em: `http://localhost:8080`
- A documentação Swagger em: `http://localhost:8080/swagger-ui.html`

### Ambiente Limpo:
```bash
docker-compose down -v
```

### Rodando Localmente com H2:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=h2
```

---

## ⚙️ Como Executar os Testes

Execute os testes com:
```bash
mvn test
```

Inclui testes unitários nas camadas **Repository**, **Service** e **Controller** com **JUnit 5** e **Mockito**.

---

## 🐳 Containerização com Docker

A aplicação possui dois serviços definidos no `docker-compose.yml`:

- `mysql`: banco de dados persistente com MySQL 8.0
- `app`: aplicação Spring Boot com build multi-stage

### Comandos úteis:
```bash
docker-compose up --build       # subir os serviços
docker-compose down             # parar mantendo volumes
docker-compose down -v          # parar e remover volumes
```

---

## 📄 Documentação da API (Swagger)

Acesse após iniciar a aplicação:

```
http://localhost:8080/swagger-ui.html
```

---

## 💡 Escolhas Arquiteturais e de Design

- Arquitetura em Camadas: **Controller**, **Service**, **Repository**
- **Entidades vs DTOs**: separação entre modelo de domínio e dados da API
- **MapStruct**: mapeamento eficiente entre entidades e DTOs
- **Tratamento de Exceções**: com `@ControllerAdvice` e exceções customizadas
- **Validação com Bean Validation**: via anotações nos DTOs
- **Reutilização de Cliente/Endereço**: lógica `findOrCreate`
- **Logs**: com SLF4J/Logback

---

## 📦 Estrutura de Dados da Entrega

- **Entrega**:
  - ID
  - Quantidade de Pacotes
  - Data Limite
  - Cliente:
    - Nome
    - CPF
  - Endereço:
    - CEP, UF, Cidade, Bairro, Rua, Número, Complemento

---

## 🔗 Repositório e Contato

- Repositório: [https://github.com/vitorsbx1/delivery-management](https://github.com/vitorsbx1/delivery-management)

Sinta-se à vontade para contribuir ou entrar em contato para dúvidas!
