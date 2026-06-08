# Sistema de Controle de Estoque — A3 Gestão e Qualidade de Software

**Universidade do Sul de Santa Catarina (UNISUL)**  
**Unidade Curricular:** Gestão e Qualidade de Software  
**Professor:** Jorge Werner  
**Atividade:** A3 – Desempenho de compreensão  

Este repositório contém o **sistema legado de controle de estoque** (desenvolvido na A3 de Programação de Soluções Computacionais) e a **implementação dos testes automatizados**, integração contínua e análise estática exigidos pela A3 de Gestão e Qualidade de Software.

---

## Integrantes do Grupo

| Nome | RA | Papel sugerido |
|------|-----|----------------|
| Marcos Antonio Gasperin | 10724265643 | Líder de equipe |
| Guilherme Custodio Capote | 10724269158 | Desenvolvedor / Testes |
| Lucas Matheus de Amarante | 10724112695 | Desenvolvedor / Testes |
| André Ghizoni Pereira Silva | 1072316272 | Testador |
| João Vitor Cardoso de Jesus | 10724266837 | Especialista DevOps (CI/CD e SonarCloud) |

> Todos os integrantes devem programar testes unitários e realizar commits individuais no código-fonte, conforme o edital da A3.

---

## Objetivo da A3

Aplicar práticas de **qualidade de software** sobre o sistema legado:

- Plano e execução de testes (unitário, integração, regressão)
- **Cobertura mínima de 75%** do código-fonte
- **Integração contínua** com GitHub Actions
- **Análise estática** com SonarCloud e quality gate
- **Gestão de configuração** via Git/GitHub (branch `main`)
- Resolução de issues abertas pelo professor

---

## Descrição do Sistema Legado

Sistema de controle de estoque em **Java + MySQL** com cadastro de categorias, produtos, movimentações de entrada/saída e relatórios gerenciais.

**Arquitetura:**

- **Desktop (Swing):** aplicação local com interface gráfica
- **Web (JSP/Servlets):** acesso via navegador no Tomcat
- **API REST (Jersey/JAX-RS):** endpoints JSON para integração distribuída

---

## Estrutura do Repositório

```
A3-Desempenho-de-compreens-o/
├── src/
│   ├── main/java/          # Código-fonte do sistema legado
│   └── test/java/          # Testes automatizados (JUnit 5)
├── web-app/                # Módulo web (WAR para Tomcat)
├── docs/                   # Documentação técnica
├── ControleEstoque.sql     # Script do banco de dados
├── pom.xml                 # Dependências, testes e JaCoCo
├── LICENSE
└── README.md
```

---

## O que instalar (pré-requisitos)

### Obrigatório para desenvolver e rodar testes

| Software | Versão mínima | Para quê |
|----------|---------------|----------|
| **Java JDK** | 8+ | Compilar e executar o projeto |
| **Apache Maven** | 3.6+ | Build, dependências e testes |
| **Git** | 2.x | Controle de versão |

### Obrigatório para rodar o sistema completo (não para testes unitários básicos)

| Software | Versão mínima | Para quê |
|----------|---------------|----------|
| **MySQL** | 8.0+ | Banco de dados em produção/desenvolvimento |
| **Apache Tomcat** | 9.0+ | Deploy da aplicação web e API REST |

### Recomendado

| Software | Para quê |
|----------|----------|
| **IntelliJ IDEA** ou **VS Code** + Extension Pack for Java | Editar código e executar testes pela IDE |
| **Conta GitHub** | Commits individuais e CI/CD |
| **Conta SonarCloud** | Análise estática e quality gate |

---

## Instalação passo a passo (Windows)

### 1. Clonar o repositório

```bash
git clone https://github.com/bulinrutss/A3-Desempenho-de-compreens-o.git
cd A3-Desempenho-de-compreens-o
```

### 2. Verificar Java e Maven

```bash
java -version
mvn -version
```

Saída esperada: Java 8 (ou superior) e Maven 3.6+.

