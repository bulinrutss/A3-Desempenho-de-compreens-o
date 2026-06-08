@echo off
title Sistema de Controle de Estoque
echo ========================================
echo   SISTEMA DE CONTROLE DE ESTOQUE
echo ========================================
echo.
echo Iniciando aplicacao...
echo.

REM Verificar se o JAR existe
if not exist "target\ControleEstoque-1.0-jar-with-dependencies.jar" (
    echo [ERRO] Arquivo JAR nao encontrado!
    echo.
    echo Execute primeiro: mvn clean install
    echo.
    pause
    exit
)

REM Executar a aplicacao
java -jar target\ControleEstoque-1.0-jar-with-dependencies.jar

REM Se houver erro
if errorlevel 1 (
    echo.
    echo [ERRO] Falha ao iniciar a aplicacao!
    echo Verifique se o Java esta instalado corretamente.
    pause
)
