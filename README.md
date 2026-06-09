# CosmoHub API

## Sobre o Projeto

O CosmoHub é uma API REST desenvolvida em Java utilizando Quarkus e Oracle Database. A aplicação permite o gerenciamento de usuários, agências espaciais, missões e favoritos, fornecendo operações completas de cadastro, consulta, atualização e remoção de dados.

O projeto foi desenvolvido para a disciplina de Java Advanced da FIAP como parte da Global Solution.

---

## Tecnologias Utilizadas

* Java 21
* Quarkus
* Oracle Database
* JDBC
* Maven
* Swagger / OpenAPI
* Git e GitHub
* Render (Deploy)

---

## Arquitetura do Projeto

O projeto está organizado nas seguintes camadas:

```text
src/main/java
│
├── entities
│   └── Classes de entidade
│
├── dao
│   └── Camada de acesso ao banco de dados
│
├── resources
│   └── Endpoints REST
│
└── infra
    └── Configuração de conexão com banco
```

---

## Banco de Dados

A aplicação utiliza Oracle Database hospedado pela FIAP.

Tabelas principais:

* T_USUARIO
* T_AGENCIA
* T_MISSAO
* T_FAVORITO

---

## Configuração de Ambiente

Por questões de segurança, as credenciais do banco não são armazenadas no código-fonte nem no repositório GitHub.

A aplicação utiliza variáveis de ambiente para configuração da conexão.

Exemplo:

```text
DB_URL=jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL
DB_USER=SEU_USUARIO
DB_PASSWORD=SUA_SENHA
```

As credenciais devem ser configuradas localmente ou no ambiente de deploy.

---

## Executando Localmente

### Clonar o repositório

```bash
git clone <https://github.com/gi13090/Projeto_Espacial.git>
```

### Acessar a pasta do projeto

```bash
cd CosmoHub
```

### Executar a aplicação

```bash
mvn quarkus:dev
```

A API ficará disponível em:

```text
http://localhost:8080
```

---

## Documentação Swagger

Após iniciar a aplicação:

```text
http://localhost:8080/q/swagger-ui
```

---

## Deploy

A API encontra-se publicada na plataforma Render.

URL de produção:

https://projeto-espacial-6.onrender.com

---

## Entidades da Aplicação

### Usuário

Responsável pelo acesso à plataforma.

### Agência

Representa organizações espaciais responsáveis pelas missões.

### Missão

Representa missões espaciais cadastradas no sistema.

### Favorito

Permite que usuários favoritem missões.

---

## Endpoints

### Usuários

| Método | Endpoint       |
| ------ | -------------- |
| GET    | /usuarios      |
| GET    | /usuarios/{id} |
| POST   | /usuarios      |
| PUT    | /usuarios/{id} |
| DELETE | /usuarios/{id} |

### Agências

| Método | Endpoint       |
| ------ | -------------- |
| GET    | /agencias      |
| GET    | /agencias/{id} |
| POST   | /agencias      |
| PUT    | /agencias/{id} |
| DELETE | /agencias/{id} |

### Missões

| Método | Endpoint      |
| ------ | ------------- |
| GET    | /missoes      |
| GET    | /missoes/{id} |
| POST   | /missoes      |
| PUT    | /missoes/{id} |
| DELETE | /missoes/{id} |

### Favoritos

| Método | Endpoint        |
| ------ | --------------- |
| GET    | /favoritos      |
| GET    | /favoritos/{id} |
| POST   | /favoritos      |
| DELETE | /favoritos/{id} |

---

## Exemplos de JSON

### Usuário

```json
{
  "id": 1,
  "nome": "Gabriel",
  "email": "gabriel@email.com",
  "senha": "123456"
}
```

### Agência

```json
{
  "id": 1,
  "nome": "NASA",
  "pais": "Estados Unidos",
  "anoFundacao": 1958,
  "site": "https://www.nasa.gov"
}
```

### Missão

```json
{
  "id": 1,
  "nome": "Apollo 11",
  "descricao": "Primeira missão lunar",
  "dataLancamento": "1969-07-16",
  "duracaoDias": 8,
  "status": "Concluída",
  "destino": "Lua",
  "agenciaId": 1
}
```

### Favorito

```json
{
  "id": 1,
  "usuarioId": 1,
  "missaoId": 1
}
```

---

## Equipe

Projeto desenvolvido para a Global Solution FIAP 2026.

---

## Links

GitHub: (https://github.com/gi13090/Projeto_Espacial)

Deploy: https://projeto-espacial-6.onrender.com

Vídeo de Demonstração: (adicionar link)
