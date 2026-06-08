# API REST - Sistema de Controle de Estoque

## 📋 Visão Geral

Esta API REST permite que o front-end e back-end rodem em servidores separados, usando JAX-RS (Jersey) para comunicação distribuída via HTTP/JSON.

**Base URL:** `http://localhost:8080/controle-estoque-web/api`

---

## 🔧 Configuração

### 1. Dependências (já adicionadas no pom.xml)
- JAX-RS API 2.1.1
- Jersey 2.35 (implementação JAX-RS)
- Jackson (serialização JSON)

### 2. CORS Habilitado
O filtro CORS permite requisições de qualquer origem. Em produção, configure o domínio específico do front-end.

---

## 📚 Endpoints da API

### **Categorias**

#### Listar todas as categorias
```http
GET /api/categorias
```

**Resposta:**
```json
[
  {
    "id": 1,
    "nome": "Eletrônicos",
    "tamanho": "Médio",
    "embalagem": "Caixa"
  }
]
```

#### Buscar categoria por ID
```http
GET /api/categorias/{id}
```

#### Criar nova categoria
```http
POST /api/categorias
Content-Type: application/json

{
  "nome": "Alimentos",
  "tamanho": "Pequeno",
  "embalagem": "Saco"
}
```

#### Atualizar categoria
```http
PUT /api/categorias/{id}
Content-Type: application/json

{
  "nome": "Eletrônicos Premium",
  "tamanho": "Grande",
  "embalagem": "Caixa Reforçada"
}
```

#### Excluir categoria
```http
DELETE /api/categorias/{id}
```

---

### **Produtos**

#### Listar todos os produtos
```http
GET /api/produtos
```

**Resposta:**
```json
[
  {
    "id": 1,
    "nome": "Notebook Dell",
    "precoUnitario": 3500.00,
    "unidade": "UN",
    "quantidade": 10,
    "quantidadeMinima": 5,
    "quantidadeMaxima": 50,
    "categoria": {
      "id": 1,
      "nome": "Eletrônicos"
    }
  }
]
```

#### Buscar produto por ID
```http
GET /api/produtos/{id}
```

#### Criar novo produto
```http
POST /api/produtos
Content-Type: application/json

{
  "nome": "Mouse Logitech",
  "precoUnitario": 89.90,
  "unidade": "UN",
  "quantidade": 50,
  "quantidadeMinima": 10,
  "quantidadeMaxima": 100,
  "categoriaId": 1
}
```

#### Atualizar produto
```http
PUT /api/produtos/{id}
Content-Type: application/json

{
  "nome": "Mouse Logitech MX Master",
  "precoUnitario": 299.90,
  "unidade": "UN",
  "quantidade": 30,
  "quantidadeMinima": 10,
  "quantidadeMaxima": 80,
  "categoriaId": 1
}
```

#### Excluir produto
```http
DELETE /api/produtos/{id}
```

---

### **Movimentações**

#### Listar todas as movimentações
```http
GET /api/movimentacoes
```

**Resposta:**
```json
[
  {
    "id": 1,
    "tipo": "ENTRADA",
    "quantidade": 20,
    "dataMovimento": "2025-11-26T10:30:00",
    "produto": {
      "id": 1,
      "nome": "Notebook Dell"
    }
  }
]
```

#### Buscar movimentação por ID
```http
GET /api/movimentacoes/{id}
```

#### Registrar nova movimentação
```http
POST /api/movimentacoes
Content-Type: application/json

{
  "tipo": "ENTRADA",
  "quantidade": 10,
  "produtoId": 1
}
```

**Tipos válidos:** `ENTRADA` ou `SAIDA`

#### Listar movimentações de um produto
```http
GET /api/movimentacoes/produto/{produtoId}
```

---

### **Relatórios**

#### Dashboard (resumo geral)
```http
GET /api/relatorios/dashboard
```

**Resposta:**
```json
{
  "totalProdutos": 45,
  "produtosAbaixoMinimo": 5,
  "produtosAcimaMaximo": 2,
  "valorTotalEstoque": 125890.50
}
```

#### Produtos abaixo do mínimo
```http
GET /api/relatorios/produtos-abaixo-minimo
```

#### Produtos acima do máximo
```http
GET /api/relatorios/produtos-acima-maximo
```

#### Lista de preços
```http
GET /api/relatorios/lista-precos
```

**Resposta:**
```json
{
  "produtos": [...],
  "valorTotalEstoque": 125890.50
}
```

#### Produtos por categoria
```http
GET /api/relatorios/produtos-por-categoria/{categoriaId}
```

#### Balanço financeiro
```http
GET /api/relatorios/balanco-financeiro
```

**Resposta:**
```json
{
  "valorTotalEstoque": 125890.50,
  "totalProdutosCadastrados": 45,
  "totalItensEstoque": 350,
  "produtos": [...]
}
```

---

## 🚀 Como Usar

### **Back-end (API REST)**
1. Compile e execute o projeto Java
2. API estará disponível em `http://localhost:8080`

### **Front-end (Exemplo com JavaScript)**

```html
<!DOCTYPE html>
<html>
<body>
    <div id="produtos"></div>

    <script>
        // Listar produtos
        fetch('http://localhost:8080/controle-estoque-web/api/produtos')
            .then(response => response.json())
            .then(data => {
                const container = document.getElementById('produtos');
                data.forEach(produto => {
                    container.innerHTML += `
                        <div>
                            ${produto.nome} - R$ ${produto.precoUnitario}
                            <button onclick="excluirProduto(${produto.id})">Excluir</button>
                        </div>
                    `;
                });
            });

        // Criar produto
        function criarProduto() {
            fetch('http://localhost:8080/controle-estoque-web/api/produtos', {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({
                    nome: 'Produto Teste',
                    precoUnitario: 99.90,
                    unidade: 'UN',
                    quantidade: 10,
                    quantidadeMinima: 5,
                    quantidadeMaxima: 50,
                    categoriaId: 1
                })
            })
            .then(response => response.json())
            .then(data => console.log(data));
        }

        // Excluir produto
        function excluirProduto(id) {
            fetch(`http://localhost:8080/controle-estoque-web/api/produtos/${id}`, {
                method: 'DELETE'
            })
            .then(response => response.json())
            .then(data => {
                alert(data.message);
                location.reload();
            });
        }
    </script>
</body>
</html>
```

---

## ✅ Respostas Padrão

### Sucesso
```json
{
  "status": "success",
  "message": "Operação realizada com sucesso"
}
```

### Erro
```json
{
  "status": "error",
  "message": "Descrição do erro"
}
```

---

## 🔒 Códigos HTTP

- `200 OK` - Requisição bem-sucedida
- `201 Created` - Recurso criado com sucesso
- `400 Bad Request` - Dados inválidos
- `404 Not Found` - Recurso não encontrado
- `500 Internal Server Error` - Erro no servidor

---

## 📝 Notas

1. **CORS:** Configurado para aceitar requisições de qualquer origem (`*`). Em produção, especifique o domínio do front-end.

2. **Autenticação:** Esta versão não implementa autenticação. Para produção, adicione JWT ou OAuth2.

3. **Validação:** Adicione validações de dados no lado do servidor para maior segurança.

4. **Paginação:** Para grandes volumes de dados, implemente paginação nos endpoints de listagem.

---

## 🛠️ Tecnologias Utilizadas

- **JAX-RS 2.1** - Especificação REST
- **Jersey 2.35** - Implementação JAX-RS
- **Jackson** - Serialização JSON
- **MySQL** - Banco de dados
- **Servlet 4.0** - Container web
