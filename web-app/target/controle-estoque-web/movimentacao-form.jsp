<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nova Movimentação - Sistema de Estoque</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>Registrar Movimentação</h1>
            <nav>
                <a href="movimentacoes" class="btn btn-secondary">← Voltar</a>
            </nav>
        </header>

        <main>
            <div class="form-container">
                <form action="movimentacoes" method="post">
                    <div class="form-group">
                        <label for="tipo">Tipo de Movimentação *</label>
                        <select id="tipo" name="tipo" required onchange="updateTipoStyle(this)">
                            <option value="">Selecione...</option>
                            <option value="ENTRADA">ENTRADA (+)</option>
                            <option value="SAIDA">SAÍDA (-)</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="produtoId">Produto *</label>
                        <select id="produtoId" name="produtoId" required onchange="showProdutoInfo(this)">
                            <option value="">Selecione...</option>
                            <c:forEach var="produto" items="${listaProdutos}">
                                <option value="${produto.id}" 
                                        data-estoque="${produto.quantidade}"
                                        data-nome="${produto.nome}"
                                        data-categoria="${produto.categoria.nome}">
                                    ${produto.nome} (${produto.categoria.nome}) - Estoque: ${produto.quantidade}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div id="produto-info" class="info-box" style="display:none;">
                        <p><strong>Produto:</strong> <span id="info-nome"></span></p>
                        <p><strong>Categoria:</strong> <span id="info-categoria"></span></p>
                        <p><strong>Estoque Atual:</strong> <span id="info-estoque"></span></p>
                    </div>

                    <div class="form-group">
                        <label for="quantidade">Quantidade *</label>
                        <input type="number" id="quantidade" name="quantidade" 
                               min="1" required>
                        <small id="estoque-warning" class="warning-text" style="display:none;">
                            ⚠️ Atenção: Quantidade maior que o estoque disponível!
                        </small>
                    </div>

                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">Registrar</button>
                        <a href="movimentacoes" class="btn btn-secondary">Cancelar</a>
                    </div>
                </form>
            </div>
        </main>
    </div>

    <script>
        function showProdutoInfo(select) {
            const option = select.options[select.selectedIndex];
            if (option.value) {
                document.getElementById('info-nome').textContent = option.getAttribute('data-nome');
                document.getElementById('info-categoria').textContent = option.getAttribute('data-categoria');
                document.getElementById('info-estoque').textContent = option.getAttribute('data-estoque');
                document.getElementById('produto-info').style.display = 'block';
            } else {
                document.getElementById('produto-info').style.display = 'none';
            }
        }

        function updateTipoStyle(select) {
            select.className = select.value === 'ENTRADA' ? 'tipo-entrada' : 
                              select.value === 'SAIDA' ? 'tipo-saida' : '';
        }

        document.getElementById('quantidade').addEventListener('input', function() {
            const tipo = document.getElementById('tipo').value;
            const produtoSelect = document.getElementById('produtoId');
            const option = produtoSelect.options[produtoSelect.selectedIndex];
            
            if (tipo === 'SAIDA' && option.value) {
                const estoque = parseInt(option.getAttribute('data-estoque'));
                const quantidade = parseInt(this.value);
                const warning = document.getElementById('estoque-warning');
                
                if (quantidade > estoque) {
                    warning.style.display = 'block';
                } else {
                    warning.style.display = 'none';
                }
            }
        });
    </script>
</body>
</html>
