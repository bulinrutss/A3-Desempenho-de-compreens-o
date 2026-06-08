<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sistema de Controle de Estoque</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>Sistema de Controle de Estoque</h1>
            <p class="subtitle">Gestão Completa de Produtos e Movimentações</p>
        </header>

        <main class="menu-principal">
            <div class="menu-grid">
                <a href="categorias" class="menu-card">
                    <div class="icon">📦</div>
                    <h2>Categorias</h2>
                    <p>Gerenciar categorias de produtos</p>
                </a>

                <a href="produtos" class="menu-card">
                    <div class="icon">🏷️</div>
                    <h2>Produtos</h2>
                    <p>Cadastrar e gerenciar produtos</p>
                </a>

                <a href="movimentacoes" class="menu-card">
                    <div class="icon">📊</div>
                    <h2>Movimentações</h2>
                    <p>Registrar entradas e saídas</p>
                </a>

                <a href="relatorios" class="menu-card">
                    <div class="icon">📈</div>
                    <h2>Relatórios</h2>
                    <p>Consultar relatórios gerenciais</p>
                </a>
            </div>
        </main>

        <footer>
            <p>&copy; 2025 A3 Sistema de Controle de Estoque</p>
        </footer>
    </div>
</body>
</html>
