<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${tipoRelatorio} - Sistema de Estoque</title>
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
            <h1>${tipoRelatorio}</h1>
            <nav>
                <a href="relatorios" class="btn btn-secondary">← Voltar</a>
                <button onclick="window.print()" class="btn btn-primary">🖨️ Imprimir</button>
            </nav>
        </header>

        <div class="relatorio-header">
            <h2>${tipoRelatorio}</h2>
            <p>Data: <fmt:formatDate value="<%= new java.util.Date() %>" pattern="dd/MM/yyyy HH:mm"/></p>
        </div>

        <main>
            <c:choose>
                <c:when test="${empty listaProdutos}">
                    <div class="alert alert-info">
                        <p>✓ Nenhum produto encontrado para este relatório.</p>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="table-container">
                        <table class="data-table relatorio-table">
                            <thead>
                                <tr>
                                    <th>Código</th>
                                    <th>Produto</th>
                                    <th>Categoria</th>
                                    <th>Estoque Atual</th>
                                    <th>Estoque Mínimo</th>
                                    <th>Estoque Máximo</th>
                                    <th>Status</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="produto" items="${listaProdutos}">
                                    <tr>
                                        <td>${produto.id}</td>
                                        <td>${produto.nome}</td>
                                        <td>${produto.categoria.nome}</td>
                                        <td class="estoque-valor">${produto.quantidade}</td>
                                        <td>${produto.quantidadeMinima}</td>
                                        <td>${produto.quantidadeMaxima}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${produto.quantidade < produto.quantidadeMinima}">
                                                    <span class="badge badge-danger">Abaixo do Mínimo</span>
                                                </c:when>
                                                <c:when test="${produto.quantidade > produto.quantidadeMaxima}">
                                                    <span class="badge badge-warning">Acima do Máximo</span>
                                                </c:when>
                                            </c:choose>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:otherwise>
            </c:choose>
        </main>

        <footer class="relatorio-footer">
            <p>Total de produtos: ${listaProdutos.size()}</p>
            <p>Sistema de Controle de Estoque</p>
        </footer>
    </div>
</body>
</html>
