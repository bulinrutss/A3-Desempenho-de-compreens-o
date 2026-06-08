<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Relatórios - Sistema de Estoque</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>Relatórios Gerenciais</h1>
            <nav>
                <a href="index.jsp" class="btn btn-secondary">← Voltar ao Menu</a>
            </nav>
        </header>

        <main class="relatorios-menu">
            <div class="relatorio-grid">
                <a href="relatorios?action=lista-precos" class="relatorio-card">
                    <div class="icon">💰</div>
                    <h2>Lista de Preços</h2>
                    <p>Consulte todos os produtos e seus preços</p>
                </a>

                <a href="relatorios?action=abaixo-minimo" class="relatorio-card alert">
                    <div class="icon">⚠️</div>
                    <h2>Produtos Abaixo do Mínimo</h2>
                    <p>Produtos com estoque abaixo do mínimo</p>
                </a>

                <a href="relatorios?action=acima-maximo" class="relatorio-card warning">
                    <div class="icon">📦</div>
                    <h2>Produtos Acima do Máximo</h2>
                    <p>Produtos com estoque acima do máximo</p>
                </a>
            </div>
        </main>
    </div>
</body>
</html>
