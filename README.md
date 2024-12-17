# Api-repository

# Visão Geral da Arquitetura

Este projeto é uma aplicação baseada em microserviços que organiza e gerencia usuários, conteúdos, cursos e categorias. A arquitetura é composta por três microserviços principais que se comunicam via APIs RESTful. Um API Gateway centraliza o controle de acesso. 


## Microserviços:

- Microserviço de Usuários: Gerencia informações de usuários. Cuja base é de nome, email, curso e senha.

- Microserviço de Conteúdos: Gerencia e organiza os conteúdos submetidos. Cuja base é de titulo, tipo, status, tags, o id do autor e o id da categoria.

- Microserviço de Categorias: Trata da organização por cursos e categorias. Cuja base é de disciplina, descrição e curso.

## Mecanismo de Persistência:

- PostgreSQL: Banco de dados relacional utilizado em todos os microserviços.

## Como executar o projeto em máquina local:

### Clonando o repositório 
- Acesse o projeto do github em https://github.com/GustavoKophal/api-repository e utilize o comando `git clone <URL_DO_REPOSITORIO>` em uma pasta do seu navegador 


### Criação de Bancos de Dados e Usuários:

Para cada microserviço, crie um banco de dados e um usuário correspondente no PostgreSQL:

- Banco de Dados para o User Service:

`CREATE DATABASE usuarios_db;
CREATE USER usuarios_user WITH PASSWORD '1234';
GRANT ALL PRIVILEGES ON DATABASE usuarios_db TO usuarios_user;
GRANT ALL ON SCHEMA public TO usuarios_user;
GRANT CREATE, CONNECT, TEMPORARY ON DATABASE usuarios_db TO usuarios_user;
GRANT ALL ON ALL TABLES IN SCHEMA public TO usuarios_user;
GRANT ALL ON ALL SEQUENCES IN SCHEMA public TO usuarios_user;`

- Banco de Dados para o Content Service:

`CREATE DATABASE conteudos_db;
CREATE USER conteudos_user WITH PASSWORD '1234';
GRANT ALL PRIVILEGES ON DATABASE conteudos_db TO conteudos_user;
GRANT ALL ON SCHEMA public TO conteudos_user;
GRANT CREATE, CONNECT, TEMPORARY ON DATABASE conteudos_db TO conteudos_user;
GRANT ALL ON ALL TABLES IN SCHEMA public TO conteudos_user;
GRANT ALL ON ALL SEQUENCES IN SCHEMA public TO conteudos_user;`

- Banco de Dados para o Category Service:

`CREATE DATABASE categorias_db;
CREATE USER categorias_user WITH PASSWORD '1234';
GRANT ALL PRIVILEGES ON DATABASE categorias_db TO categorias_user;
GRANT ALL ON SCHEMA public TO categorias_user;
GRANT CREATE, CONNECT, TEMPORARY ON DATABASE categorias_db TO categorias_user;
GRANT ALL ON ALL TABLES IN SCHEMA public TO categorias_user;
GRANT ALL ON ALL SEQUENCES IN SCHEMA public TO categorias_user;`

### Rodando projeto
- Abra a pasta do projeto em uma IDE de sua escolha;

- Rode os arquivos aplication das pastas de `usuarios`, `conteudos`,`categorias` e `gateway`.

### Testando a aplicação
Os microserviços rodam nas seguintes portas por padrão:

#### Usuarios: 

- GET: http://localhost:8081/usuarios

- POST: http://localhost:8081/usuarios

Exemplo de uso: 

`{
    "email": "joao.silva@email.com",
    "nome": "João Silva",
    "senha": "senha1234",
    "curso": "Engenharia de Software"
}`

- PUT: http://localhost:8081/usuarios/id

Exemplo de uso: 

`{
    "email": "joao.silva@email.com",
    "nome": "João Silva",
    "senha": "senhaNOVA",
    "curso": "Engenharia de Software"
}`

- DELETE: http://localhost:8081/usuarios/id



#### Categorias: 

- GET: http://localhost:8082/Categorias
- POST: http://localhost:8082/Categorias

Exemplo de uso:

`{
    "disciplina": "Fisica",
    "descricao": "Categoria relacionada a fisica básica",
    "curso": "Engenharia"
}`

- PUT: http://localhost:8082/Categorias/id

Exemplo de uso:

`{
    "disciplina": "Fisica 2",
    "descricao": "Categoria relacionada a fisica AVANÇADA",
    "curso": "Engenharia"
}`

- DELETE: http://localhost:8082/Categorias/id

#### Conteudos: 

- GET: http://localhost:8083/Conteudos

- POST: http://localhost:8083/Conteudos

Exemplo de uso: 

`{
    "titulo": "Introdução à Fisica",
    "tipo": "texto",
    "status": "rascunho",
    "tags": "programação, básico",
    "autorId": 1,
    "categoriaId": 1
}`
- PUT: http://localhost:8083/Conteudos/id

Exemplo de uso: 

`{
    "titulo": "Introdução à Fisica AVANÇADA",
    "tipo": "texto",
    "status": "rascunho",
    "tags": "programação, básico",
    "autorId": 1,
    "categoriaId": 1
}`

- DELETE: http://localhost:8083/Conteudos/id

#### API Gateway: http://localhost:8080/microserviçoQueDesejaTestar 

- Utilize qualquer rota dos micriserviços no porta 8080 para testar o gateway

### O que foi feito

- CRUD para os 3 microsserviços;
- Autenticação e Validações;
- Swagger para os 3 microsserviços;
- Gateway para os 3 microsserviços.

### O que falta

- Swagger para o gateway.



