# 📦 Sistema de Controle de Estoque - Projeto Completo

## 📋 Visão Geral

Este projeto consiste em **duas aplicações** para gerenciamento de estoque:

1. **Aplicação Desktop** - Interface Swing (Java Desktop)
2. **Aplicação Web** - Interface JSP/Servlets (Java Web)

Ambas compartilham o **mesmo banco de dados MySQL** e possuem funcionalidades equivalentes.

---

## 🗂️ Estrutura do Projeto

```
ControleEstoque/
│
├── src/                          # Aplicação Desktop (Swing)
│   └── main/java/
│       ├── ControleEstoque.java  # Classe principal
│       ├── dao/                  # Data Access Objects
│       ├── modelo/               # Classes de modelo
│       └── visao/                # Interfaces Swing
│
├── web-app/                      # Aplicação Web (JSP/Servlets)
│   ├── src/main/
│   │   └── webapp/
│   │       ├── WEB-INF/
│   │       ├── css/              # Estilos
│   │       ├── js/               # Scripts
│   │       └── *.jsp             # Páginas web
│   ├── target/
│   │   └── controle-estoque-web.war  # Arquivo deployável
│   ├── pom.xml
│   ├── README.md
│   ├── Deploy.bat
│   └── DEPLOY-GUIDE.md
│
├── pom.xml                       # Config Maven (Desktop)
├── Iniciar.bat                   # Launcher Desktop
├── ControleEstoque.sql           # Script SQL original
└── README.md
```

---

## 🗄️ Banco de Dados

### Informações de Conexão:
- **Host:** localhost:3306
- **Banco:** controle_estoque
- **Usuário:** root
- **Senha:** (vazio - padrão XAMPP)

### Tabelas:

#### `categoria`
```sql
CREATE TABLE categoria (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    tamanho VARCHAR(50),
    embalagem VARCHAR(50)
);
```

#### `produto`
```sql
CREATE TABLE produto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    preco_unitario DECIMAL(10,2) NOT NULL,
    unidade VARCHAR(20) NOT NULL,
    quantidade INT NOT NULL,
    quantidade_minima INT NOT NULL,
    quantidade_maxima INT NOT NULL,
    categoria_id INT,
    FOREIGN KEY (categoria_id) REFERENCES categoria(id)
);
```

#### `movimentacao`
```sql
CREATE TABLE movimentacao (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo ENUM('ENTRADA', 'SAIDA') NOT NULL,
    quantidade INT NOT NULL,
    data_movimento TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    produto_id INT,
    FOREIGN KEY (produto_id) REFERENCES produto(id)
);
```

### Dados de Exemplo:

O banco já contém dados de exemplo:
- **4 categorias:** Bebidas, Alimentos, Limpeza, Higiene
- **12 produtos** distribuídos entre as categorias
- **6 movimentações** de entrada/saída

---

## 🖥️ Aplicação Desktop

### Executar:

**Método 1 - Launcher automático:**
```batch
Iniciar.bat
```

**Método 2 - Linha de comando:**
```bash
cd ControleEstoque
java -jar target/ControleEstoque-1.0-jar-with-dependencies.jar
```

### Funcionalidades:
- ✅ Gerenciar Categorias (CRUD completo)
- ✅ Gerenciar Produtos (CRUD completo)
- ✅ Registrar Movimentações (Entrada/Saída)
- ✅ Visualizar Histórico de Movimentações
- ✅ Relatórios:
  - Lista de Preços
  - Produtos Abaixo do Mínimo
  - Produtos Acima do Máximo
  - Produtos por Categoria
  - Balanço Financeiro

### Tecnologias:
- Java 8
- Swing (GUI)
- Maven
- MySQL Connector 8.0.33

---

## 🌐 Aplicação Web

### Compilar:

```bash
cd web-app
mvn clean package
```

### Executar:

1. Copie `web-app/target/controle-estoque-web.war` para `webapps/` do Tomcat
2. Inicie o Tomcat
3. Acesse: `http://localhost:8080/controle-estoque-web/`

### Funcionalidades:
- ✅ Gerenciar Categorias (CRUD completo)
- ✅ Gerenciar Produtos (CRUD completo)
- ✅ Registrar Movimentações (Entrada/Saída)
- ✅ Visualizar Histórico de Movimentações
- ✅ Relatórios:
  - Lista de Preços (com impressão)
  - Produtos Abaixo do Mínimo (com impressão)
  - Produtos Acima do Máximo (com impressão)

### Tecnologias:
- Java 8
- Servlets 4.0
- JSP 2.3
- JSTL 1.2
- JAX-RS 2.1 (API REST)
- Jersey 2.35 (Implementação JAX-RS)
- Jackson 2.15 (Serialização JSON)
- Maven
- MySQL Connector 8.0.33
- CSS3 + JavaScript

### Design:
- Interface moderna com gradientes roxo/azul
- Totalmente responsiva (mobile-friendly)
- Validação de formulários
- Alertas visuais para estoque crítico
- Suporte a impressão de relatórios

---

## 🎯 Casos de Uso Principais

### 1. Cadastrar Novo Produto
1. Acesse "Categorias" e crie uma categoria
2. Acesse "Produtos" → "Novo Produto"
3. Preencha os dados:
   - Nome
   - Categoria
   - Preço
   - Unidade
   - Quantidade atual
   - Mínimo/Máximo
