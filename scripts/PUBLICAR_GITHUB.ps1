param([string]$RepoUrl = 'https://github.com/JoaoCastro768/cidadao-ativo-api.git')
$ErrorActionPreference = 'Stop'
if (-not (Get-Command git -ErrorAction SilentlyContinue)) { throw 'Git nao encontrado no PATH.' }
if (-not (Test-Path 'pom.xml')) { throw 'Entre na raiz do projeto, onde fica pom.xml.' }
git init
git branch -M main
git add .
git commit -m 'Global Solution 2026.2 - Cidadao Ativo API'
$already = git remote get-url origin 2>$null
if ($LASTEXITCODE -eq 0) { git remote set-url origin $RepoUrl } else { git remote add origin $RepoUrl }
git push -u origin main
Write-Host 'Confirme no GitHub o workflow em Actions. Nao guarde senhas no repositorio.'
