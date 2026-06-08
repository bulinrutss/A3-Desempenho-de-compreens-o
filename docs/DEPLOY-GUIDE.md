# 🌐 APLICAÇÃO WEB - GUIA RÁPIDO DE DEPLOY

> ⚠️ **NOTA:** Este arquivo foi substituído por [DEPLOY-TOMCAT.md](./DEPLOY-TOMCAT.md) com informações mais completas incluindo a API REST.

## ✅ Status da Compilação
O projeto web foi **compilado com sucesso**!

**Arquivo gerado:** `target/controle-estoque-web.war`

---

## 📋 Pré-requisitos

Antes de fazer o deploy, certifique-se de ter:

- ✅ **Apache Tomcat 9+** instalado
- ✅ **MySQL/XAMPP** rodando (porta 3306)
- ✅ **Banco de dados** `controle_estoque` criado e populado
- ✅ **Porta 8080** disponível para o Tomcat

---

## 🚀 OPÇÃO 1: Deploy Manual (Mais Simples)

### Passos:

1. **Inicie o Apache Tomcat**
   - Windows: Execute `startup.bat` na pasta `bin` do Tomcat
   - Ou inicie o serviço pelo Windows Services

2. **Copie o arquivo WAR**
   ```
   Origem: web-app\target\controle-estoque-web.war
   Destino: C:\Program Files\Apache Tomcat\webapps\
   ```

3. **Aguarde o deploy automático**
   - O Tomcat detecta automaticamente novos arquivos WAR
   - Aguarde 5-10 segundos

4. **Acesse a aplicação**
   ```
   http://localhost:8080/controle-estoque-web/
   ```

---

## 🌐 OPÇÃO 2: Deploy via Tomcat Manager

1. Acesse: `http://localhost:8080/manager/html`
2. Faça login (usuário/senha configurados no `tomcat-users.xml`)
3. Na seção **"WAR file to deploy"**:
   - Clique em **"Escolher arquivo"**
   - Selecione: `web-app\target\controle-estoque-web.war`
   - Clique em **"Deploy"**
4. Acesse: `http://localhost:8080/controle-estoque-web/`

---

## 💻 OPÇÃO 3: Deploy via IDE (Eclipse/IntelliJ)

### Eclipse:

1. File → Import → Existing Maven Projects
2. Selecione a pasta `web-app`
3. Botão direito no projeto → Run As → Run on Server
4. Selecione **Tomcat 9** e clique em **Finish**

### IntelliJ IDEA:

1. File → Open → Selecione a pasta `web-app`
2. Run → Edit Configurations
3. Clique em **+** → Tomcat Server → Local
4. Configure o Tomcat Home
5. Na aba **Deployment**, adicione o artifact WAR
6. Clique em **Run**

---

## 🔍 Verificar se está funcionando

Após o deploy, acesse:

```
http://localhost:8080/controle-estoque-web/
```

Você deve ver a **tela inicial** com 4 opções:
- 📦 Categorias
- 🏷️ Produtos
- 📊 Movimentações
- 📈 Relatórios

---

## ⚠️ Solução de Problemas

### Erro 404 - Aplicação não encontrada

**Causa:** WAR não foi deployado
**Solução:**
- Verifique se o arquivo WAR está em `webapps/`
- Reinicie o Tomcat
- Verifique os logs em `logs/catalina.out`

### Erro de Conexão com Banco de Dados

**Causa:** MySQL não está rodando ou credenciais incorretas
**Solução:**
1. Inicie o XAMPP → MySQL
2. Verifique se o banco `controle_estoque` existe:
   ```sql
   SHOW DATABASES;
   USE controle_estoque;
   SHOW TABLES;
   ```
3. Confirme credenciais em:
   ```
   web-app/src/main/java/dao/Conexao.java
   ```

### Porta 8080 já em uso

**Solução:**
1. Pare outros serviços na porta 8080
2. Ou altere a porta do Tomcat em `server.xml`:
   ```xml
   <Connector port="8081" protocol="HTTP/1.1" .../>
   ```

### Servlet não encontrado (Erro 500)

**Solução:**
1. Recompile o projeto:
   ```bash
   mvn clean package
   ```
2. Faça um novo deploy do WAR atualizado
3. Verifique se as anotações `@WebServlet` estão corretas

---

## 📊 Estrutura de URLs da Aplicação

| Funcionalidade | URL |
|----------------|-----|
| **Página inicial** | `/` ou `/index.jsp` |
| **Categorias** | `/categorias` |
| **Nova Categoria** | `/categorias?action=novo` |
| **Produtos** | `/produtos` |
| **Novo Produto** | `/produtos?action=novo` |
| **Movimentações** | `/movimentacoes` |
| **Nova Movimentação** | `/movimentacoes?action=novo` |
| **Relatórios** | `/relatorios` |
| **Lista de Preços** | `/relatorios?action=lista-precos` |
| **Abaixo do Mínimo** | `/relatorios?action=abaixo-minimo` |
| **Acima do Máximo** | `/relatorios?action=acima-maximo` |

---

## 📝 Logs e Depuração

**Logs do Tomcat:**
```
C:\Program Files\Apache Tomcat\logs\catalina.out
```

**Para habilitar logs detalhados:**
Edite `logging.properties` do Tomcat e adicione:
```
org.apache.catalina.core.level = FINE
```

---

## 🔄 Recompilar e Redeploy

Sempre que alterar o código:

```bash
cd web-app
mvn clean package
```

Depois copie novamente o WAR atualizado para `webapps/` (ou use redeploy via IDE).

---

## 📞 Testando as Funcionalidades

1. **Cadastre uma categoria** (ex: Eletrônicos)
2. **Cadastre um produto** vinculado à categoria
3. **Registre uma movimentação** de entrada
4. **Gere um relatório** para verificar
5. **Teste a impressão** de relatórios

---

**Pronto! Sua aplicação web está deployada e funcionando!** 🎉
