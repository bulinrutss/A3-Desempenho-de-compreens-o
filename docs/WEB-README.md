# Sistema de Controle de Estoque - Versão Web

## 📋 Descrição
Interface web para o Sistema de Controle de Estoque desenvolvida com JSP, Servlets e JSTL. Esta aplicação permite gerenciar produtos, categorias e movimentações de estoque através de um navegador web.

## 🛠️ Tecnologias Utilizadas
- **Java 8**
- **Servlets 4.0**
- **JSP 2.3**
- **JSTL 1.2**
- **JAX-RS 2.1** (API REST)
- **Jersey 2.35** (Implementação JAX-RS)
- **Jackson 2.15** (Serialização JSON)
- **Maven** (gerenciamento de dependências)
- **MySQL** (banco de dados)
- **Apache Tomcat 9+** (servidor de aplicação)

## 📦 Estrutura do Projeto
```
web-app/
├── src/
│   └── main/
│       ├── java/
│       │   ├── controller/      # Servlets (Controladores)
│       │   ├── dao/             # Data Access Objects
│       │   └── modelo/          # Classes de modelo (POJOs)
│       └── webapp/
│           ├── WEB-INF/
│           │   └── web.xml      # Configuração da aplicação
│           ├── css/
│           │   └── style.css    # Estilos
│           ├── js/
│           │   └── validation.js # Validações JavaScript
│           └── *.jsp            # Páginas JSP
├── pom.xml                      # Configuração Maven
└── README.md
```

## 🚀 Como Executar

### Pré-requisitos
1. **Java JDK 8 ou superior**
2. **Maven 3.6+**
3. **Apache Tomcat 9+**
4. **MySQL/MariaDB** (XAMPP ou instalação standalone)
5. **Banco de dados configurado** (usar o mesmo banco do aplicativo desktop)

### Passos para Deploy

#### 1. Compilar o Projeto
```bash
cd web-app
mvn clean package
```

#### 2. Deploy no Tomcat

**Opção A: Manual**
- Copie o arquivo `target/controle-estoque-web.war` para a pasta `webapps` do Tomcat
- Inicie o Tomcat
- Acesse: `http://localhost:8080/controle-estoque-web/`

**Opção B: Maven Tomcat Plugin**
```bash
mvn tomcat7:deploy
# ou para redeploy
mvn tomcat7:redeploy
```

**Opção C: IDE (Eclipse/IntelliJ)**
- Importe o projeto como Maven Project
- Configure o servidor Tomcat na IDE
- Execute "Run on Server"

### 3. Configuração do Banco de Dados

O aplicativo usa o mesmo banco de dados do sistema desktop. Certifique-se de que:

- O MySQL está em execução (porta 3306)
- O banco `controle_estoque` existe
- As tabelas estão criadas: `categoria`, `produto`, `movimentacao`
- Credenciais em `../src/main/java/dao/Conexao.java`:
  - URL: `jdbc:mysql://localhost:3306/controle_estoque`
  - Usuário: `root`
  - Senha: `` (vazio para XAMPP)

## 📱 Funcionalidades

### 1. Gestão de Categorias
- ✅ Listar todas as categorias
- ✅ Cadastrar nova categoria
- ✅ Editar categoria existente
- ✅ Excluir categoria

### 2. Gestão de Produtos
- ✅ Listar todos os produtos
- ✅ Cadastrar novo produto
- ✅ Editar produto existente
- ✅ Excluir produto
- ✅ Visualização de status do estoque (Normal/Baixo/Alto)

### 3. Movimentações
- ✅ Registrar entrada de produtos
- ✅ Registrar saída de produtos
- ✅ Histórico de movimentações
- ✅ Atualização automática do estoque

### 4. Relatórios
- ✅ Lista de preços
- ✅ Produtos abaixo do mínimo
- ✅ Produtos acima do máximo
- ✅ Balanço financeiro
- ✅ Produtos por categoria
- ✅ Dashboard com estatísticas
- ✅ Impressão de relatórios

### 5. API REST
- ✅ Endpoints RESTful (JSON)
- ✅ CRUD completo via API
- ✅ CORS habilitado para front-end separado
- ✅ Suporte a arquitetura distribuída

## 🎨 Interface

A interface web possui:
- Design moderno e responsivo
- Cores baseadas em gradientes roxo/azul
- Ícones intuitivos para navegação
- Tabelas formatadas para fácil leitura
- Formulários com validação
- Alertas visuais para estoque crítico
- Suporte a impressão de relatórios

## 🔧 Configurações Avançadas

### Alterar Porta do Servidor
Edite o `server.xml` do Tomcat:
```xml
<Connector port="8080" protocol="HTTP/1.1" .../>
```

### Configurar Pool de Conexões
Para ambientes de produção, considere usar um DataSource em vez de conexões diretas. Edite o `web.xml` e `context.xml` do Tomcat.

### Habilitar HTTPS
Configure um certificado SSL no Tomcat para comunicação segura.

## 📊 Arquitetura

O projeto segue o padrão **MVC (Model-View-Controller)**:

- **Model**: Classes em `modelo/` (Categoria, Produto, Movimentacao)
- **View**: Páginas JSP (*.jsp)
- **Controller**: Servlets em `controller/`
- **DAO**: Camada de acesso a dados em `dao/`

## 🐛 Solução de Problemas

### Erro 404 - Página não encontrada
- Verifique se o WAR foi deployado corretamente
- Confirme que o Tomcat está em execução
- Acesse a URL completa: `http://localhost:8080/controle-estoque/`

### Erro de Conexão com Banco
- Verifique se o MySQL está rodando (XAMPP)
- Confirme as credenciais em `Conexao.java`
- Teste a conexão manualmente

### Servlet não encontrado
- Verifique as anotações `@WebServlet`
- Confirme que o `web.xml` está configurado corretamente
- Recompile o projeto: `mvn clean package`

## 📞 Suporte

Para dúvidas ou problemas:
1. Verifique os logs do Tomcat em `logs/catalina.out`
2. Confirme que todas as dependências Maven foram baixadas
3. Teste primeiro o aplicativo desktop para validar o banco de dados

## 📄 Licença

Este projeto faz parte de um trabalho acadêmico.

---

**Versão**: 1.0  
**Última atualização**: 2025