4. Clique em "Cadastrar"

### 2. Registrar Entrada de Mercadoria
1. Acesse "Movimentações" → "Nova Movimentação"
2. Selecione o tipo: **ENTRADA**
3. Escolha o produto
4. Informe a quantidade
5. O sistema atualiza o estoque automaticamente

### 3. Registrar Saída de Mercadoria
1. Acesse "Movimentações" → "Nova Movimentação"
2. Selecione o tipo: **SAÍDA**
3. Escolha o produto
4. Informe a quantidade
5. ⚠️ Sistema alerta se quantidade > estoque disponível
6. O estoque é atualizado automaticamente

### 4. Gerar Relatório de Estoque Crítico
1. Acesse "Relatórios"
2. Escolha "Produtos Abaixo do Mínimo"
3. Visualize produtos que precisam reposição
4. (Web) Clique em "Imprimir" para gerar PDF

---

## 🔧 Configuração do Ambiente

### Requisitos:

- **Java JDK 8+** (configurado com JAVA_HOME)
- **Maven 3.6+** (configurado no PATH)
- **MySQL/MariaDB** (XAMPP ou standalone)
- **Apache Tomcat 9+** (somente para aplicação web)

### Instalação do Banco:

```sql
-- 1. Criar banco
CREATE DATABASE controle_estoque
CHARACTER SET utf8mb4
COLLATE utf8mb4_general_ci;

-- 2. Usar o banco
USE controle_estoque;

-- 3. Criar as 3 tabelas (veja scripts acima)

-- 4. Popular com dados de exemplo (opcional)
-- Execute os INSERTs disponíveis no README
```

---

## 📊 Comparação Desktop vs Web

| Característica | Desktop | Web |
|----------------|---------|-----|
| **Interface** | Swing | JSP/HTML/CSS |
| **Acesso** | Local | Navegador |
| **Instalação** | Requer Java | Requer Tomcat |
| **Mobilidade** | Limitada | Alta (qualquer dispositivo) |
| **Impressão** | Difícil | Nativa do navegador |
| **Multi-usuário** | Limitado | Suporta múltiplos usuários |
| **Atualizações** | Redistribuir JAR | Substituir WAR |
| **API REST** | ❌ | ✅ (Endpoints disponíveis) |

---

## 🐛 Troubleshooting Comum

### Desktop não inicia:
```
Solução: Execute Iniciar.bat e verifique mensagens de erro
```

### Erro de conexão com banco:
```
Solução: 
1. Inicie XAMPP → MySQL
2. Verifique senha em dao/Conexao.java
3. Teste conexão: mysql -u root -p
```

### Web retorna 404:
```
Solução:
1. Verifique se Tomcat está rodando
2. Confirme que WAR está em webapps/
3. Aguarde 10 segundos para deploy
4. Acesse URL completa: http://localhost:8080/controle-estoque-web/
```

### Servlet não encontrado:
```
Solução:
1. Recompile: mvn clean package
2. Redeploy o WAR
3. Verifique anotações @WebServlet
```

---

## 📈 Próximos Passos / Melhorias Futuras

- [ ] Sistema de login e autenticação
- [ ] Controle de permissões por usuário
- [ ] Backup automático do banco de dados
- [ ] Exportação de relatórios para Excel/PDF
- [ ] Dashboard com gráficos e estatísticas
- [ ] Notificações de estoque baixo por e-mail
- [x] ~~API REST para integração com outros sistemas~~ ✅ **Implementado!**
- [ ] Suporte a código de barras
- [ ] Histórico de preços
- [ ] Multi-empresa (suporte a várias filiais)
- [ ] Autenticação JWT para API REST
- [ ] Documentação Swagger/OpenAPI

---

## 👥 Créditos

**Projeto:** A3 Unisul ADS  
**Tecnologias:** Java, MySQL, Servlets, JSP, Maven  
**Ano:** 2025

---

## 📄 Licença

Este é um projeto acadêmico desenvolvido para fins educacionais.

---

## 📞 Suporte

Para dúvidas ou problemas:

1. Verifique este README
2. Consulte o `DEPLOY-GUIDE.md` (aplicação web)
3. Verifique os logs:
   - Desktop: Console do terminal
   - Web: `tomcat/logs/catalina.out`

---

**Versão:** 1.0  
**Última atualização:** Novembro 2025

---

## ✅ Checklist de Validação

Antes de usar o sistema, confirme:

- [x] MySQL/XAMPP rodando na porta 3306
- [x] Banco `controle_estoque` criado
- [x] Tabelas criadas (categoria, produto, movimentacao)
- [x] Dados de exemplo inseridos
- [x] Java JDK instalado e JAVA_HOME configurado
- [x] Maven instalado e no PATH
- [x] Aplicação desktop compilada (target/ControleEstoque-1.0-jar-with-dependencies.jar)
- [x] Aplicação web compilada (web-app/target/controle-estoque-web.war)
- [x] Tomcat instalado (para aplicação web)
- [x] Iniciar.bat funcional
- [x] Deploy.bat funcional

---

**🎉 Sistema pronto para uso! Boa gestão de estoque!**
