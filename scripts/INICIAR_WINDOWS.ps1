$ErrorActionPreference = "Stop"
if (-not (Get-Command docker -ErrorAction SilentlyContinue)) {
    throw "Docker nao encontrado. Instale e inicie Docker Desktop para Windows."
}
$info = docker info 2>&1
if ($LASTEXITCODE -ne 0) { throw "Docker Engine indisponivel. Abra o Docker Desktop e habilite o WSL 2." }
if (-not (Test-Path '.env')) {
    Copy-Item '.env.example' '.env'
    Write-Host "Edite .env e defina MSSQL_SA_PASSWORD forte. Depois execute novamente." -ForegroundColor Yellow
    exit 1
}
$envVars = Get-Content .env -ErrorAction Stop
if (($envVars -join ' ') -match 'SUBSTITUA_POR_SENHA_FORTE') {
    throw "Altere MSSQL_SA_PASSWORD em .env antes de iniciar."
}
docker compose up --build -d
if ($LASTEXITCODE -ne 0) { throw "docker compose falhou. Execute docker compose logs para investigar." }
docker compose ps
Write-Host "Swagger (se o banco e API estiverem saudaveis): http://localhost:8080" -ForegroundColor Green
