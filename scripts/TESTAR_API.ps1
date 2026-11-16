param([string]$BaseUrl = "http://localhost:8080")
$ErrorActionPreference = "Stop"
$BaseUrl = $BaseUrl.TrimEnd('/')
function PostJson([string]$uri, [object]$obj) {
    $body = $obj | ConvertTo-Json -Depth 12 -Compress
    return Invoke-RestMethod -Method Post -Uri "$BaseUrl$uri" -ContentType 'application/json; charset=utf-8' -Body ([Text.Encoding]::UTF8.GetBytes($body))
}
function PutJson([string]$uri, [object]$obj) {
    $body = $obj | ConvertTo-Json -Depth 12 -Compress
    return Invoke-RestMethod -Method Put -Uri "$BaseUrl$uri" -ContentType 'application/json; charset=utf-8' -Body ([Text.Encoding]::UTF8.GetBytes($body))
}
Write-Host "Verificando API em $BaseUrl" -ForegroundColor Cyan
Invoke-RestMethod -Uri "$BaseUrl/actuator/health" | ConvertTo-Json
$email = "teste.$([guid]::NewGuid().ToString('N').Substring(0,8))@example.org"
$cid = PostJson '/api/v1/cidadaos' @{ nome='Pessoa Exemplo'; email=$email; telefone='11999990000'; ativo=$true }
$bairro = PostJson '/api/v1/bairros' @{ nome='Mooca'; cidade='Sao Paulo'; uf='SP'; cepReferencia='03122000'; ativo=$true }
$occ = PostJson '/api/v1/ocorrencias' @{ titulo='Poste apagado'; descricao='Registro de exemplo criado por teste PowerShell'; categoria='ILUMINACAO'; status='ABERTA'; cidadaoId=$cid.id; bairroId=$bairro.id }
$upd = PostJson '/api/v1/atualizacoes' @{ descricao='Registro cadastrado'; autor='Pessoa Exemplo'; tipo='OBSERVACAO'; concluida=$false; ocorrenciaId=$occ.id }
$oc2 = Invoke-RestMethod -Uri "$BaseUrl/api/v1/ocorrencias/$($occ.id)"
if ($oc2.titulo -ne 'Poste apagado') { throw 'Falha de persistencia' }
$oc3 = PutJson "/api/v1/ocorrencias/$($occ.id)" @{ titulo='Poste apagado'; descricao='Registro atualizado'; categoria='ILUMINACAO'; status='CONCLUIDA'; cidadaoId=$cid.id; bairroId=$bairro.id }
if ($oc3.status -ne 'CONCLUIDA') { throw 'Falha no PUT' }
Write-Host "CRUD e relacionamento validados: cidadao=$($cid.id), bairro=$($bairro.id), ocorrencia=$($occ.id), atualizacao=$($upd.id)" -ForegroundColor Green
Write-Host "Observacao: mantenha registros para comprovacao de persistencia. Exclua pela API na ordem atualizacoes->ocorrencias->bairros->cidadaos se desejar." -ForegroundColor Yellow
