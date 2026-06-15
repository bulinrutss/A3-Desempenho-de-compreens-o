# Evolução do Quality Gate — SonarCloud

Documento para apresentação da A3: comparação entre a **primeira análise** do projeto no SonarCloud e o estado **atual**, com os problemas identificados e as correções realizadas.

**Painel público:** https://sonarcloud.io/project/overview?id=bulinrutss_A3-Desempenho-de-compreens-o

---

## Contexto

O sistema legado foi importado no SonarCloud em **08/06/2026**. Como o trabalho foi consolidado e enviado em poucos ciclos (repositório único, entregas concentradas), este registro usa as **análises históricas do SonarCloud** e o histórico de commits como referência da evolução.

| Análise | Data | Commit de referência | Observação |
|---------|------|----------------------|------------|
| 1ª | 08/06/2026 | `abcdac73` | Primeira varredura do código legado |
| 2ª | 11/06/2026 | `abcdac73` | Inclusão de relatório JaCoCo na análise |
| Atual | 15/06/2026 | `f9551bdf` | Suite de testes ampliada + CI integrada |

---

## Métricas — antes e depois

| Métrica | 1ª análise (08/06) | Atual (15/06) | Evolução |
|---------|-------------------|---------------|----------|
| **Quality Gate** | OK | **ERROR** | Piorou* |
| **Cobertura de testes** | — | **81,9%** | Meta A3 (≥ 75%) atingida |
| **Bugs** | 16 | 24 | +8† |
| **Vulnerabilidades** | 21 | 21 | Estável |
| **Code smells** | 56 | 51 | −5 |
| **Duplicação** | — | 16,2% | Medida na análise atual |
| **Manutenibilidade (rating)** | — | A (1,0) | Boa |
| **Segurança (rating)** | — | E (5,0) | Crítico no legado |
| **Confiabilidade (rating)** | — | E (5,0) | Crítico no legado |

\* O quality gate do SonarCloud avalia principalmente o **código novo** em relação à versão anterior. Com a entrada massiva de testes e código analisado em 15/06, a condição **new_reliability_rating** passou a falhar (nota 5 — bugs no código novo).

† O aumento de bugs contabilizados ocorre porque a análise passou a cobrir **mais arquivos** (controllers, REST, testes) e o relatório JaCoCo passou a ser enviado à CI.

---

## Quality Gate — situação atual

| Condição | Status | Detalhe |
|----------|--------|---------|
| Confiabilidade (código novo) | **Falhou** | `new_reliability_rating` = 5 |
| Segurança (código novo) | OK | — |
| Manutenibilidade (código novo) | OK | — |
| Duplicação (código novo) | OK | 0% no período |
| Security hotspots revisados | OK | 100% |

**Conclusão para apresentação:** a pipeline CI executa testes, gera cobertura e **envia a análise ao SonarCloud**; o gate ainda não passa por bugs de confiabilidade no código novo, embora a **cobertura** e parte dos smells tenham melhorado.

---

## Problemas apontados na 1ª análise (resumo)

Na primeira varredura (08/06), o SonarCloud identificou, entre outros:

### Código Java (legado)

| Regra / tema | Qtd. aprox. | Descrição |
|--------------|-------------|-----------|
| `java:S2095` | 18 | `PreparedStatement` / `Statement` sem try-with-resources |
| `java:S1989` | 19 | Exceções de Servlet/IO não tratadas nos controllers web |
| `java:S1192` | 25 | Strings duplicadas (deveriam ser constantes) |
| `java:S2226` | 6 | Campos mutáveis em Servlets (não thread-safe) |
| `java:S8694` | 8 | Uso de `int` em vez de `Month` em datas |
| `java:S5122` | 1 | CORS habilitado — revisão de segurança necessária |

### Interface web (JSP, CSS, JS)

| Regra / tema | Qtd. | Descrição |
|--------------|------|-----------|
| `Web:S5254` | 10 | Falta de `lang` em `<html>` (acessibilidade) |
| `css:S7924` | 5 | Contraste insuficiente texto/fundo |
| `javascript:S7773` | 5 | Uso de `parseInt`/`parseFloat`/`isNaN` globais |

### Vulnerabilidades e bugs

- **21 vulnerabilidades** no código legado (principalmente web e DAOs).
- **16 bugs** na 1ª análise, subindo para **24** após ampliação do escopo analisado.

---

## Correções realizadas desde a 1ª análise

### 1. Qualidade e testes (impacto principal)

| Ação | Responsável | Resultado |
|------|-------------|-----------|
| Suite de testes unitários (modelo, DAO, Servlets, REST) | Grupo | **83 testes** na CI |
| Relatório JaCoCo integrado à CI | João Vitor | Cobertura **59,7% → 81,9%** |
| Exclusão de pacotes não testáveis (`visao/`, `Conexao.java`) | `sonar-project.properties` | Foco da cobertura na lógica testável |

### 2. Issues fechadas no SonarCloud (20 registros)

O SonarCloud marcou como **resolvidas** issues de interface web:

- **10×** `Web:S5254` — atributo `lang` em páginas JSP
- **5×** `css:S7924` — contraste de cores no `style.css`
- **5×** `javascript:S7773` — APIs numéricas modernas no `validation.js`

### 3. Pipeline CI/CD e SonarCloud

| Problema inicial | Correção |
|------------------|----------|
| CI com Java 8 + Mockito 5 (incompatível) | JDK **17** no workflow |
| Scanner apontando para `localhost:9000` | URL e credenciais SonarCloud explícitas no `ci.yml` |
| Análise não atualizava no painel | Secret `SONAR_TOKEN` + reexecução da pipeline |
| Artefatos de build versionados (`web-app/target/`) | Removidos do Git; `.gitignore` reforçado |

### 4. Code smells

- Redução de **56 → 51** code smells entre a 1ª análise e a atual.

---

## Problemas ainda abertos (backlog)

Principais categorias **não corrigidas** (código legado — esforço alto / risco de regressão):

| Prioridade | Tema | Exemplo de regra | Qtd. |
|------------|------|------------------|------|
| Alta | Vazamento de recursos JDBC | `java:S2095` | 18 |
| Alta | Tratamento de exceções em Servlets | `java:S1989` | 19 |
| Média | Strings duplicadas | `java:S1192` | 25 |
| Média | Campos mutáveis em Servlets | `java:S2226` | 6 |
| Média | Teste sem asserção | `java:S2699` | 1 |
| Baixa | Constantes de mês | `java:S8694` | 8 |

**Total de issues abertas:** 96 (última análise em 15/06/2026).

---

## Linha do tempo (apresentação)

```
08/06  ──► 1ª análise SonarCloud (legado, gate OK, sem cobertura)
11/06  ──► JaCoCo na análise (59,7% cobertura, gate ERROR)
15/06  ──► Testes ampliados + CI estável (81,9% cobertura)
         ──► 20 issues web fechadas; smells −5
         ──► Gate ainda falha em confiabilidade do código novo
```

---

## Referências

- [SonarCloud — Overview do projeto](https://sonarcloud.io/project/overview?id=bulinrutss_A3-Desempenho-de-compreens-o)
- [SonarCloud — Issues](https://sonarcloud.io/project/issues?id=bulinrutss_A3-Desempenho-de-compreens-o)
- [GitHub Actions — CI](https://github.com/bulinrutss/A3-Desempenho-de-compreens-o/actions)
- Configuração local: `sonar-project.properties`, `.github/workflows/ci.yml`

---

*Última atualização: 15/06/2026 — métricas extraídas do SonarCloud (API pública).*
