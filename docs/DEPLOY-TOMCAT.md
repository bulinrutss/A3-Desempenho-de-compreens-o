# Deploy no Tomcat - Sistema de Controle de Estoque com API REST

## 📦 Arquivos Gerados

### 1. Aplicação Web (WAR)
- **Arquivo**: `web-app/target/controle-estoque-web.war`
- **Contém**: API REST + Servlets + JSP + Frontend

### 2. Bibliotecas Principais
- **Arquivo**: `target/ControleEstoque-1.0-jar-with-dependencies.jar`
- **Contém**: DAOs + Modelos + Dependências

---

## 🚀 Passos para Deploy no Tomcat

### **Opção 1: Deploy Manual**

1. **Copie o arquivo WAR para o Tomcat**
```powershell
# Localize o diretório webapps do Tomcat (exemplo)
$TOMCAT_HOME = "C:\Program Files\Apache Software Foundation\Tomcat 9.0"

# Copie o WAR
Copy-Item "web-app\target\controle-estoque-web.war" "$TOMCAT_HOME\webapps\"
```

2. **Inicie o Tomcat**
```powershell
cd "$TOMCAT_HOME\bin"
.\startup.bat
```

3. **Acesse a aplicação**
- **Front-end (JSP)**: http://localhost:8080/controle-estoque-web
- **API REST**: http://localhost:8080/controle-estoque-web/api/

---

### **Opção 2: Deploy via Tomcat Manager**

1. Acesse: http://localhost:8080/manager/html
2. Na seção "WAR file to deploy", selecione o arquivo `controle-estoque-web.war`
3. Clique em "Deploy"

---

## 🔧 Configuração Necessária

### 1. **Banco de Dados MySQL**

Certifique-se de que o banco de dados está configurado:

```sql
-- Execute o script de criação do banco
mysql -u root -p < ControleEstoque.sql
```

### 2. **Conexão no código**

Verifique se a classe `Conexao.java` está apontando para o servidor MySQL correto:

```java
// src/main/java/dao/Conexao.java
private static final String URL = "jdbc:mysql://localhost:3306/controle_estoque";
private static final String USER = "root";
private static final String PASSWORD = "sua_senha";
```

---

## 📡 Testando a API REST

### **1. Teste com navegador (GET)**

Abra no navegador:
```
http://localhost:8080/controle-estoque-web/api/produtos
http://localhost:8080/controle-estoque-web/api/categorias
http://localhost:8080/controle-estoque-web/api/relatorios/dashboard
```

### **2. Teste com curl (POST)**

```powershell
# Criar nova categoria
curl -X POST http://localhost:8080/controle-estoque-web/api/categorias `
  -H "Content-Type: application/json" `
  -d '{\"nome\":\"Eletrônicos\",\"tamanho\":\"Médio\",\"embalagem\":\"Caixa\"}'

# Criar novo produto
curl -X POST http://localhost:8080/controle-estoque-web/api/produtos `
  -H "Content-Type: application/json" `
  -d '{\"nome\":\"Notebook\",\"precoUnitario\":3500.00,\"unidade\":\"UN\",\"quantidade\":10,\"quantidadeMinima\":5,\"quantidadeMaxima\":50,\"categoriaId\":1}'
```

### **3. Teste com Postman**

Importe os endpoints para testar:

**GET** `/api/produtos` - Lista todos os produtos  
**POST** `/api/produtos` - Cria novo produto  
**PUT** `/api/produtos/{id}` - Atualiza produto  
**DELETE** `/api/produtos/{id}` - Exclui produto

(Consulte `API-REST-DOCUMENTACAO.md` para lista completa de endpoints)

---

## 🌐 Acessando a Aplicação

### **Interface Web (JSP)**
- Menu Principal: http://localhost:8080/controle-estoque-web/
- Categorias: http://localhost:8080/controle-estoque-web/categorias
- Produtos: http://localhost:8080/controle-estoque-web/produtos
- Movimentações: http://localhost:8080/controle-estoque-web/movimentacoes
- Relatórios: http://localhost:8080/controle-estoque-web/relatorios

### **API REST**
- Base URL: http://localhost:8080/controle-estoque-web/api/
- Documentação completa: Ver `API-REST-DOCUMENTACAO.md`

---

## 🔍 Verificando Logs

```powershell
# Logs do Tomcat
Get-Content "$TOMCAT_HOME\logs\catalina.out" -Tail 50 -Wait
```

---

## ⚠️ Troubleshooting

### **Erro: Aplicação não inicia**
- Verifique se a porta 8080 está livre
- Confira os logs em `$TOMCAT_HOME\logs\`
- Certifique-se de que o MySQL está rodando

### **Erro: 404 nos endpoints da API**
- Verifique se o arquivo WAR foi extraído corretamente em `webapps/controle-estoque-web/`
- Confirme que o contexto da aplicação está correto

### **Erro: CORS bloqueando requisições**
- O filtro CORS já está configurado em `CorsFilter.java`
- Se necessário, ajuste o domínio permitido em produção

---

## 📋 Checklist de Deploy

- [ ] MySQL instalado e rodando
- [ ] Banco de dados criado (executar `ControleEstoque.sql`)
- [ ] Tomcat instalado
- [ ] Arquivo WAR copiado para `webapps/`
- [ ] Tomcat iniciado
- [ ] Acessar http://localhost:8080/controle-estoque-web/
- [ ] Testar endpoints REST em http://localhost:8080/controle-estoque-web/api/

---

## 🎯 Próximos Passos

1. **Front-end separado**: Crie um front-end em React/Vue que consuma a API REST
2. **Autenticação**: Implemente JWT para segurança da API
3. **Documentação Swagger**: Adicione Swagger UI para documentação interativa
4. **Docker**: Containerize a aplicação para facilitar deploy

---

## 📚 Arquivos de Referência

- `API-REST-DOCUMENTACAO.md` - Documentação completa da API REST
- `README.md` - Documentação geral do projeto
- `ARQUITETURA.md` - Arquitetura do sistema
