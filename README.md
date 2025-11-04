# 🏋️‍♂️ Academia API

API RESTful profissional para gerenciamento completo de academias de ginástica, desenvolvida com **Spring Boot 3.2.5**, **Spring Data JPA**, **Bean Validation** e arquitetura em camadas.

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

---

## 📋 Índice

- [✨ Características](#-características)
- [🛠 Tecnologias](#-tecnologias)
- [📁 Estrutura do Projeto](#-estrutura-do-projeto)
- [🚀 Instalação e Execução](#-instalação-e-execução)
- [⚙️ Configuração de Profiles](#️-configuração-de-profiles)
- [📡 Endpoints da API](#-endpoints-da-api)
- [🗂 Modelos de Dados](#-modelos-de-dados)
- [✅ Validações e Regras de Negócio](#-validações-e-regras-de-negócio)
- [🚨 Tratamento de Erros](#-tratamento-de-erros)
- [📊 Documentação Swagger](#-documentação-swagger)
- [🧪 Testes](#-testes)
- [📃 Banco de Dados](#-banco-de-dados)
- [🤝 Contribuição](#-contribuição)
- [📄 Licença](#-licença)

---

## ✨ Características

✅ **CRUD Completo** - Gerenciamento de Academias, Alunos, Instrutores, Planos e Treinos

✅ **Arquitetura Profissional** - Separação em camadas (Controller, Service, Repository, DTO)

✅ **Validação Robusta** - Bean Validation com mensagens personalizadas

✅ **Tratamento Global de Exceções** - Respostas padronizadas e informativas

✅ **DTOs** - Transferência de dados otimizada e segura

✅ **Paginação** - Lista de alunos com suporte a paginação e ordenação

✅ **Regras de Negócio** - Validação de idade mínima (16 anos) e integridade de dados

✅ **Perfis de Configuração** - Ambientes separados (dev, test, prod)

✅ **Documentação Swagger** - Interface interativa para exploração da API

✅ **Testes Automatizados** - Cobertura de testes unitários e de integração

✅ **Múltiplos Bancos de Dados** - H2 (desenvolvimento/testes) e PostgreSQL (produção)

✅ **Monitoramento** - Spring Boot Actuator para health checks e métricas

✅ **Logs Detalhados** - Configuração específica por ambiente

---

## 🛠 Tecnologias

### Core
- **Java 17** - Linguagem de programação
- **Spring Boot 3.2.5** - Framework principal
- **Spring Data JPA** - Persistência de dados
- **Spring Boot Validation** - Validação de dados
- **Spring Boot Actuator** - Monitoramento e métricas

### Banco de Dados
- **H2 Database** - Banco em memória para desenvolvimento e testes
- **PostgreSQL** - Banco de dados para produção

### Documentação
- **SpringDoc OpenAPI 2.3.0** - Documentação Swagger/OpenAPI

### Testes
- **JUnit 5** - Framework de testes
- **Mockito** - Mocks para testes unitários
- **REST Assured** - Testes de API
- **AssertJ** - Asserções fluentes
- **JaCoCo 0.8.11** - Cobertura de código

### Build
- **Maven 3.6+** - Gerenciamento de dependências e build

---

## 📁 Estrutura do Projeto

```text
Academia-API/
├── src/
│   ├── main/
│   │   ├── java/com/academia/
│   │   │   ├── controller/              # Controladores REST
│   │   │   │   ├── AcademiaController.java
│   │   │   │   ├── AlunoController.java
│   │   │   │   ├── InstrutorController.java
│   │   │   │   ├── PlanoController.java
│   │   │   │   └── TreinoController.java
│   │   │   ├── dto/                     # Data Transfer Objects
│   │   │   │   ├── AlunoDto.java
│   │   │   │   ├── CreateAlunoDto.java
│   │   │   │   ├── CreateTreinoDto.java
│   │   │   │   └── PlanoDto.java
│   │   │   ├── model/                   # Entidades JPA
│   │   │   │   ├── Academia.java
│   │   │   │   ├── Aluno.java
│   │   │   │   ├── Instrutor.java
│   │   │   │   ├── Plano.java
│   │   │   │   └── Treino.java
│   │   │   ├── repository/              # Repositórios JPA
│   │   │   │   ├── AcademiaRepository.java
│   │   │   │   ├── AlunoRepository.java
│   │   │   │   ├── InstrutorRepository.java
│   │   │   │   ├── PlanoRepository.java
│   │   │   │   └── TreinoRepository.java
│   │   │   ├── service/                 # Lógica de negócio
│   │   │   │   ├── AlunoService.java
│   │   │   │   └── PlanoService.java
│   │   │   ├── exception/               # Tratamento de exceções
│   │   │   │   ├── ApiExceptionHandler.java
│   │   │   │   ├── BadRequestException.java
│   │   │   │   ├── ResourceNotFoundException.java
│   │   │   │   └── ErrorResponse.java
│   │   │   └── AcademiaApiApplication.java
│   │   └── resources/
│   │       ├── application.yml          # Configuração principal
│   │       ├── application-dev.yml      # Perfil de desenvolvimento
│   │       ├── application-test.yml     # Perfil de testes
│   │       └── application-prod.yml     # Perfil de produção
│   └── test/
│       └── java/com/academia/
│           ├── controller/
│           │   └── AlunoControllerIntegrationTest.java
│           └── service/
│               ├── AlunoServiceTest.java
│               └── PlanoServiceTest.java
├── pom.xml
├── LICENSE
└── README.md
```

---

## 🚀 Instalação e Execução

### Pré-requisitos

- Java 17 ou superior
- Maven 3.6 ou superior
- PostgreSQL 12+ (apenas para produção)

### Clone o repositório

```bash
git clone https://github.com/Otavio2007/academia-api.git
cd academia-api
```

### Compilar o projeto

```bash
mvn clean install
```

### Executar a aplicação

#### Ambiente de Desenvolvimento (H2)
```bash
mvn spring-boot:run
```

#### Ambiente de Produção (PostgreSQL)
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

#### Ambiente de Testes
```bash
mvn test
```

### Acessar a aplicação

- **API Base**: `http://localhost:8080`
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **API Docs**: `http://localhost:8080/api-docs`
- **Console H2** (apenas dev): `http://localhost:8080/h2-console`
- **Actuator**: `http://localhost:8080/actuator`

---

## ⚙️ Configuração de Profiles

### Development (dev) - Padrão

```yaml
# H2 Database em memória
spring.datasource.url: jdbc:h2:mem:academia_dev
spring.datasource.username: [...]
spring.datasource.password: [...]

# Console H2 habilitado
spring.h2.console.enabled: true

# Logs detalhados
logging.level.com.academia: DEBUG
```

### Test

```yaml
# H2 Database isolado para testes
spring.datasource.url: jdbc:h2:mem:academia_test
spring.jpa.hibernate.ddl-auto: create-drop

# Logs mínimos
logging.level.root: WARN
```

### Production (prod)

```yaml
# PostgreSQL
spring.datasource.url: ${DATABASE_URL}
spring.datasource.username: ${DB_USERNAME}
spring.datasource.password: ${DB_PASSWORD}

# Swagger desabilitado
springdoc.swagger-ui.enabled: false

# Segurança aumentada
server.error.include-message: never
```

---

## 📡 Endpoints da API

### 🏢 Academias

| Método | Endpoint            | Descrição                | Autenticação |
|--------|---------------------|--------------------------|--------------|
| GET    | `/api/academias`      | Lista todas as academias | Não          |
| GET    | `/api/academias/{id}` | Busca academia por ID    | Não          |
| POST   | `/api/academias`      | Cria nova academia       | Não          |
| PUT    | `/api/academias/{id}` | Atualiza academia        | Não          |
| DELETE | `/api/academias/{id}` | Remove academia          | Não          |

### 👥 Alunos

| Método | Endpoint         | Descrição                | Parâmetros                    |
|--------|------------------|--------------------------|-------------------------------|
| GET    | `/api/alunos`      | Lista alunos (paginado)  | `page`, `size`, `sort`        |
| GET    | `/api/alunos/{id}` | Busca aluno por ID       | -                             |
| POST   | `/api/alunos`      | Cadastra novo aluno      | Body: `CreateAlunoDto`        |
| PUT    | `/api/alunos/{id}` | Atualiza dados do aluno  | Body: `CreateAlunoDto`        |
| DELETE | `/api/alunos/{id}` | Remove aluno             | -                             |

### 🏃‍♂️ Instrutores

| Método | Endpoint              | Descrição                   |
|--------|-----------------------|-----------------------------|
| GET    | `/api/instrutores`      | Lista todos os instrutores  |
| GET    | `/api/instrutores/{id}` | Busca instrutor por ID      |
| POST   | `/api/instrutores`      | Cadastra novo instrutor     |
| PUT    | `/api/instrutores/{id}` | Atualiza dados do instrutor |
| DELETE | `/api/instrutores/{id}` | Remove instrutor            |

### 💳 Planos

| Método | Endpoint         | Descrição             |
|--------|------------------|-----------------------|
| GET    | `/api/planos`      | Lista todos os planos |
| GET    | `/api/planos/{id}` | Busca plano por ID    |
| POST   | `/api/planos`      | Cria novo plano       |
| PUT    | `/api/planos/{id}` | Atualiza plano        |
| DELETE | `/api/planos/{id}` | Remove plano          |

### 🏋️‍♀️ Treinos

| Método | Endpoint          | Descrição              |
|--------|-------------------|------------------------|
| GET    | `/api/treinos`      | Lista todos os treinos |
| GET    | `/api/treinos/{id}` | Busca treino por ID    |
| POST   | `/api/treinos`      | Cria novo treino       |
| PUT    | `/api/treinos/{id}` | Atualiza treino        |
| DELETE | `/api/treinos/{id}` | Remove treino          |

---

## 🗂 Modelos de Dados

### Academia
```json
{
  "id": 1,
  "nome": "Academia Fitness Pro",
  "endereco": "Rua das Flores, 123 - Centro",
  "telefone": "(11) 99999-9999"
}
```

### Aluno (Response)
```json
{
  "id": 1,
  "nome": "João Silva",
  "email": "joao@email.com",
  "dataNascimento": "1990-05-15",
  "plano": {
    "id": 1,
    "nome": "Plano Mensal",
    "valorMensal": 89.90
  },
  "quantidadeTreinos": 5
}
```

### CreateAlunoDto (Request)
```json
{
  "nome": "João Silva",
  "email": "joao@email.com",
  "dataNascimento": "1990-05-15",
  "planoId": 1
}
```

### Instrutor
```json
{
  "id": 1,
  "nome": "Maria Santos",
  "especialidade": "Musculação e Crossfit"
}
```

### Plano
```json
{
  "id": 1,
  "nome": "Plano Premium",
  "valorMensal": 149.90
}
```

### Treino
```json
{
  "id": 1,
  "descricao": "Treino de peito e tríceps - 3x12 repetições",
  "aluno": {
    "id": 1,
    "nome": "João Silva"
  },
  "instrutor": {
    "id": 1,
    "nome": "Maria Santos"
  },
  "dataCriacao": "2025-01-15T10:30:00"
}
```

---

## ✅ Validações e Regras de Negócio

### Academia
- ✓ Nome: obrigatório, 2-100 caracteres
- ✓ Endereço: obrigatório, 5-200 caracteres

### Aluno
- ✓ Nome: obrigatório, 2-100 caracteres
- ✓ Email: obrigatório, formato válido, **único no sistema**
- ✓ Data de Nascimento: deve ser no passado
- ✓ **Idade mínima: 16 anos** (regra de negócio)
- ✓ Plano: opcional, mas se informado deve existir

### Instrutor
- ✓ Nome: obrigatório, 2-100 caracteres
- ✓ Especialidade: obrigatório, 2-50 caracteres

### Plano
- ✓ Nome: obrigatório, 2-50 caracteres
- ✓ Valor Mensal: obrigatório, **maior que zero**

### Treino
- ✓ Descrição: obrigatório
- ✓ Aluno e Instrutor: devem existir no sistema
- ✓ Data de criação: gerada automaticamente

---

## 🚨 Tratamento de Erros

### Erro de Validação (400 Bad Request)
```json
{
  "nome": "Nome é obrigatório",
  "email": "Email deve ser válido",
  "dataNascimento": "Data de nascimento deve ser no passado"
}
```

### Erro de Regra de Negócio (400 Bad Request)
```json
{
  "code": "BAD_REQUEST",
  "message": "Idade mínima permitida é 16 anos",
  "timestamp": "2025-11-04T10:30:00"
}
```

### Email Duplicado (400 Bad Request)
```json
{
  "code": "DATA_INTEGRITY_VIOLATION",
  "message": "Email já cadastrado",
  "timestamp": "2025-11-04T10:30:00"
}
```

### Recurso Não Encontrado (404 Not Found)
```json
{
  "code": "RESOURCE_NOT_FOUND",
  "message": "Aluno não encontrado",
  "timestamp": "2025-11-04T10:30:00"
}
```

### Erro Interno (500 Internal Server Error)
```json
{
  "code": "INTERNAL_SERVER_ERROR",
  "message": "Erro interno do servidor",
  "timestamp": "2025-11-04T10:30:00"
}
```

---

## 📊 Documentação Swagger

A API possui documentação interativa completa via Swagger UI.

**Acesse**: `http://localhost:8080/swagger-ui.html`

### Recursos do Swagger
- 📖 Documentação completa de todos os endpoints
- 🧪 Teste interativo das requisições
- 📝 Visualização de modelos de dados
- ✅ Validação de schemas
- 🔍 Filtros por tags (Academias, Alunos, etc.)

---

## 🧪 Testes

### Executar todos os testes
```bash
mvn test
```

### Executar apenas testes unitários
```bash
mvn test -Dtest=*ServiceTest
```

### Executar apenas testes de integração
```bash
mvn test -Dtest=*IntegrationTest
```

### Gerar relatório de cobertura (JaCoCo)
```bash
mvn clean test jacoco:report
```
📊 Relatório disponível em: `target/site/jacoco/index.html`

### Estrutura de Testes

#### Testes Unitários (Service Layer)
- `AlunoServiceTest.java` - 8 cenários de teste
- `PlanoServiceTest.java` - 8 cenários de teste

#### Testes de Integração (Controller + Service + Repository)
- `AlunoControllerIntegrationTest.java` - 9 cenários end-to-end

### Cobertura Mínima Exigida
- **50% de cobertura de linha** (configurado no JaCoCo)

---

## 📃 Banco de Dados

### Desenvolvimento (H2)
```yaml
URL: jdbc:h2:mem:academia_dev
Driver: org.h2.Driver
Username: panda27
Password: panda27
Console: http://localhost:8080/h2-console
DDL: create-drop
```

### Produção (PostgreSQL)
```yaml
URL: jdbc:postgresql://localhost:5432/academia_prod
Driver: org.postgresql.Driver
Username: ${DB_USERNAME}
Password: ${DB_PASSWORD}
DDL: validate (requer migrations)
```

### Diagrama de Relacionamentos

```
┌─────────────┐         ┌──────────┐
│   Academia  │         │  Plano   │
└─────────────┘         └──────────┘
                              │
                              │ 1
                              │
                           N  │
                        ┌─────▼────┐
                        │  Aluno   │
                        └─────┬────┘
                              │ 1
                              │
                           N  │
                        ┌─────▼────┐         ┌────────────┐
                        │  Treino  │◄────────┤ Instrutor  │
                        └──────────┘    N    └────────────┘
                                         1
```

### Relacionamentos
- **Aluno ↔ Plano**: Many-to-One (opcional)
- **Aluno ↔ Treino**: One-to-Many (cascade all, orphan removal)
- **Instrutor ↔ Treino**: One-to-Many

---

## 🤝 Contribuição

Contribuições são bem-vindas! Siga os passos:

1. **Fork** o projeto
2. Crie sua branch de feature
   ```bash
   git checkout -b feature/MinhaNovaFeature
   ```
3. Commit suas mudanças
   ```bash
   git commit -m 'feat: Adiciona nova feature X'
   ```
4. Push para a branch
   ```bash
   git push origin feature/MinhaNovaFeature
   ```
5. Abra um **Pull Request**

### Padrões de Commit
- `feat:` Nova funcionalidade
- `fix:` Correção de bug
- `docs:` Apenas documentação
- `test:` Adiciona ou modifica testes
- `refactor:` Refatoração de código

---

## 📄 Licença

Este projeto está sob a licença **MIT**. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

## 👨‍💻 Autor

Desenvolvido com ☕ Java, 🍃 Spring Boot e 💚 por **[Otavio2007](https://github.com/Otavio2007)**

---

## 🌟 Mostre seu apoio

Se este projeto foi útil para você, considere dar uma ⭐ no repositório!

[![GitHub Stars](https://img.shields.io/github/stars/Otavio2007/academia-api?style=social)](https://github.com/Otavio2007/academia-api)
