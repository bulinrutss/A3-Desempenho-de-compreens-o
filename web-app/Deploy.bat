@echo off
chcp 65001 >nul
echo ====================================
echo  DEPLOY - Sistema de Estoque Web
echo ====================================
echo.
echo Este script irá compilar o projeto web
echo e preparar o arquivo WAR para deploy.
echo.
pause

echo.
echo [1/2] Compilando projeto com Maven...
call mvn clean package

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ❌ ERRO: Falha na compilação!
    echo Verifique os erros acima.
    pause
    exit /b 1
)

echo.
echo ✅ Compilação concluída com sucesso!
echo.
echo [2/2] Arquivo WAR criado em:
echo target\controle-estoque-web.war
echo.
echo ====================================
echo  PRÓXIMOS PASSOS PARA DEPLOY
echo ====================================
echo.
echo OPÇÃO 1 - Deploy Manual no Tomcat:
echo   1. Inicie o Apache Tomcat
echo   2. Copie o arquivo: target\controle-estoque-web.war
echo   3. Cole na pasta: C:\Program Files\Apache Tomcat\webapps\
echo   4. Aguarde o Tomcat fazer o deploy automático
echo   5. Acesse: http://localhost:8080/controle-estoque-web/
echo.
echo OPÇÃO 2 - Deploy via Tomcat Manager:
echo   1. Acesse: http://localhost:8080/manager/html
echo   2. Na seção "WAR file to deploy"
echo   3. Clique em "Escolher arquivo" e selecione o WAR
echo   4. Clique em "Deploy"
echo   5. Acesse: http://localhost:8080/controle-estoque-web/
echo.
echo OPÇÃO 3 - Deploy via IDE (Eclipse/IntelliJ):
echo   1. Importe o projeto web-app como Maven Project
echo   2. Configure o servidor Tomcat na IDE
echo   3. Clique com botão direito no projeto
echo   4. Escolha "Run As" ^> "Run on Server"
echo   5. Selecione Tomcat e clique em "Finish"
echo.
echo ====================================
echo  IMPORTANTE
echo ====================================
echo.
echo ⚠️ Certifique-se de que:
echo   • MySQL/XAMPP está rodando
echo   • Banco 'controle_estoque' existe
echo   • Apache Tomcat 9+ está instalado
echo   • Porta 8080 está disponível
echo.
pause
