<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Preços - Sistema de Estoque</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        @media print {
            .no-print { display: none; }
            body { background: white; }
        }
    </style>
</head>
<body>
    <div class="container">
        <header class="no-print">
            <h1>Lista de Preços</h1>
            <nav>
                <a href="relatorios" class="btn btn-secondary">← Voltar</a>
                <button onclick="window.print()" class="btn btn-primary">🖨️ Imprimir</button>
            </nav>
        </header>

        <div class="relatorio-header">
            <h2>Lista de Preços de Produtos</h2>
            <p>Data: <fmt:formatDate value="<%= new java.util.Date() %>" pattern="dd/MM/yyyy HH:mm"/></p>
        </div>

        <main>
            <div class="table-container">
                <table class="data-table relatorio-table">
                    <thead>
                        <tr>
                            <th>Código</th>
                            <th>Produto</th>
                            <th>Categoria</th>
                            <th>Unidade</th>
                            <th>Preço Unitário</th>
                            <th>Estoque</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="produto" items="${listaProdutos}">
                            <tr>
                                <td>${produto.id}</td>
                                <td>${produto.nome}</td>
                                <td>${produto.categoria.nome}</td>
                                <td>${produto.unidade}</td>
                                <td class="preco">R$ <fmt:formatNumber value="${produto.precoUnitario}" pattern="#,##0.00"/></td>
                                <td>${produto.quantidade}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </main>

        <footer class="relatorio-footer">
            <p>Total de produtos: ${listaProdutos.size()}</p>
            <p>Sistema de Controle de Estoque</p>
        </footer>
    </div>
</body>
</html>
