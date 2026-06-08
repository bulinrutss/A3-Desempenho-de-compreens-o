<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Produtos - Sistema de Estoque</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>Gerenciamento de Produtos</h1>
            <nav>
                <a href="index.jsp" class="btn btn-secondary">← Voltar ao Menu</a>
                <a href="produtos?action=novo" class="btn btn-primary">+ Novo Produto</a>
            </nav>
        </header>

        <main>
            <div class="table-container">
                <table class="data-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nome</th>
                            <th>Categoria</th>
                            <th>Preço</th>
                            <th>Unidade</th>
                            <th>Estoque</th>
                            <th>Mín/Máx</th>
                            <th>Status</th>
                            <th>Ações</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="produto" items="${listaProdutos}">
                            <tr>
                                <td>${produto.id}</td>
                                <td>${produto.nome}</td>
                                <td>${produto.categoria.nome}</td>
                                <td>R$ <fmt:formatNumber value="${produto.precoUnitario}" pattern="#,##0.00"/></td>
                                <td>${produto.unidade}</td>
                                <td class="estoque-valor">${produto.quantidade}</td>
                                <td>${produto.quantidadeMinima} / ${produto.quantidadeMaxima}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${produto.quantidade < produto.quantidadeMinima}">
                                            <span class="badge badge-danger">Baixo</span>
                                        </c:when>
                                        <c:when test="${produto.quantidade > produto.quantidadeMaxima}">
                                            <span class="badge badge-warning">Alto</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge badge-success">Normal</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="actions">
                                    <a href="produtos?action=editar&id=${produto.id}" class="btn btn-sm btn-edit">Editar</a>
                                    <a href="produtos?action=excluir&id=${produto.id}" 
                                       class="btn btn-sm btn-delete" 
                                       onclick="return confirm('Deseja realmente excluir este produto?')">Excluir</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </main>
    </div>
</body>
</html>
