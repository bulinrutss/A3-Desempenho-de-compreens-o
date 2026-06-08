<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${produto != null ? 'Editar' : 'Novo'} Produto - Sistema de Estoque</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>${produto != null ? 'Editar' : 'Novo'} Produto</h1>
            <nav>
                <a href="produtos" class="btn btn-secondary">← Voltar</a>
            </nav>
        </header>

        <main>
            <div class="form-container">
                <form action="produtos" method="post">
                    <input type="hidden" name="action" value="${produto != null ? 'atualizar' : 'salvar'}">
                    <c:if test="${produto != null}">
                        <input type="hidden" name="id" value="${produto.id}">
                    </c:if>

                    <div class="form-row">
                        <div class="form-group">
                            <label for="nome">Nome do Produto *</label>
                            <input type="text" id="nome" name="nome" 
                                   value="${produto != null ? produto.nome : ''}" 
                                   required maxlength="100">
                        </div>

                        <div class="form-group">
                            <label for="categoriaId">Categoria *</label>
                            <select id="categoriaId" name="categoriaId" required>
                                <option value="">Selecione...</option>
                                <c:forEach var="cat" items="${listaCategorias}">
                                    <option value="${cat.id}" 
                                            ${produto != null && produto.categoria.id == cat.id ? 'selected' : ''}>
                                        ${cat.nome}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label for="precoUnitario">Preço Unitário (R$) *</label>
                            <input type="number" id="precoUnitario" name="precoUnitario" 
                                   value="${produto != null ? produto.precoUnitario : ''}" 
                                   step="0.01" min="0" required>
                        </div>

                        <div class="form-group">
                            <label for="unidade">Unidade *</label>
                            <select id="unidade" name="unidade" required>
                                <option value="UN" ${produto != null && produto.unidade == 'UN' ? 'selected' : ''}>Unidade (UN)</option>
                                <option value="KG" ${produto != null && produto.unidade == 'KG' ? 'selected' : ''}>Quilograma (KG)</option>
                                <option value="LT" ${produto != null && produto.unidade == 'LT' ? 'selected' : ''}>Litro (LT)</option>
                                <option value="CX" ${produto != null && produto.unidade == 'CX' ? 'selected' : ''}>Caixa (CX)</option>
                                <option value="PC" ${produto != null && produto.unidade == 'PC' ? 'selected' : ''}>Pacote (PC)</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label for="quantidade">Quantidade Atual *</label>
                            <input type="number" id="quantidade" name="quantidade" 
                                   value="${produto != null ? produto.quantidade : 0}" 
                                   min="0" required>
                        </div>

                        <div class="form-group">
                            <label for="quantidadeMinima">Quantidade Mínima *</label>
                            <input type="number" id="quantidadeMinima" name="quantidadeMinima" 
                                   value="${produto != null ? produto.quantidadeMinima : 10}" 
                                   min="0" required>
                        </div>

                        <div class="form-group">
                            <label for="quantidadeMaxima">Quantidade Máxima *</label>
                            <input type="number" id="quantidadeMaxima" name="quantidadeMaxima" 
                                   value="${produto != null ? produto.quantidadeMaxima : 100}" 
                                   min="0" required>
                        </div>
                    </div>

                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">
                            ${produto != null ? 'Atualizar' : 'Cadastrar'}
                        </button>
                        <a href="produtos" class="btn btn-secondary">Cancelar</a>
                    </div>
                </form>
            </div>
        </main>
    </div>
    
    <script src="js/validation.js"></script>
</body>
</html>
