<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Movimentações - Sistema de Estoque</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>Histórico de Movimentações</h1>
            <nav>
                <a href="index.jsp" class="btn btn-secondary">← Voltar ao Menu</a>
                <a href="movimentacoes?action=novo" class="btn btn-primary">+ Nova Movimentação</a>
            </nav>
        </header>

        <main>
            <div class="table-container">
                <table class="data-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Data</th>
                            <th>Tipo</th>
                            <th>Produto</th>
                            <th>Categoria</th>
                            <th>Quantidade</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="mov" items="${listaMovimentacoes}">
                            <tr>
                                <td>${mov.id}</td>
                                <td>${mov.dataFormatada}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${mov.tipo == 'ENTRADA'}">
                                            <span class="badge badge-success">ENTRADA</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge badge-danger">SAÍDA</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${mov.produto.nome}</td>
                                <td>${mov.produto.categoria.nome}</td>
                                <td class="quantidade-valor">
                                    ${mov.tipo == 'ENTRADA' ? '+' : '-'}${mov.quantidade}
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
