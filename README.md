# JavaEspacial

## Descrição

API REST desenvolvida em Java utilizando Quarkus e Oracle Database para gerenciamento de usuários, agências espaciais, missões e favoritos.

## Tecnologias Utilizadas

* Java
* Quarkus
* Oracle Database
* Maven
* Swagger/OpenAPI

## Configuração do Banco de Dados

O acesso ao banco é realizado através do arquivo:

`src/main/resources/application.properties`

Configurações utilizadas:

```properties
quarkus.datasource.username=567548
quarkus.datasource.password=130905
quarkus.datasource.jdbc.url=jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL
```

Para executar o projeto, substitua `SEU_USUARIO` e `SUA_SENHA` pelas credenciais do banco Oracle.

## Executando o Projeto

1. Clonar o repositório.
2. Abrir o projeto na IDE.
3. Configurar as credenciais do banco em `application.properties`.
4. Executar:

```bash
mvn quarkus:dev
```

A aplicação ficará disponível em:

```text
http://localhost:8080
```

## Documentação da API

Swagger:

```text
http://localhost:8080/q/swagger-ui
```

## Entidades

* Usuário
* Agência
* Missão
* Favorito

## Endpoints Principais

### Usuários

* GET /usuarios
* GET /usuarios/{id}
* POST /usuarios
* PUT /usuarios/{id}
* DELETE /usuarios/{id}

### Agências

* GET /agencias
* GET /agencias/{id}
* POST /agencias
* PUT /agencias/{id}
* DELETE /agencias/{id}

### Missões

* GET /missoes
* GET /missoes/{id}
* POST /missoes
* PUT /missoes/{id}
* DELETE /missoes/{id}

### Favoritos

* GET /favoritos
* GET /favoritos/{id}
* POST /favoritos
* DELETE /favoritos/{id}
