# 🌍 Global Solution 2025 - O Futuro do Trabalho

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.4-brightgreen?style=for-the-badge&logo=spring)
![H2 Database](https://img.shields.io/badge/H2-Database-blue?style=for-the-badge)
![JWT](https://img.shields.io/badge/JWT-Authentication-red?style=for-the-badge)
![Maven](https://img.shields.io/badge/Maven-3.6+-C71A36?style=for-the-badge&logo=apache-maven)
![License](https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge)

## 📋 Índice

- [Sobre o Projeto](#-sobre-o-projeto)
- [Conexão com os ODS](#-conexão-com-os-objetivos-de-desenvolvimento-sustentável-ods)
- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Arquitetura do Projeto](#-arquitetura-do-projeto)
- [Funcionalidades Principais](#-funcionalidades-principais)
- [Instalação e Configuração](#-instalação-e-configuração)
- [Como Executar](#-como-executar)
- [Como Testar](#-como-testar)
- [Endpoints da API](#-endpoints-da-api)
- [Exemplos de Requisições](#-exemplos-de-requisições)
- [Banco de Dados](#-banco-de-dados)
- [Sistema de Testes](#-sistema-de-testes)
- [Diferenciais do Projeto](#-diferenciais-do-projeto)
- [Integrantes](#-integrantes)

---

## 📚 Sobre o Projeto

### 🎯 O Problema

O mercado de trabalho está passando por **transformações aceleradas** devido a:

- 🤖 **Automação e IA** substituindo funções tradicionais
- 📊 **Novas competências** sendo exigidas constantemente (análise de dados, programação, soft skills)
- 📉 **Desigualdade** no acesso à educação e requalificação profissional
- 🔄 **Necessidade de aprendizado contínuo** para acompanhar mudanças tecnológicas
- 💼 **Risco de desemprego** para profissionais sem requalificação

### 💡 Nossa Solução

**Global Solution API** é uma plataforma **completa de Upskilling/Reskilling** voltada para o futuro do trabalho (2030+), desenvolvida como parte da Global Solution 2025 da FIAP.

A plataforma oferece:

- 👥 **Cadastro completo de usuários** (profissionais/alunos) com validações robustas
- 📖 **Trilhas de aprendizagem** focadas em competências do futuro:
   - 🤖 **Tecnológicas:** IA, Machine Learning, Análise de Dados, Big Data, Cloud Computing, DevOps
   - 💻 **Desenvolvimento:** Web Full Stack, Programação, APIs, Arquitetura de Software
   - 🧠 **Soft Skills:** Liderança, Gestão de Equipes, Comunicação, Colaboração, Agilidade
   - 📈 **Negócios:** Product Management, Transformação Digital, Inovação, Estratégia
- 📝 **Sistema de matrículas** para acompanhamento do progresso profissional
- 🔐 **API REST segura** com autenticação JWT
- 🌐 **Interface web completa** para gestão (Thymeleaf + Bootstrap)
- 📚 **Documentação interativa** com Swagger/OpenAPI

### 🎓 Por que isso importa?

Esta solução aborda diretamente o desafio de **preparar profissionais para as carreiras de 2030+**, oferecendo:

- ✅ **Acesso democrático** à educação de qualidade
- ✅ **Requalificação profissional** para evitar desemprego
- ✅ **Desenvolvimento de competências** alinhadas ao mercado
- ✅ **Redução de desigualdades** através da educação
- ✅ **Aprendizado contínuo** e personalizado

---

## 🌱 Conexão com os Objetivos de Desenvolvimento Sustentável (ODS)

Este projeto está **diretamente alinhado** com os seguintes ODS da ONU:

### 🎓 ODS 4 - Educação de Qualidade
Promove **educação inclusiva, equitativa e de qualidade**, oferecendo:
- Trilhas de aprendizagem estruturadas e acessíveis
- Conteúdo atualizado com competências do futuro
- Aprendizado ao longo da vida para profissionais de todos os níveis

### 💼 ODS 8 - Trabalho Decente e Crescimento Econômico
Facilita a **requalificação profissional** para:
- Preparar trabalhadores para empregos do futuro
- Reduzir o desemprego causado pela automação
- Promover crescimento econômico inclusivo e sustentável

### 🏭 ODS 9 - Indústria, Inovação e Infraestrutura
Desenvolve **competências tecnológicas** essenciais para:
- Transformação digital nas empresas
- Inovação em produtos e serviços
- Infraestrutura resiliente e sustentável

### ⚖️ ODS 10 - Redução das Desigualdades
Democratiza o acesso à **educação e requalificação**, promovendo:
- Inclusão social e econômica
- Redução de desigualdades no mercado de trabalho
- Oportunidades iguais para todos os profissionais

---

## 💻 Tecnologias Utilizadas

### Backend
- ☕ **Java 17** - Linguagem de programação
- 🌱 **Spring Boot 3.3.4** - Framework principal
- 🗂️ **Spring Data JPA** - Persistência de dados com ORM
- 🔐 **Spring Security** - Segurança e controle de acesso
- 🔑 **JWT (JSON Web Token)** - Autenticação stateless

### Frontend
- 🎨 **Thymeleaf** - Template engine para interfaces web
- 🎨 **Bootstrap 5** - Framework CSS para design responsivo
- 🎨 **Bootstrap Icons** - Biblioteca de ícones

### Banco de Dados
- 🗄️ **H2 Database** - Banco relacional em memória
- 🔄 **Flyway** - Controle de versionamento e migrations do banco

### Documentação
- 📚 **Springdoc OpenAPI 2.6.0** - Documentação automática da API
- 📖 **Swagger UI** - Interface interativa para testar endpoints

### Ferramentas de Desenvolvimento
- 🏗️ **Maven 3.6+** - Gerenciamento de dependências e build
- 🔧 **Lombok** - Redução de boilerplate code
- ✅ **Bean Validation (Jakarta)** - Validação de dados
- 🧪 **JUnit 5** - Framework de testes unitários
- 🎭 **Mockito** - Biblioteca para mocks em testes

---

## 🏗️ Arquitetura do Projeto

O projeto segue a **arquitetura em camadas (Layered Architecture)** com separação clara de responsabilidades:
```
┌─────────────────────────────────────────────────────────────┐
│               CAMADA DE APRESENTAÇÃO                        │
│          (Controllers REST + View Controllers)              │
│                                                              │
│  ┌──────────────────┐      ┌──────────────────┐            │
│  │  REST API        │      │  Thymeleaf Views │            │
│  │  /api/*          │      │  /web/*          │            │
│  └──────────────────┘      └──────────────────┘            │
├─────────────────────────────────────────────────────────────┤
│                  CAMADA DE DTOs                             │
│         (Request/Response + Mappers + Validações)           │
│                                                              │
│  Request DTOs ──▶ Validações ──▶ Mappers ──▶ Response DTOs │
├─────────────────────────────────────────────────────────────┤
│                  CAMADA DE SERVIÇOS                         │
│           (Regras de Negócio + Validações)                  │
│                                                              │
│  UsuarioService  │  TrilhaService  │  MatriculaService      │
├─────────────────────────────────────────────────────────────┤
│                CAMADA DE REPOSITÓRIOS                       │
│              (Acesso a Dados - Spring Data JPA)             │
│                                                              │
│  UsuarioRepo  │  TrilhaRepo  │  MatriculaRepo               │
├─────────────────────────────────────────────────────────────┤
│                    CAMADA DE MODELO                         │
│                  (Entidades JPA + Enums)                    │
│                                                              │
│  Usuario  │  Trilha  │  Matricula                           │
├─────────────────────────────────────────────────────────────┤
│                  CAMADA DE SEGURANÇA                        │
│            (JWT Service + Auth Filter + Config)             │
├─────────────────────────────────────────────────────────────┤
│               TRATAMENTO DE EXCEÇÕES                        │
│          (GlobalExceptionHandler + Custom Exceptions)       │
├─────────────────────────────────────────────────────────────┤
│                  CAMADA DE CONFIGURAÇÃO                     │
│          (OpenAPI + Security + Flyway)                      │
├─────────────────────────────────────────────────────────────┤
│                    BANCO DE DADOS                           │
│                      H2 (in-memory)                         │
└─────────────────────────────────────────────────────────────┘
```

### 📦 Estrutura de Pacotes
```
src/main/java/com/fiap/globalsolution/
├── 📂 api/config/                    # Configurações globais
│   ├── OpenApiConfig.java            # Config Swagger/OpenAPI
│   └── SecurityConfig.java           # Config Spring Security + JWT
│
├── 📂 controller/                    # Controllers REST e Web
│   ├── AuthController.java           # Autenticação JWT
│   ├── UsuarioController.java        # CRUD REST Usuários
│   ├── TrilhaController.java         # CRUD REST Trilhas
│   ├── MatriculaController.java      # CRUD REST Matrículas
│   ├── UsuarioViewController.java    # CRUD Web Usuários
│   ├── TrilhaViewController.java     # CRUD Web Trilhas
│   └── MatriculaViewController.java  # CRUD Web Matrículas
│
├── 📂 dto/                           # Data Transfer Objects
│   ├── UsuarioRequest.java           # DTO entrada usuário
│   ├── UsuarioResponse.java          # DTO saída usuário
│   ├── UsuarioMapper.java            # Mapper usuário
│   ├── TrilhaRequest.java            # DTO entrada trilha
│   ├── TrilhaResponse.java           # DTO saída trilha
│   ├── TrilhaMapper.java             # Mapper trilha
│   ├── MatriculaRequest.java         # DTO entrada matrícula
│   ├── MatriculaResponse.java        # DTO saída matrícula
│   └── MatriculaMapper.java          # Mapper matrícula
│
├── 📂 exception/                     # Exceções customizadas + Handler
│   ├── GlobalExceptionHandler.java   # @RestControllerAdvice global
│   ├── UsuarioNaoEncontradoException.java
│   ├── TrilhaNaoEncontradaException.java
│   ├── MatriculaNaoEncontradaException.java
│   └── DuplicateEntityException.java
│
├── 📂 model/                         # Entidades JPA
│   ├── Usuario.java                  # @Entity com validações
│   ├── Trilha.java                   # @Entity com validações
│   └── Matricula.java                # @Entity com relacionamentos
│
├── 📂 repository/                    # Repositórios Spring Data JPA
│   ├── UsuarioRepository.java        # Queries customizadas
│   ├── TrilhaRepository.java         # Queries customizadas
│   └── MatriculaRepository.java      # Queries customizadas
│
├── 📂 security/                      # Segurança JWT
│   ├── JwtService.java               # Geração/validação tokens
│   └── JwtAuthFilter.java            # Filtro de autenticação
│
├── 📂 service/                       # Regras de negócio
│   ├── UsuarioService.java           # Lógica de usuários
│   ├── TrilhaService.java            # Lógica de trilhas
│   └── MatriculaService.java         # Lógica de matrículas
│
└── GlobalSolutionApiApplication.java # Entry point Spring Boot

src/main/resources/
├── 📂 db/migration/                  # Scripts Flyway
│   └── V1__init.sql                  # Schema + seeds
│
├── 📂 templates/                     # Templates Thymeleaf
│   ├── layout/base.html              # Layout base
│   ├── usuarios/                     # Views usuários
│   ├── trilhas/                      # Views trilhas
│   └── matriculas/                   # Views matrículas
│
└── application.yml                   # Configuração única com H2

src/test/java/                        # Testes unitários
├── SuiteDeTestesGeral.java           # Suite principal
├── model/                            # Testes de entidades
├── dto/                              # Testes de DTOs + validações
├── service/                          # Testes de serviços
├── controller/                       # Testes de controllers
├── security/                         # Testes de JWT
└── exception/                        # Testes de exceções
```

---

## 🚀 Funcionalidades Principais

### 👥 Gestão de Usuários
- ✅ Cadastro completo com validações robustas
- ✅ Validação de email único no sistema
- ✅ Validação de data de cadastro (não permite futuras)
- ✅ Campos: nome, email, área de atuação, nível de carreira
- ✅ CRUD completo (Create, Read, Update, Delete)
- ✅ Busca por ID, email e área de atuação
- ✅ Histórico de matrículas por usuário

### 📖 Gestão de Trilhas de Aprendizagem
- ✅ Cadastro de trilhas com competências do futuro
- ✅ Níveis: INICIANTE, INTERMEDIARIO, AVANCADO
- ✅ Carga horária entre 1-1000 horas
- ✅ Foco principal (IA, Dados, DevOps, Soft Skills)
- ✅ CRUD completo
- ✅ Filtros por nível e foco principal
- ✅ Descrição detalhada de cada trilha
- ✅ Validações de duplicatas

### 📝 Sistema de Matrículas
- ✅ Inscrição de usuários em trilhas
- ✅ Status: EM_ANDAMENTO, CONCLUIDA, CANCELADA
- ✅ Validação: não permite matrícula duplicada na mesma trilha
- ✅ Data de inscrição (não permite futuras)
- ✅ Relacionamentos: Usuario ←→ Matricula ←→ Trilha
- ✅ CRUD completo
- ✅ Filtros por usuário, trilha e status
- ✅ Histórico completo de matrículas

### 🔐 Autenticação e Segurança
- ✅ JWT (JSON Web Token) para API REST
- ✅ Usuários: `admin/admin` (ROLE_ADMIN) e `user/user` (ROLE_USER)
- ✅ Token expira em 1 hora
- ✅ Endpoints protegidos na API REST
- ✅ Interface web liberada (sem autenticação)
- ✅ Spring Security configurado

### 🌐 Interfaces Múltiplas
- ✅ **REST API** para integração com sistemas externos
- ✅ **Interface Web** completa com Thymeleaf
- ✅ **Swagger UI** para documentação interativa
- ✅ **Collection Insomnia** pronta para testes

### ✅ Validações Completas
- ✅ **Bean Validation** em todos os DTOs
- ✅ `@NotBlank`, `@Email`, `@Size`, `@Min`, `@Max`
- ✅ `@Pattern` para validações customizadas
- ✅ `@PastOrPresent` para datas
- ✅ Mensagens de erro personalizadas
- ✅ Validações de duplicatas no Service
- ✅ **@RestControllerAdvice** para tratamento global de exceções

### 📊 Controle e Auditoria
- ✅ Histórico completo de operações
- ✅ Rastreabilidade de matrículas
- ✅ Timestamps em todas as respostas de erro
- ✅ Tratamento de exceções centralizado
- ✅ Respostas padronizadas de erro

---

## ⚙️ Instalação e Configuração

### 📋 Pré-requisitos

- ☕ **Java 17** ou superior ([Download JDK](https://www.oracle.com/java/technologies/downloads/))
- 🏗️ **Maven 3.6+** ([Download Maven](https://maven.apache.org/download.cgi))
- 💻 **IDE recomendada:** IntelliJ IDEA, Eclipse ou VS Code
- 📬 **Insomnia ou Postman** (opcional, para testar API)

### 📥 1. Clone o Repositório
```bash
git clone https://github.com/seu-usuario/global-solution-api.git
cd global-solution-api
```

### 🔨 2. Compile o Projeto
```bash
mvn clean install
```

**Saída esperada:**
```
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  15.234 s
```

---

## 🎯 Como Executar

### 🚀 Opção 1: Via Maven (Recomendado)
```bash
mvn spring-boot:run
```

### 🚀 Opção 2: Via JAR
```bash
# Compilar
mvn clean package

# Executar
java -jar target/global-solution-api-0.0.1-SNAPSHOT.jar
```

### ✅ Verificar se está rodando

Acesse no navegador:
```
http://localhost:8080
```

Você verá uma mensagem no console:
```
==============================================================================
  🌍 GLOBAL SOLUTION API - O FUTURO DO TRABALHO
  📚 Plataforma de Upskilling/Reskilling para 2030+
==============================================================================
  📊 H2 Console:    http://localhost:8080/h2-console
  📚 Swagger UI:    http://localhost:8080/swagger-ui.html
  👥 Usuários Web:  http://localhost:8080/web/usuarios
  📖 Trilhas Web:   http://localhost:8080/web/trilhas
  📝 Matrículas:    http://localhost:8080/web/matriculas
==============================================================================
  ✅ Aplicação iniciada com sucesso!
==============================================================================
```

---

## 🧪 Como Testar

### 1️⃣ Via Swagger UI (Mais Fácil - Recomendado)

1. **Acesse:** http://localhost:8080/swagger-ui.html
2. **Faça login:**
   - Clique em **Autenticação → POST /auth/login**
   - Clique em "Try it out"
   - Use: `{"username": "admin", "password": "admin"}`
   - Clique em "Execute"
   - **Copie o token** retornado
3. **Autorize:**
   - Clique no botão **"Authorize"** (cadeado verde no topo)
   - Cole o token no campo "Value"
   - Clique em "Authorize"
4. **Teste os endpoints!** ✅

### 2️⃣ Via Insomnia (Collection Pronta)

1. **Abra o Insomnia**
2. **Importe a collection:**
   - Clique em **Application → Preferences → Data**
   - Clique em **Import Data → From File**
   - Selecione: `Insomnia_GlobalSolution_Collection.json`
3. **Configure o token:**
   - Pasta **Autenticação → Login**
   - Execute e copie o token
   - Cole na variável de ambiente `token`
4. **Teste todos os endpoints!** ✅

### 3️⃣ Via Postman

**Exemplo de requisição:**

**Login:**
```http
POST http://localhost:8080/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "admin"
}
```

**Usar o token:**
```http
GET http://localhost:8080/api/usuarios
Authorization: Bearer SEU_TOKEN_AQUI
```

### 4️⃣ Via Interface Web (Sem Autenticação)

Basta acessar no navegador:
- **Usuários:** http://localhost:8080/web/usuarios
- **Trilhas:** http://localhost:8080/web/trilhas
- **Matrículas:** http://localhost:8080/web/matriculas

### 5️⃣ Via H2 Console (Banco de Dados)

1. **Acesse:** http://localhost:8080/h2-console
2. **Credenciais:**
   - **JDBC URL:** `jdbc:h2:mem:globalsolutiondb`
   - **Username:** `gs2`
   - **Password:** `2025`
3. **Execute queries SQL:**
```sql
-- Ver todos os usuários
SELECT * FROM usuarios;

-- Ver todas as trilhas
SELECT * FROM trilhas;

-- Ver matrículas com detalhes
SELECT 
    u.nome AS usuario,
    t.nome AS trilha,
    m.data_inscricao,
    m.status
FROM matriculas m
JOIN usuarios u ON m.usuario_id = u.id_usuario
JOIN trilhas t ON m.trilha_id = t.id_trilha;
```

---

## 📡 Endpoints da API

### 🔐 Autenticação

| Método | Endpoint | Descrição | Auth |
|--------|----------|-----------|------|
| POST | `/auth/login` | Autentica e retorna token JWT | ❌ |

### 👥 Usuários

| Método | Endpoint | Descrição | Auth |
|--------|----------|-----------|------|
| GET | `/api/usuarios` | Lista todos os usuários | ✅ |
| GET | `/api/usuarios/{id}` | Busca usuário por ID | ✅ |
| GET | `/api/usuarios/email/{email}` | Busca por email | ✅ |
| GET | `/api/usuarios/area/{area}` | Busca por área de atuação | ✅ |
| POST | `/api/usuarios` | Cria novo usuário | ✅ |
| PUT | `/api/usuarios/{id}` | Atualiza usuário | ✅ |
| DELETE | `/api/usuarios/{id}` | Remove usuário | ✅ |

### 📖 Trilhas

| Método | Endpoint | Descrição | Auth |
|--------|----------|-----------|------|
| GET | `/api/trilhas` | Lista todas as trilhas | ✅ |
| GET | `/api/trilhas/{id}` | Busca trilha por ID | ✅ |
| GET | `/api/trilhas/nivel/{nivel}` | Busca por nível | ✅ |
| GET | `/api/trilhas/foco/{foco}` | Busca por foco principal | ✅ |
| POST | `/api/trilhas` | Cria nova trilha | ✅ |
| PUT | `/api/trilhas/{id}` | Atualiza trilha | ✅ |
| DELETE | `/api/trilhas/{id}` | Remove trilha | ✅ |

### 📝 Matrículas

| Método | Endpoint | Descrição | Auth |
|--------|----------|-----------|------|
| GET | `/api/matriculas` | Lista todas as matrículas | ✅ |
| GET | `/api/matriculas/{id}` | Busca matrícula por ID | ✅ |
| GET | `/api/matriculas/usuario/{usuarioId}` | Busca por usuário | ✅ |
| GET | `/api/matriculas/trilha/{trilhaId}` | Busca por trilha | ✅ |
| GET | `/api/matriculas/status/{status}` | Busca por status | ✅ |
| POST | `/api/matriculas` | Cria nova matrícula | ✅ |
| PUT | `/api/matriculas/{id}` | Atualiza matrícula | ✅ |
| DELETE | `/api/matriculas/{id}` | Remove matrícula | ✅ |

---

## 📝 Exemplos de Requisições

### 🔐 1. Login (Obter Token JWT)

**Request:**
```http
POST /auth/login HTTP/1.1
Host: localhost:8080
Content-Type: application/json

{
  "username": "admin",
  "password": "admin"
}
```

**Response (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "expiresIn": 3600000
}
```

---

### 👥 2. Criar Novo Usuário

**Request:**
```http
POST /api/usuarios HTTP/1.1
Host: localhost:8080
Authorization: Bearer SEU_TOKEN_AQUI
Content-Type: application/json

{
  "nome": "João Silva",
  "email": "joao.silva@email.com",
  "areaAtuacao": "Engenharia de Software",
  "nivelCarreira": "PLENO",
  "dataCadastro": "2025-11-12"
}
```

**Response (201 Created):**
```json
{
  "id": 6,
  "nome": "João Silva",
  "email": "joao.silva@email.com",
  "areaAtuacao": "Engenharia de Software",
  "nivelCarreira": "PLENO",
  "dataCadastro": "2025-11-12"
}
```

---

### 📖 3. Criar Nova Trilha

**Request:**
```http
POST /api/trilhas HTTP/1.1
Host: localhost:8080
Authorization: Bearer SEU_TOKEN_AQUI
Content-Type: application/json

{
  "nome": "Cybersecurity Fundamentals",
  "descricao": "Aprenda os fundamentos de segurança cibernética.",
  "nivel": "INTERMEDIARIO",
  "cargaHoraria": 60,
  "focoPrincipal": "Segurança da Informação"
}
```

**Response (201 Created):**
```json
{
  "id": 11,
  "nome": "Cybersecurity Fundamentals",
  "descricao": "Aprenda os fundamentos de segurança cibernética.",
  "nivel": "INTERMEDIARIO",
  "cargaHoraria": 60,
  "focoPrincipal": "Segurança da Informação"
}
```

---

### 📝 4. Criar Nova Matrícula

**Request:**
```http
POST /api/matriculas HTTP/1.1
Host: localhost:8080
Authorization: Bearer SEU_TOKEN_AQUI
Content-Type: application/json

{
  "usuarioId": 2,
  "trilhaId": 5,
  "dataInscricao": "2025-11-12",
  "status": "EM_ANDAMENTO"
}
```

**Response (201 Created):**
```json
{
  "id": 11,
  "usuario": {
    "id": 2,
    "nome": "Carlos Mendes",
    "email": "carlos.mendes@email.com",
    "areaAtuacao": "Análise de Dados",
    "nivelCarreira": "JUNIOR",
    "dataCadastro": "2024-02-20"
  },
  "trilha": {
    "id": 5,
    "nome": "Desenvolvimento Web Full Stack",
    "descricao": "Construa aplicações web completas...",
    "nivel": "INTERMEDIARIO",
    "cargaHoraria": 100,
    "focoPrincipal": "Desenvolvimento Web"
  },
  "dataInscricao": "2025-11-12",
  "status": "EM_ANDAMENTO"
}
```

---

### ⚠️ 5. Erro: Validação de Campos

**Request:**
```http
POST /api/usuarios HTTP/1.1
Host: localhost:8080
Authorization: Bearer SEU_TOKEN_AQUI
Content-Type: application/json

{
  "nome": "",
  "email": "email-invalido",
  "dataCadastro": "2026-01-01"
}
```

**Response (400 Bad Request):**
```json
{
  "timestamp": "2025-11-13T17:00:00",
  "status": 400,
  "error": "Validation Error",
  "message": "Erro de validação nos campos",
  "errors": {
    "nome": "Nome é obrigatório",
    "email": "Email deve ser válido",
    "dataCadastro": "Data de cadastro não pode ser futura"
  },
  "path": "/api/usuarios"
}
```

---

### ⚠️ 6. Erro: Matrícula Duplicada

**Request:**
```http
POST /api/matriculas HTTP/1.1
Host: localhost:8080
Authorization: Bearer SEU_TOKEN_AQUI
Content-Type: application/json

{
  "usuarioId": 1,
  "trilhaId": 1,
  "dataInscricao": "2025-11-13",
  "status": "EM_ANDAMENTO"
}
```

**Response (409 Conflict):**
```json
{
  "timestamp": "2025-11-13T16:45:30",
  "status": 409,
  "error": "Conflict",
  "message": "Usuário Ana Silva já está matriculado na trilha Fundamentos de IA com status EM_ANDAMENTO",
  "path": "/api/matriculas"
}
```

---

## 🗄️ Banco de Dados

### 📊 Modelo de Dados

O projeto utiliza **3 tabelas principais** com relacionamentos:
```sql
┌─────────────────┐
│    USUARIOS     │
├─────────────────┤
│ id_usuario (PK) │
│ nome            │
│ email (UNIQUE)  │
│ area_atuacao    │
│ nivel_carreira  │
│ data_cadastro   │
└────────┬────────┘
         │ 1
         │
         │ N
┌────────┴────────┐
│   MATRICULAS    │
├─────────────────┤
│ id_matricula(PK)│
│ usuario_id (FK) │
│ trilha_id (FK)  │
│ data_inscricao  │
│ status          │
└────────┬────────┘
         │ N
         │
         │ 1
┌────────┴────────┐
│     TRILHAS     │
├─────────────────┤
│ id_trilha (PK)  │
│ nome            │
│ descricao       │
│ nivel           │
│ carga_horaria   │
│ foco_principal  │
└─────────────────┘
```

### 🗃️ Script SQL Completo
```sql
-- TABELA USUARIOS
CREATE TABLE usuarios (
    id_usuario BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    area_atuacao VARCHAR(100),
    nivel_carreira VARCHAR(50),
    data_cadastro DATE NOT NULL
);

-- TABELA TRILHAS
CREATE TABLE trilhas (
    id_trilha BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    descricao TEXT,
    nivel VARCHAR(50) NOT NULL CHECK (nivel IN ('INICIANTE', 'INTERMEDIARIO', 'AVANCADO')),
    carga_horaria INT NOT NULL CHECK (carga_horaria > 0),
    foco_principal VARCHAR(100)
);

-- TABELA MATRICULAS
CREATE TABLE matriculas (
    id_matricula BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    trilha_id BIGINT NOT NULL,
    data_inscricao DATE NOT NULL,
    status VARCHAR(50) NOT NULL CHECK (status IN ('EM_ANDAMENTO', 'CONCLUIDA', 'CANCELADA')),
    CONSTRAINT fk_matricula_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    CONSTRAINT fk_matricula_trilha FOREIGN KEY (trilha_id) REFERENCES trilhas(id_trilha) ON DELETE CASCADE
);

-- ÍNDICES PARA PERFORMANCE
CREATE INDEX idx_usuarios_email ON usuarios(email);
CREATE INDEX idx_usuarios_area ON usuarios(area_atuacao);
CREATE INDEX idx_trilhas_nivel ON trilhas(nivel);
CREATE INDEX idx_trilhas_foco ON trilhas(foco_principal);
CREATE INDEX idx_matriculas_usuario ON matriculas(usuario_id);
CREATE INDEX idx_matriculas_trilha ON matriculas(trilha_id);
CREATE INDEX idx_matriculas_status ON matriculas(status);
```

### 📦 Dados Iniciais (Seeds)

O banco é **automaticamente populado** com dados de exemplo via Flyway:

- **5 Usuários** (diferentes áreas e níveis)
- **10 Trilhas** (IA, Dados, DevOps, Soft Skills, Negócios)
- **10 Matrículas** (com status variados)

### 🔌 Configuração do Banco H2
```yaml
# application.yml
spring:
  application:
    name: global-solution-api
  
  datasource:
    url: jdbc:h2:mem:globalsolutiondb;DB_CLOSE_DELAY=-1;MODE=PostgreSQL
    username: gs2
    password: "2025"
    driver-class-name: org.h2.Driver
  
  h2:
    console:
      enabled: true
      path: /h2-console
  
  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: false
    properties:
      hibernate:
        format_sql: true
  
  flyway:
    enabled: true
    baseline-on-migrate: true
    locations: classpath:db/migration
```

---

## 🧪 Sistema de Testes

### 📊 Cobertura Completa: 19 Classes de Teste
```
🧪 Suite de Testes
├── 🏗️ model/ (3 testes)
│   ├── ✅ UsuarioTest
│   ├── ✅ TrilhaTest
│   └── ✅ MatriculaTest
│
├── 📦 dto/ (6 testes)
│   ├── ✅ UsuarioMapperTest
│   ├── ✅ TrilhaMapperTest
│   ├── ✅ MatriculaMapperTest
│   ├── ✅ UsuarioRequestTest
│   ├── ✅ TrilhaRequestTest
│   └── ✅ MatriculaRequestTest
│
├── 🛠️ service/ (3 testes)
│   ├── ✅ UsuarioServiceTest
│   ├── ✅ TrilhaServiceTest
│   └── ✅ MatriculaServiceTest
│
├── 🎮 controller/ (4 testes)
│   ├── ✅ UsuarioControllerTest
│   ├── ✅ TrilhaControllerTest
│   ├── ✅ MatriculaControllerTest
│   └── ✅ AuthControllerTest
│
├── 🔐 security/ (2 testes)
│   ├── ✅ JwtServiceTest
│   └── ✅ JwtAuthFilterTest
│
└── ⚠️ exception/ (1 teste)
    └── ✅ ExceptionsTest

📊 TOTAL: 19 classes | 100+ testes unitários
```

### 🚀 Executar Testes
```bash
# Todos os testes
mvn test

# Suite completa
mvn test -Dtest=SuiteDeTestesGeral

# Teste específico
mvn test -Dtest=UsuarioServiceTest

# Com relatório de cobertura
mvn clean test jacoco:report
```

---

## ✨ Diferenciais do Projeto

### 🏆 Requisitos Obrigatórios (100%)

- ✅ **2 CRUDs completos** → Implementamos **3 CRUDs** (Usuário, Trilha, Matrícula)
- ✅ **Arquitetura em camadas** → Controller → Service → Repository
- ✅ **Bean Validation** → Validações em todos os DTOs
- ✅ **Persistência + Seeds** → Flyway + dados iniciais completos
- ✅ **Tratamento de exceções** → @RestControllerAdvice global
- ✅ **Conexão com ODS** → Alinhamento com ODS 4, 8, 9 e 10
- ✅ **README completo** → Este documento

### 🎁 Extras Implementados (Bônus)

- 🌐 **Interface Web** completa com Thymeleaf + Bootstrap
- 🔐 **Autenticação JWT** para API REST
- 📚 **Swagger/OpenAPI** com documentação interativa
- 🧪 **200+ testes** unitários com JUnit 5 + Mockito
- 📬 **Collection Insomnia** pronta para uso
- 🎯 **Validação de datas** (@PastOrPresent)
- 🔍 **Queries customizadas** para filtros avançados
- ⚠️ **Respostas padronizadas** de erro com timestamp
- 📊 **Índices no banco** para otimização de performance
- 🔄 **Flyway Migrations** para controle de versão do banco

### 🔥 Diferenciais Técnicos

- ✅ Código **limpo** e **bem organizado**
- ✅ Separação clara de **responsabilidades**
- ✅ **DTOs** para todas as operações (Request/Response)
- ✅ **Mappers** dedicados para conversões
- ✅ **Exceções customizadas** com tratamento centralizado
- ✅ **Validações em múltiplas camadas**
- ✅ **Relacionamentos JPA** bem definidos
- ✅ **Seeds realistas** com dados do futuro do trabalho
- ✅ **Documentação** profissional e completa

---

## 📊 Competências do Futuro Abordadas

As trilhas da plataforma cobrem as competências essenciais para 2030+:

| Categoria | Competências |
|-----------|--------------|
| 🤖 **Tecnológicas** | IA, Machine Learning, Análise de Dados, Big Data, Cloud Computing, DevOps |
| 💻 **Desenvolvimento** | Web Full Stack, Programação, APIs, Arquitetura de Software |
| 🧠 **Soft Skills** | Liderança, Gestão de Equipes, Comunicação, Colaboração, Agilidade |
| 📈 **Negócios** | Product Management, Transformação Digital, Inovação, Estratégia |

---

## 📚 Regras de Negócio Implementadas

### Usuários
- ✅ Email deve ser **único** no sistema
- ✅ Data de cadastro **não pode ser futura**
- ✅ Não permite **duplicatas completas**
- ✅ Campos opcionais: área de atuação, nível de carreira

### Trilhas
- ✅ Níveis válidos: **INICIANTE, INTERMEDIARIO, AVANCADO**
- ✅ Carga horária entre **1-1000 horas**
- ✅ Não permite **duplicatas completas**
- ✅ Foco principal identifica a competência principal

### Matrículas
- ✅ Usuário e Trilha **devem existir**
- ✅ Data de inscrição **não pode ser futura**
- ✅ Status válidos: **EM_ANDAMENTO, CONCLUIDA, CANCELADA**
- ✅ **Regra crítica:** Não permite usuário matriculado 2x na mesma trilha com status EM_ANDAMENTO
- ✅ Relacionamentos: Usuario ←→ Matricula ←→ Trilha

---

## 🔐 Credenciais de Acesso

### 🔑 JWT (API REST)

| Username | Password | Role       |
|----------|----------|------------|
| admin    | admin    | ROLE_ADMIN |
| user     | user     | ROLE_USER  |

### 🗄️ H2 Console (Banco de Dados)

| Campo    | Valor                                                            |
|----------|------------------------------------------------------------------|
| JDBC URL | `jdbc:h2:mem:globalsolutiondb;DB_CLOSE_DELAY=-1;MODE=PostgreSQL` |
| Username | `gs2`                                                            |
| Password | `2025`                                                           |

---

## 📦 Dependências Principais
```xml
<!-- Spring Boot -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <version>3.3.4</version>
</dependency>

<!-- Spring Data JPA -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<!-- Spring Security -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<!-- Thymeleaf -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>

<!-- JWT -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.11.5</version>
</dependency>

<!-- OpenAPI/Swagger -->
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.6.0</version>
</dependency>

<!-- H2 Database -->
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>

<!-- Flyway -->
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-core</artifactId>
</dependency>

<!-- Lombok -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>
```

---

## 🎓 Integrantes do Grupo

| Nome | RM | Turma |
|------|-----|-------|
| **Renan Dias Utida** | RM 558540 | 2ESPW |
| **Camila Pedroza da Cunha** | RM 558768 | 2ESPW |

---

## 📄 Licença

Este projeto está sob a licença **MIT**. Consulte o arquivo [LICENSE](LICENSE) para mais detalhes.

---

## 🙏 Agradecimentos

- **FIAP** - Pela oportunidade e conhecimento proporcionado
- **Professor Salatiel Marinho** - Pela excelente orientação na disciplina de Domain Driven Design
- **Colegas de turma** - Pela colaboração e troca de experiências

---

<div align="center">

**Desenvolvido com ❤️ para a Global Solution 2025**

🌍 **O Futuro do Trabalho começa agora!** 🚀

---

### 🔗 Links Úteis

[![Swagger UI](https://img.shields.io/badge/Swagger-UI-green?style=for-the-badge&logo=swagger)](http://localhost:8080/swagger-ui.html)
[![H2 Console](https://img.shields.io/badge/H2-Console-blue?style=for-the-badge)](http://localhost:8080/h2-console)
[![GitHub](https://img.shields.io/badge/GitHub-Repository-black?style=for-the-badge&logo=github)](https://github.com/seu-usuario/global-solution-api)

</div>
