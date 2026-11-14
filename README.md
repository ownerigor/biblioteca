# API Biblioteca

API REST simples para organizar autores e livros de uma biblioteca e controlar empréstimos.

## Problema resolvido

O projeto substitui controles manuais do acervo. Ele permite saber quais livros existem, quem são seus autores e se um livro está disponível ou emprestado.

## Funcionalidades

- CRUD completo de autores.
- CRUD completo de livros.
- Consulta de livros disponíveis.
- Cadastro e consulta de empréstimos.
- Registro de devolução.
- Validação dos dados recebidos.
- Documentação interativa com Swagger/OpenAPI.

## Regras de negócio

1. Um livro indisponível não pode ser emprestado novamente.
2. A data prevista de devolução é calculada automaticamente para 14 dias após o empréstimo.
3. Ao emprestar, o livro fica indisponível; ao devolver, volta a ficar disponível.
4. Uma devolução não pode ser registrada duas vezes.

## Tecnologias

- Java 17
- Spring Boot
- Spring Web MVC
- Spring Data JPA
- Bean Validation
- PostgreSQL
- Swagger/OpenAPI
- Lombok

## Como executar

1. Tenha o Java 17 e o PostgreSQL instalados.
2. Crie no PostgreSQL um banco chamado `biblioteca`.
3. Confira usuário e senha em `src/main/resources/application.properties`.
4. Na pasta do projeto, execute:

```powershell
.\mvnw.cmd spring-boot:run
```

A API ficará disponível em `http://localhost:8080` e a documentação em `http://localhost:8080/documentation`.

## Ordem de uso e exemplos

### 1. Cadastrar um autor

`POST /autores`

```json
{
  "nome": "Machado de Assis",
  "nacionalidade": "Brasileira"
}
```

Resposta `201 Created`:

```json
{
  "id": 1,
  "nome": "Machado de Assis",
  "nacionalidade": "Brasileira"
}
```

### 2. Cadastrar um livro

`POST /livros`

```json
{
  "titulo": "Dom Casmurro",
  "isbn": "9788535910663",
  "autor": {
    "id": 1
  }
}
```

### 3. Realizar um empréstimo

`POST /emprestimos`

```json
{
  "nomeLeitor": "Ana Silva",
  "livro": {
    "id": 1
  }
}
```

### 4. Registrar a devolução

`PATCH /emprestimos/1/devolucao`

## Endpoints

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/autores` | Cadastrar autor |
| GET | `/autores` | Listar autores |
| GET | `/autores/{id}` | Consultar autor |
| PUT | `/autores/{id}` | Atualizar autor |
| DELETE | `/autores/{id}` | Excluir autor |
| POST | `/livros` | Cadastrar livro |
| GET | `/livros` | Listar livros |
| GET | `/livros/disponiveis` | Listar livros disponíveis |
| GET | `/livros/{id}` | Consultar livro |
| PUT | `/livros/{id}` | Atualizar livro |
| DELETE | `/livros/{id}` | Excluir livro |
| POST | `/emprestimos` | Realizar empréstimo |
| GET | `/emprestimos` | Listar empréstimos |
| GET | `/emprestimos/{id}` | Consultar empréstimo |
| PATCH | `/emprestimos/{id}/devolucao` | Registrar devolução |
