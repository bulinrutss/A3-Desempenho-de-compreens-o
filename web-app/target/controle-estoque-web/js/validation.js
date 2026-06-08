// Validação de formulários
document.addEventListener("DOMContentLoaded", function () {
  // Validação do formulário de produto
  const produtoForm = document.querySelector('form[action*="produtos"]');
  if (produtoForm) {
    produtoForm.addEventListener("submit", function (e) {
      const quantidade = parseInt(document.getElementById("quantidade").value);
      const quantidadeMinima = parseInt(
        document.getElementById("quantidadeMinima").value
      );
      const quantidadeMaxima = parseInt(
        document.getElementById("quantidadeMaxima").value
      );

      if (quantidadeMinima >= quantidadeMaxima) {
        e.preventDefault();
        alert("A quantidade mínima deve ser menor que a quantidade máxima!");
        return false;
      }

      if (quantidade < 0) {
        e.preventDefault();
        alert("A quantidade não pode ser negativa!");
        return false;
      }
    });
  }

  // Formatação automática de preço
  const precoInput = document.getElementById("precoUnitario");
  if (precoInput) {
    precoInput.addEventListener("blur", function () {
      const valor = parseFloat(this.value);
      if (!isNaN(valor)) {
        this.value = valor.toFixed(2);
      }
    });
  }

  // Confirmação de exclusão com detalhes
  const deleteLinks = document.querySelectorAll(".btn-delete");
  deleteLinks.forEach((link) => {
    link.addEventListener("click", function (e) {
      const confirmacao = confirm(
        "⚠️ ATENÇÃO!\n\nEsta ação não pode ser desfeita.\nDeseja realmente excluir este registro?"
      );
      if (!confirmacao) {
        e.preventDefault();
        return false;
      }
    });
  });
});

// Função para destacar linhas da tabela com estoque crítico
function highlightCriticalStock() {
  const rows = document.querySelectorAll(".data-table tbody tr");
  rows.forEach((row) => {
    const badge = row.querySelector(".badge-danger");
    if (badge) {
      row.style.backgroundColor = "#fff5f5";
    }
  });
}

// Executar ao carregar
if (document.readyState === "loading") {
  document.addEventListener("DOMContentLoaded", highlightCriticalStock);
} else {
  highlightCriticalStock();
}
