<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${categoria != null ? 'Editar' : 'Nova'} Categoria - Sistema de Estoque</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>${categoria != null ? 'Editar' : 'Nova'} Categoria</h1>
            <nav>
                <a href="categorias" class="btn btn-secondary">← Voltar</a>
            </nav>
        </header>

        <main>
            <div class="form-container">
                <form action="categorias" method="post">
                    <input type="hidden" name="action" value="${categoria != null ? 'atualizar' : 'salvar'}">
                    <c:if test="${categoria != null}">
                        <input type="hidden" name="id" value="${categoria.id}">
                    </c:if>

                    <div class="form-group">
                        <label for="nome">Nome da Categoria *</label>
                        <input type="text" id="nome" name="nome" 
                               value="${categoria != null ? categoria.nome : ''}" 
                               required maxlength="100">
                    </div>

                    <div class="form-group">
                        <label for="tamanho">Tamanho *</label>
                        <input type="text" id="tamanho" name="tamanho" 
                               value="${categoria != null ? categoria.tamanho : ''}" 
                               required maxlength="50">
                        <small>Ex: Pequeno, Médio, Grande</small>
                    </div>

                    <div class="form-group">
                        <label for="embalagem">Embalagem *</label>
                        <input type="text" id="embalagem" name="embalagem" 
                               value="${categoria != null ? categoria.embalagem : ''}" 
                               required maxlength="50">
                        <small>Ex: Plástico, Vidro, Papelão</small>
                    </div>

                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">
                            ${categoria != null ? 'Atualizar' : 'Cadastrar'}
                        </button>
                        <a href="categorias" class="btn btn-secondary">Cancelar</a>
                    </div>
                </form>
            </div>
        </main>
    </div>
</body>
</html>
