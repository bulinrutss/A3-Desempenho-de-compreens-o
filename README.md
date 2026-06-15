# Sistema de Controle de Estoque — A3 Gestão e Qualidade de Software

**Universidade do Sul de Santa Catarina (UNISUL)**  
**Unidade Curricular:** Gestão e Qualidade de Software  
**Professor:** Jorge Werner  
**Atividade:** A3 – Desempenho de compreensão  

Este repositório contém o **sistema legado de controle de estoque** (desenvolvido na A3 de Programação de Soluções Computacionais) e a **implementação dos testes automatizados**, integração contínua e análise estática exigidos pela A3 de Gestão e Qualidade de Software.

---

## Integrantes do Grupo

| Nome | RA | GitHub | Papel |
|------|-----|--------|-------|
| Marcos Antonio Gasperin | 10724265643 | [@bulinrutss](https://github.com/bulinrutss) | Líder de equipe |
| Guilherme Custodio Capote | 10724269158 | [@Gcapote45](https://github.com/Gcapote45) | Desenvolvedor / Testes |
| Lucas Matheus de Amarante | 10724112695 | [@mathlucs](https://github.com/mathlucs) | Desenvolvedor / Testes |
| André Ghizoni Pereira Silva | 1072316272 | [@ghizoniandre-byte](https://github.com/ghizoniandre-byte) | Testador |
| João Vitor Cardoso de Jesus | 10724266837 | [@C4rdos027](https://github.com/C4rdos027) | Especialista DevOps (CI/CD e SonarCloud) |

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
├── .github/workflows/      # CI com GitHub Actions
├── sonar-project.properties
├── pom.xml
├── LICENSE
└── README.md
```

---

## Pré-requisitos

| Software | Versão mínima | Para quê |
|----------|---------------|----------|
| **Java JDK** | 8+ | Compilar e executar o projeto |
| **Apache Maven** | 3.6+ | Build, dependências e testes |
| **Git** | 2.x | Controle de versão |
| **MySQL** | 8.0+ | Banco de dados (sistema completo) |
| **Apache Tomcat** | 9.0+ | Deploy web e API REST |

**Clonar e preparar o projeto:**

```bash
git clone https://github.com/bulinrutss/A3-Desempenho-de-compreens-o.git
cd A3-Desempenho-de-compreens-o
mvn clean install -DskipTests
```

---

## Como executar os testes

```bash
mvn test                              # todos os testes
mvn test -Dtest=CategoriaTest         # teste específico
mvn clean verify                      # testes + cobertura (JaCoCo)
```

Relatório de cobertura: `target/site/jacoco/index.html`  
Meta da A3: **≥ 75%** nas classes de modelo, DAO, controllers e REST.

---

## Estratégia de testes do grupo

| Tipo | O que testar | Ferramenta | Pasta |
|------|--------------|------------|-------|
| Unitário | Modelos, regras de negócio | JUnit 5 + Mockito | `src/test/java/modelo/` |
| Integração | DAOs | JUnit 5 + Mockito | `src/test/java/dao/` |
| API REST | Endpoints JSON | JUnit 5 + Jersey Test | `src/test/java/controller/rest/` |
| Regressão | Suite completa na CI | GitHub Actions | `.github/workflows/` |

### Distribuição de testes por integrante

| Integrante | Funcionalidade | Arquivo(s) de teste |
|------------|----------------|---------------------|
| Marcos Antonio Gasperin | Categorias (modelo/DAO) | `CategoriaTest.java`, `CategoriaDAOTest.java` |
| Guilherme Custodio Capote | Produtos (modelo/DAO) | `ProdutoTest.java`, `ProdutoDAOTest.java` |
| Lucas Matheus de Amarante | Movimentações | `MovimentacaoTest.java`, `MovimentacaoDAOTest.java` |
| André Ghizoni Pereira Silva | Servlets / controllers web | `src/test/java/controller/*Test.java` |
| João Vitor Cardoso de Jesus | API REST + CI/SonarCloud | `src/test/java/controller/rest/*Test.java`, `.github/`, `sonar-project.properties` |

> Cada integrante commita **apenas os arquivos que desenvolveu**, com mensagens claras e atômicas.

---

## Ferramentas de qualidade (A3)

| Ferramenta | Finalidade | Onde |
|------------|------------|------|
| **JUnit 5** | Testes unitários | `pom.xml` |
| **Mockito** | Mocks e isolamento | `pom.xml` |
| **JaCoCo** | Cobertura de código | `pom.xml` / `target/site/jacoco/` |
| **GitHub Actions** | CI a cada push na `main` | `.github/workflows/ci.yml` |
| **SonarCloud** | Análise estática e quality gate | `sonar-project.properties` |

### SonarCloud — conferência

O painel do projeto é **público** e atualizado automaticamente pela CI a cada push na `main`:

**https://sonarcloud.io/project/overview?id=bulinrutss_A3-Desempenho-de-compreens-o**

- **Organização:** `bulinrutss`
- **Project key:** `bulinrutss_A3-Desempenho-de-compreens-o`
- **CI:** GitHub Actions (testes + análise SonarCloud)
- **Evolução do quality gate:** [docs/EVOLUCAO-SONARCLOUD.md](./docs/EVOLUCAO-SONARCLOUD.md)

---

## Como executar o sistema

**Desktop:**

```bash
mvn clean package
java -jar target/ControleEstoque-1.0-jar-with-dependencies.jar
```

**Web / API REST:** ver [docs/DEPLOY-TOMCAT.md](./docs/DEPLOY-TOMCAT.md)

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
| Mockito | 5.14.2 | Mocks nos testes |
| JaCoCo | 0.8.12 | Cobertura de código |
| Swing | — | Interface desktop |
| Servlet/JSP | 4.0.1 / 2.3.3 | Interface web |
| Jersey (JAX-RS) | 2.35 | API REST |
| Tomcat | 9.0+ | Servidor web |

---

## Documentação Técnica

- [Evolução SonarCloud / Quality Gate](./docs/EVOLUCAO-SONARCLOUD.md)
- [Arquitetura](./docs/ARQUITETURA.md)
- [Projeto Completo](./docs/PROJETO-COMPLETO.md)
- [API REST](./docs/API-REST-DOCUMENTACAO.md)
- [Deploy no Tomcat](./docs/DEPLOY-TOMCAT.md)

---

## Links

- **Repositório GitHub:** https://github.com/bulinrutss/A3-Desempenho-de-compreens-o
- **SonarCloud (análise estática — público):** https://sonarcloud.io/project/overview?id=bulinrutss_A3-Desempenho-de-compreens-o
- **Aplicação web (legado):** https://estoquejava.ruts.dev/

---

## Licença

Projeto acadêmico, sem fins lucrativos, desenvolvido para fins de aprendizagem na **UNISUL**.
