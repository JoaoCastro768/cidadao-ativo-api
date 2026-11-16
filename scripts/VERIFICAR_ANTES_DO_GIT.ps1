$ErrorActionPreference = "Stop"
if (-not (Get-Command git -ErrorAction SilentlyContinue)) { throw "Git não encontrado. Instale Git for Windows." }
if (-not (Test-Path "pom.xml")) { throw "Abra o PowerShell na pasta que contém pom.xml." }
if (-not (Test-Path ".github/workflows/dockerhub-cd.yml")) { throw "Workflow GitHub Actions ausente." }
if (Test-Path ".env") { Write-Warning "Existe .env; ele não deve ser versionado. Confira git status após git add." }
Write-Host "Projeto preparado para envio pelo GitHub, sem Docker no Windows." -ForegroundColor Green
Write-Host "Comandos e links: GUIA_SEM_DOCKER_LOCAL.md"