**Instalar Java (se necessário):** [Adoptium Temurin JDK 8](https://adoptium.net/)  
**Instalar Maven (se necessário):** [Apache Maven](https://maven.apache.org/download.cgi) — adicione `MAVEN_HOME` e `%MAVEN_HOME%\bin` ao PATH.

### 3. Baixar dependências do projeto

Na raiz do projeto:

```bash
mvn clean install -DskipTests
```

O Maven baixa automaticamente JUnit, Mockito, JaCoCo e demais bibliotecas definidas no `pom.xml`. **Não é necessário instalar JUnit manualmente.**

---

## Como executar os testes

### Rodar todos os testes

```bash
mvn test
```

### Rodar um teste específico

```bash
mvn test -Dtest=CategoriaTest
```

### Rodar testes e gerar relatório de cobertura (JaCoCo)

```bash
mvn clean test jacoco:report
```

Após executar, abra o relatório HTML em:

```
target/site/jacoco/index.html
```

A meta da A3 é atingir **≥ 75% de cobertura** nas classes de modelo, DAO, controllers e REST.

### Executar testes pela IDE

**IntelliJ IDEA:** clique com o botão direito em `src/test/java` → *Run 'All Tests'*  
**VS Code:** abra um arquivo `*Test.java` → ícone *Run Test* acima do método `@Test`

---

## Estratégia de testes do grupo

| Tipo | O que testar | Ferramenta | Pasta sugerida |
|------|--------------|------------|----------------|
| Unitário | Modelos, regras de negócio | JUnit 5 + Mockito | `src/test/java/modelo/` |
| Integração | DAOs com banco em memória | JUnit 5 + H2 (a adicionar) | `src/test/java/dao/` |
| API REST | Endpoints JSON | JUnit 5 + Jersey Test | `src/test/java/controller/rest/` |
| Regressão | Suite completa na CI | GitHub Actions | `.github/workflows/` |

### Distribuição de testes unitários (atualizar conforme o grupo avançar)

| Integrante | Funcionalidade | Arquivo(s) de teste |
|------------|----------------|---------------------|
| Marcos Antonio Gasperin | Categorias (modelo/DAO) | `src/test/java/modelo/CategoriaTest.java` |
| Guilherme Custodio Capote | Produtos (modelo/DAO) | `src/test/java/modelo/ProdutoTest.java` |
| Lucas Matheus de Amarante | Movimentações | `src/test/java/modelo/MovimentacaoTest.java` |
| André Ghizoni Pereira Silva | Servlets / controllers web | `src/test/java/controller/` |
| João Vitor Cardoso de Jesus | API REST + CI/SonarCloud | `src/test/java/controller/rest/` |

> Cada integrante deve commitar **apenas os arquivos de código-fonte que desenvolveu**, com mensagens claras e atômicas.

---

## Ferramentas de qualidade (A3)

| Ferramenta | Finalidade | Status |
|------------|------------|--------|
| **JUnit 5** | Testes unitários | Configurado no `pom.xml` |
| **Mockito** | Mocks e isolamento de dependências | Configurado no `pom.xml` |
| **JaCoCo** | Medição de cobertura de código | Configurado no `pom.xml` |
| **GitHub Actions** | CI — testes automáticos a cada push | A configurar |
| **SonarCloud** | Análise estática e quality gate | A configurar |

### Análise estática com SonarCloud (quando configurado)

1. Criar projeto em [sonarcloud.io](https://sonarcloud.io) vinculado ao repositório GitHub
2. Gerar token de autenticação
3. Executar localmente:

```bash
mvn clean verify sonar:sonar \
  -Dsonar.projectKey=SEU_PROJECT_KEY \
  -Dsonar.organization=SUA_ORGANIZACAO \
  -Dsonar.host.url=https://sonarcloud.io \
  -Dsonar.login=SEU_TOKEN
```

---

## Como executar o sistema (uso normal)

### Aplicação Desktop

```bash
mvn clean package
java -jar target/ControleEstoque-1.0-jar-with-dependencies.jar
```

### Aplicação Web / API REST

```bash
cd web-app
mvn clean package
# Copiar target/controle-estoque-web.war para TOMCAT_HOME/webapps/
# Acessar: http://localhost:8080/controle-estoque-web
```

> Guia completo: [docs/DEPLOY-TOMCAT.md](./docs/DEPLOY-TOMCAT.md)

---

## Convenção de commits

Use mensagens curtas e descritivas:

```
test: adiciona testes unitários para CategoriaDAO.inserir
fix: corrige validação de quantidade mínima em Produto
refactor: extrai lógica de movimentação para método reutilizável
ci: adiciona workflow de testes no GitHub Actions
```

**Não contabilizam para nota individual:** commits apenas em README, `pom.xml`, imagens ou documentação.

---

## Requisitos Funcionais

- RF01 – Cadastro de categorias
- RF02 – Cadastro de produtos
- RF03 – Controle de entradas e saídas
- RF04 – Geração de relatórios
- RF05 – Histórico de movimentações
- RF06 – API REST para integração
- RF07 – Arquitetura distribuída (front-end e back-end separados)

## Requisitos Não Funcionais

- RNF01 – Linguagem Java
- RNF02 – Banco MySQL
- RNF03 – Interface desktop em Swing
- RNF04 – Usabilidade e leveza
- RNF05 – Controle de versão no GitHub (branch `main`)
- RNF06 – API RESTful com JSON
- RNF07 – CORS habilitado na aplicação web

---

## Tecnologias

| Tecnologia | Versão | Finalidade |
|------------|--------|------------|
| Java | 8 | Linguagem principal |
| Maven | 3.6+ | Build e testes |
| MySQL | 8.0+ | Banco de dados |
| JUnit 5 | 5.10.2 | Testes unitários |
| Mockito | 4.11.0 | Mocks nos testes |
| JaCoCo | 0.8.12 | Cobertura de código |
| Swing | — | Interface desktop |
| Servlet/JSP | 4.0.1 / 2.3.3 | Interface web |
| Jersey (JAX-RS) | 2.35 | API REST |
| Tomcat | 9.0+ | Servidor web |

---

## Documentação Técnica

- [Arquitetura](./docs/ARQUITETURA.md)
- [Projeto Completo](./docs/PROJETO-COMPLETO.md)
- [API REST](./docs/API-REST-DOCUMENTACAO.md)
- [Deploy no Tomcat](./docs/DEPLOY-TOMCAT.md)

---

## Links

- **Repositório GitHub:** https://github.com/bulinrutss/A3-Desempenho-de-compreens-o
- **Aplicação web (legado):** https://estoquejava.ruts.dev/

---

## Licença

Projeto acadêmico, sem fins lucrativos, desenvolvido para fins de aprendizagem na **UNISUL**.
