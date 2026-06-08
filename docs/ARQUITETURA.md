# 🏗️ Arquitetura do Projeto - Back-end Compartilhado

## 📐 Estrutura Reorganizada

O projeto utiliza uma arquitetura de **back-end compartilhado com API REST**, onde:

- ✅ **Back-end único** em `src/main/java/` (DAOs, Models, Controllers)
- ✅ **API REST** em `src/main/java/controller/` (Endpoints RESTful)
- ✅ **Front-end web** em `web-app/src/main/webapp/` (JSP/Servlets)
- ✅ **Front-end desktop** em `src/main/java/visao/` (Swing)

---

## 📁 Estrutura de Pastas

```
ControleEstoque/
│
├── src/main/java/                    # BACK-END COMPARTILHADO
│   ├── ControleEstoque.java          # Main Desktop
│   │
│   ├── controller/                   # Servlets e REST Controllers
│   │   ├── CategoriaServlet.java
│   │   ├── ProdutoServlet.java
│   │   ├── MovimentacaoServlet.java
│   │   ├── RelatorioServlet.java
│   │   ├── CorsFilter.java           # Filtro CORS
│   │   ├── ProdutoRestController.java
│   │   ├── CategoriaRestController.java
│   │   ├── MovimentacaoRestController.java
│   │   ├── RelatorioRestController.java
│   │   └── RestApplication.java      # Config JAX-RS
│   │
│   ├── dao/                          # Data Access Objects (Compartilhado)
│   │   ├── Conexao.java
│   │   ├── CategoriaDAO.java
│   │   ├── ProdutoDAO.java
│   │   └── MovimentacaoDAO.java
│   │
│   ├── modelo/                       # Models (Compartilhado)
│   │   ├── Categoria.java
│   │   ├── Produto.java
│   │   └── Movimentacao.java
│   │
│   └── visao/                        # Views Desktop (Swing)
│       ├── MenuPrincipal.java
│       ├── CategoriaVisao.java
│       ├── ProdutoVisao.java
│       ├── MovimentacaoVisao.java
│       └── Relatorio*.java
│
├── web-app/                          # FRONT-END WEB
│   ├── src/main/webapp/
│   │   ├── WEB-INF/
│   │   │   └── web.xml
│   │   ├── css/
│   │   │   └── style.css
│   │   ├── js/
│   │   │   └── validation.js
│   │   ├── index.jsp
│   │   ├── categorias.jsp
│   │   ├── categoria-form.jsp
│   │   ├── produtos.jsp
│   │   ├── produto-form.jsp
│   │   ├── movimentacoes.jsp
│   │   ├── movimentacao-form.jsp
│   │   └── relatorios*.jsp
│   │
│   ├── target/
│   │   └── controle-estoque-web.war
│   └── pom.xml
│
├── target/
│   ├── ControleEstoque-1.0.jar
│   └── ControleEstoque-1.0-jar-with-dependencies.jar
│
├── pom.xml                           # Config Maven Principal
├── Iniciar.bat                       # Launcher Desktop
└── ARQUITETURA.md                    # Este arquivo
```

---

## 🔄 Fluxo de Funcionamento

### **Aplicação Desktop**

```
Usuario → Swing GUI → DAO → MySQL
         (visao/)    (dao/)
```

### **Arquitetura Distribuída (REST API)**

```
Cliente → HTTP/JSON → REST API → DAO → MySQL
(React/     (CORS)    (controller/)  (dao/)
Angular/
Mobile)
```

1. Usuário interage com interface Swing
2. Views chamam DAOs diretamente
3. DAOs executam operações no MySQL
4. Resultados retornam para a interface

---

### **Aplicação Web**

```
Usuario → Browser → JSP → Servlet → DAO → MySQL
                   (webapp/) (controller/) (dao/)
```

1. Usuário acessa via navegador
2. JSP renderiza a interface
3. Formulários são enviados para Servlets
4. Servlets processam requisições
5. Servlets chamam DAOs
6. DAOs executam operações no MySQL
7. Servlets retornam dados para JSP
8. JSP renderiza resultado

---

## 🔗 Compartilhamento de Código

### **O que é compartilhado:**

✅ **DAOs** (`dao/`)
- `Conexao.java` - Conexão com banco
- `CategoriaDAO.java` - CRUD de categorias
- `ProdutoDAO.java` - CRUD de produtos + relatórios
- `MovimentacaoDAO.java` - CRUD de movimentações

