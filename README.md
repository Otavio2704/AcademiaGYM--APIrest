# 🏋️‍♂️ Academia API

API RESTful completa para gerenciamento de academia de ginástica, desenvolvida com **Spring Boot 3.2.5**, **Spring Data JPA** e **Maven**.

---

## 📋 Índice

* [✨ Características](#-características)
* [🛠 Tecnologias](#-tecnologias)
* [📁 Estrutura do Projeto](#-estrutura-do-projeto)
* [🚀 Instalação e Execução](#-instalação-e-execução)
* [📡 Endpoints da API](#-endpoints-da-api)
* [🗂 Modelos de Dados](#-modelos-de-dados)
* [✅ Validações](#-validações)
* [🚨 Tratamento de Erros](#-tratamento-de-erros)
* [📃 Banco de Dados](#-banco-de-dados)
* [🤝 Contribuição](#-contribuição)
* [📄 Licença](#-licença)

---

## ✨ Características

✅ CRUD completo para todas as entidades

✅ Validação de dados com Bean Validation

✅ Tratamento global de exceções

✅ Relacionamentos JPA bem estruturados

✅ Console H2 para desenvolvimento

✅ Logs detalhados do Hibernate

✅ Arquitetura em camadas (Controller, Service, Repository)

✅ Documentação completa dos endpoints

---

## 🛠 Tecnologias

* Java 17
* Spring Boot 3.2.5
* Spring Data JPA
* Spring Boot Actuator
* H2 Database (em memória)
* Maven 3.6+
* Bean Validation (Hibernate Validator)

---

## 📁 Estrutura do Projeto

```text
Academia-API/
├── src/
│   ├── main/
│   │   ├── java/com/academia/
│   │   │   ├── controller/           # Controladores REST
│   │   │   │   ├── AcademiaController.java
│   │   │   │   ├── AlunoController.java
│   │   │   │   ├── InstrutorController.java
│   │   │   │   ├── PlanoController.java
│   │   │   │   └── TreinoController.java
│   │   │   ├── model/                # Entidades JPA
│   │   │   │   ├── Academia.java
│   │   │   │   ├── Aluno.java
│   │   │   │   ├── Instrutor.java
│   │   │   │   ├── Plano.java
│   │   │   │   └── Treino.java
│   │   │   ├── repository/           # Repositórios JPA
│   │   │   │   ├── AcademiaRepository.java
│   │   │   │   ├── AlunoRepository.java
│   │   │   │   ├── InstrutorRepository.java
│   │   │   │   ├── PlanoRepository.java
│   │   │   │   └── TreinoRepository.java
│   │   │   ├── exception/            # Tratamento de exceções
│   │   │   │   ├── ApiExceptionHandler.java
│   │   │   │   ├── BadRequestException.java
│   │   │   │   └── ErrorResponse.java
│   │   │   └── AcademiaApiApplication.java
│   │   └── resources/
│   │       └── application.properties
├── pom.xml
└── README.md
```

---

## 🚀 Instalação e Execução

### Pré-requisitos

* Java 17+
* Maven 3.6+

### Passos para execução

Clone o repositório:

```bash
git clone https://github.com/seu-usuario/academia-api.git
cd academia-api
```

Compile o projeto:

```bash
mvn clean compile
```

Execute a aplicação:

```bash
mvn spring-boot:run
```

Acesse:

* API: [http://localhost:8080](http://localhost:8080)
* Console H2: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

**JDBC URL:** `jdbc:h2:mem:academia`
**Username:** `panda27`
**Password:** `panda27`

---

## 📡 Endpoints da API

### 🏢 Academias

| Método | Endpoint            | Descrição                |
| ------ | ------------------- | ------------------------ |
| GET    | /api/academias      | Lista todas as academias |
| GET    | /api/academias/{id} | Busca academia por ID    |
| POST   | /api/academias      | Cria nova academia       |
| PUT    | /api/academias/{id} | Atualiza academia        |
| DELETE | /api/academias/{id} | Remove academia          |

### 👥 Alunos

| Método | Endpoint         | Descrição               |
| ------ | ---------------- | ----------------------- |
| GET    | /api/alunos      | Lista todos os alunos   |
| GET    | /api/alunos/{id} | Busca aluno por ID      |
| POST   | /api/alunos      | Cadastra novo aluno     |
| PUT    | /api/alunos/{id} | Atualiza dados do aluno |
| DELETE | /api/alunos/{id} | Remove aluno            |

### 🏃‍♂️ Instrutores

| Método | Endpoint              | Descrição                   |
| ------ | --------------------- | --------------------------- |
| GET    | /api/instrutores      | Lista todos os instrutores  |
| GET    | /api/instrutores/{id} | Busca instrutor por ID      |
| POST   | /api/instrutores      | Cadastra novo instrutor     |
| PUT    | /api/instrutores/{id} | Atualiza dados do instrutor |
| DELETE | /api/instrutores/{id} | Remove instrutor            |

### 💳 Planos

| Método | Endpoint         | Descrição             |
| ------ | ---------------- | --------------------- |
| GET    | /api/planos      | Lista todos os planos |
| GET    | /api/planos/{id} | Busca plano por ID    |
| POST   | /api/planos      | Cria novo plano       |
| PUT    | /api/planos/{id} | Atualiza plano        |
| DELETE | /api/planos/{id} | Remove plano          |

### 🏋️‍♀️ Treinos

| Método | Endpoint          | Descrição              |
| ------ | ----------------- | ---------------------- |
| GET    | /api/treinos      | Lista todos os treinos |
| GET    | /api/treinos/{id} | Busca treino por ID    |
| POST   | /api/treinos      | Cria novo treino       |
| PUT    | /api/treinos/{id} | Atualiza treino        |
| DELETE | /api/treinos/{id} | Remove treino          |

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

### Aluno

```json
{
  "id": 1,
  "nome": "João Silva",
  "email": "joao@email.com",
  "dataNascimento": "1990-05-15",
  "plano": {
    "id": 1,
    "nome": "Plano Mensal"
  }
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
  "valorMensal": 89.90
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

## ✅ Validações

### Academia

* **Nome:** Obrigatório, entre 2-100 caracteres
* **Endereço:** Obrigatório, entre 5-200 caracteres

### Aluno

* **Nome:** Obrigatório, entre 2-100 caracteres
* **Email:** Obrigatório, formato válido, único
* **Data de Nascimento:** Deve ser no passado

### Instrutor

* **Nome:** Obrigatório, entre 2-100 caracteres
* **Especialidade:** Obrigatório, entre 2-50 caracteres

### Plano

* **Nome:** Obrigatório, entre 2-50 caracteres
* **Valor Mensal:** Obrigatório, maior que zero

### Treino

* **Descrição:** Obrigatório

---

## 🚨 Tratamento de Erros

### Exemplo de Erro de Validação (400)

```json
{
  "nome": "Nome é obrigatório",
  "email": "Email deve ser válido"
}
```

### Exemplo de Erro Personalizado (400)

```json
{
  "code": "BAD_REQUEST",
  "message": "Email já cadastrado",
  "timestamp": "2025-01-15T10:30:00"
}
```

### Exemplo de Recurso Não Encontrado (404)

```json
{
  "code": "RESOURCE_NOT_FOUND",
  "message": "Aluno não encontrado",
  "timestamp": "2025-01-15T10:30:00"
}
```

---

## 📃 Banco de Dados

* **Banco:** H2 Database (em memória)
* **JDBC URL:** `jdbc:h2:mem:academia`
* **Driver:** `org.h2.Driver`
* **Username:** `(...)`
* **Password:** `(...)`
* **Console H2:** `/h2-console`
* **DDL:** `hibernate.ddl-auto=update`

### Relacionamentos

* Aluno ↔ Plano: Many-to-One
* Aluno ↔ Treino: One-to-Many
* Instrutor ↔ Treino: One-to-Many

---

## 🤝 Contribuição

1. Faça um fork do projeto
2. Crie uma branch (`git checkout -b feature/nova-feature`)
3. Commit suas alterações (`git commit -m 'Adiciona nova feature'`)
4. Push para a branch (`git push origin feature/nova-feature`)
5. Abra um Pull Request 🚀

---

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.

---

Desenvolvido com ☕ e 🍃 por [Otavio2007](https://github.com/Otavio2007)
🌟 Se este projeto foi útil para você, considere dar uma estrela!
