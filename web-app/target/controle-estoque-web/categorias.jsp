<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Categorias - Sistema de Estoque</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>Gerenciamento de Categorias</h1>
            <nav>
                <a href="index.jsp" class="btn btn-secondary">← Voltar ao Menu</a>
                <a href="categorias?action=novo" class="btn btn-primary">+ Nova Categoria</a>
            </nav>
        </header>

        <main>
            <div class="table-container">
                <table class="data-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nome</th>
                            <th>Tamanho</th>
                            <th>Embalagem</th>
                            <th>Ações</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="categoria" items="${listaCategorias}">
                            <tr>
                                <td>${categoria.id}</td>
                                <td>${categoria.nome}</td>
                                <td>${categoria.tamanho}</td>
                                <td>${categoria.embalagem}</td>
                                <td class="actions">
                                    <a href="categorias?action=editar&id=${categoria.id}" class="btn btn-sm btn-edit">Editar</a>
                                    <a href="categorias?action=excluir&id=${categoria.id}" 
                                       class="btn btn-sm btn-delete" 
                                       onclick="return confirm('Deseja realmente excluir esta categoria?')">Excluir</a>
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