✅ **Models** (`modelo/`)
- `Categoria.java` - Entidade Categoria
- `Produto.java` - Entidade Produto
- `Movimentacao.java` - Entidade Movimentação

✅ **Controllers Web** (`controller/`)
- Servlets exclusivos para web
- Usam os DAOs compartilhados

### **O que NÃO é compartilhado:**

❌ **Views Desktop** - Permanecem em `visao/` (Swing)
❌ **Views Web** - Permanecem em `web-app/webapp/` (JSP)

---

## 📦 Compilação e Deploy

### **1. Compilar Projeto Principal (Back-end + Desktop)**

```bash
cd ControleEstoque
mvn clean install
```

**Gera:**
- `target/ControleEstoque-1.0.jar` - Biblioteca
- `target/ControleEstoque-1.0-jar-with-dependencies.jar` - Desktop executável

---

### **2. Compilar Aplicação Web (Front-end)**

```bash
cd web-app
mvn clean package
```

**Gera:**
- `target/controle-estoque-web.war` - Arquivo deployável no Tomcat

---

## 🔧 Dependências Maven

### **pom.xml Principal** (Desktop + Back-end)

```xml
<dependencies>
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.33</version>
    </dependency>
    
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>javax.servlet-api</artifactId>
        <version>4.0.1</version>
        <scope>provided</scope>
    </dependency>
    
    <dependency>
        <groupId>javax.servlet.jsp</groupId>
        <artifactId>javax.servlet.jsp-api</artifactId>
        <version>2.3.3</version>
        <scope>provided</scope>
    </dependency>
    
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>jstl</artifactId>
        <version>1.2</version>
    </dependency>
    
    <dependency>
        <groupId>javax.ws.rs</groupId>
        <artifactId>javax.ws.rs-api</artifactId>
        <version>2.1.1</version>
    </dependency>
    
    <dependency>
        <groupId>org.glassfish.jersey.core</groupId>
        <artifactId>jersey-server</artifactId>
        <version>2.35</version>
    </dependency>
    
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.15.2</version>
    </dependency>
</dependencies>
```

### **pom.xml Web** (Front-end)

```xml
<dependencies>
    <dependency>
        <groupId>com.projeto.controleestoque</groupId>
        <artifactId>ControleEstoque</artifactId>
        <version>1.0</version>
        <scope>compile</scope>
    </dependency>
</dependencies>
```

---

## ✅ Vantagens desta Arquitetura

### **1. Zero Duplicação de Código**
- DAOs escritos uma única vez
- Models consistentes entre Desktop e Web
- Alterações em DAO afetam ambas aplicações

### **2. Manutenção Simplificada**
- Correção de bugs em um lugar só
- Novos métodos disponíveis para Desktop e Web
- Regras de negócio centralizadas

### **3. Consistência de Dados**
- Mesma lógica de acesso ao banco
- Mesmas validações
- Mesmo comportamento em ambas interfaces

### **4. Reutilização Eficiente**
- Desktop usa: DAOs + Models + Views Swing
- Web usa: DAOs + Models + Servlets + JSP

---

## 🚀 Como Executar

### **Desktop:**

```bash
# Opção 1: Bat file
Iniciar.bat

# Opção 2: Java direto
java -jar target/ControleEstoque-1.0-jar-with-dependencies.jar
```

### **Web:**

```bash
# 1. Compilar
cd web-app
mvn clean package

# 2. Copiar WAR para Tomcat
copy target\controle-estoque-web.war "C:\Program Files\Apache Tomcat\webapps\"

# 3. Acessar
http://localhost:8080/controle-estoque-web/
```

---

## 🔍 Verificação de Funcionamento

### **Testar Desktop:**
1. Execute `Iniciar.bat`
2. Interface Swing deve abrir
3. Teste CRUD de Categorias/Produtos
4. Verifique banco de dados

### **Testar Web:**
1. Deploy WAR no Tomcat
2. Acesse `http://localhost:8080/controle-estoque-web/`
3. Teste CRUD de Categorias/Produtos
4. Dados devem ser os mesmos do Desktop

---

## 📊 Compatibilidade

| Componente | Desktop | Web | Compartilhado |
|------------|---------|-----|---------------|
| **DAOs** | ✅ | ✅ | ✅ |
| **Models** | ✅ | ✅ | ✅ |
| **Conexao** | ✅ | ✅ | ✅ |
| **Servlets** | ❌ | ✅ | ✅ (em src/) |
| **Views Swing** | ✅ | ❌ | ❌ |
| **JSP** | ❌ | ✅ | ❌ |

