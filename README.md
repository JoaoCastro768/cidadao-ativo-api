> **FLUXO RECOMENDADO PARA ESTA ENTREGA:** não é necessário instalar Docker no Windows. Veja o arquivo [`GUIA_SEM_DOCKER_LOCAL.md`](GUIA_SEM_DOCKER_LOCAL.md) para publicar pelo GitHub Actions, Docker Hub e Azure. A seção de Docker local abaixo é opcional.

# Cidadão Ativo API — Global Solution FIAP 2026.2

**Desenvolvedor:** João Vitor Pedreira de Castro — **RM 554628** — Turma **3SIR**  
**Disciplina:** Microservice and Web Engineering & IT Services  
**Professor:** Antonio Carlos de Lima Júnior  
**Tema:** CitizenTech — soluções centradas no cidadão, alinhadas aos ODS 11 e 16.

## 1. Proposta

O **Cidadão Ativo** é um protótipo acadêmico para registrar situações do bairro (iluminação pública, vias, saneamento, limpeza e acessibilidade) e acompanhar sua evolução por meio de atualizações. O cidadão pode relacionar a ocorrência a um bairro, registrar observações e acompanhar seu status. O objetivo é apoiar a organização de informações locais e a participação social.

> **Limitação importante:** esta API NÃO envia reclamações à prefeitura ou a órgãos públicos. Não possui autenticação; deve ser utilizada com dados fictícios de demonstração, nunca informações pessoais verdadeiras.

### Fluxo de dados / arquitetura

```text
Cliente HTTP / Swagger
     |
     v
Controller (@RestController, /api/v1/...) -> Request DTO + Bean Validation
     |
     v
Service (@Service, @Transactional) -> regras, relacionamento, not found
     |
     v
Repository (Spring Data JPA) -> persistência
     |
     v
Microsoft SQL Server
     |
Entity JPA  -> ModelMapper -> Response DTO -> JSON / HTTP
```

Camadas separadas em `controller/`, `service/`, `repository/`, `entity/`, `dto/`, `domain/`, `config/` e `exception/`. Controllers não acessam Repositories. O mapeamento entre classes usa **ModelMapper**. As entidades JPA não são expostas diretamente nos payloads.

## 2. Modelo relacional — quatro entidades

| Tabela | Campos principais (>=5) | Relações |
|---|---|---|
| `cidadaos` | id, nome (obrig.), email (obrig.), telefone, ativo, criadoEm | 1:N para ocorrencias |
| `bairros` | id, nome (obrig.), cidade (obrig.), uf (obrig.), cepReferencia, ativo, criadoEm | 1:N para ocorrencias |
| `ocorrencias` | id, titulo (obrig.), descricao (obrig.), categoria, status, criadoEm, atualizadoEm, cidadao_id, bairro_id | N:1 cidadaos; N:1 bairros; 1:N atualizacoes |
| `atualizacoes` | id, descricao (obrig.), autor (obrig.), tipo, anexoUrl, concluida, criadoEm, ocorrencia_id | N:1 ocorrencias |

As tabelas são nomeadas no plural e as relações utilizam chaves estrangeiras. O SQL Server mantém as restrições definidas pelas entidades.

## 3. Tecnologias

Java 17, Spring Boot 3.5.8, Spring Web MVC, Spring Data JPA/Hibernate, Jakarta Validation, Microsoft SQL Server, ModelMapper, springdoc OpenAPI/Swagger, Maven, Docker (multi-stage), Docker Hub, GitHub Actions e Microsoft Azure Container Apps (ou App Service for Containers).

## 4. Rodar localmente pelo Docker Desktop (recomendado)

**Pré-requisitos:** Docker Desktop aberto, com Docker Compose e virtualização habilitada; mínimo recomendado de 4 GB livres para contêineres (SQL Server requer memória). Conexão com a internet na primeira execução.

Abra um terminal na raiz deste projeto:

**PowerShell:**

```powershell
Copy-Item .env.example .env
notepad .env
```

No `.env`, substitua `MSSQL_SA_PASSWORD` por uma senha forte, com letras maiúsculas, minúsculas, números e símbolos, sem compartilhá-la nem adicioná-la ao Git. Depois:

```powershell
docker compose up --build -d
docker compose ps
docker compose logs --tail=100 api
```

O Compose inicia SQL Server, cria a base `cidadao_ativo` e sobe a API. Aguarde o `sqlserver-init` concluir com exit code 0. A URL da documentação será:

- Swagger: **http://localhost:8080/** (redireciona para `/swagger-ui/index.html`)
- Especificação OpenAPI: **http://localhost:8080/v3/api-docs**
- Health check: **http://localhost:8080/actuator/health**

Para parar: `docker compose down`. Não utilize `docker compose down -v` se quiser manter o banco.

**Alternativa Maven:** execute apenas o serviço `sqlserver` e o `sqlserver-init` com `docker compose up -d sqlserver sqlserver-init`, configure `DB_PASSWORD` no PowerShell e rode `mvn spring-boot:run`, com `DB_URL` apontando para `localhost:1433`. O Java deve estar instalado (17 ou superior).

## 5. Endpoints v1 (20 operações CRUD)

| Entidade | Criar | Listar | Buscar ID | Alterar | Excluir |
|---|---|---|---|---|---|
| Cidadãos | POST `/api/v1/cidadaos` | GET `/api/v1/cidadaos` | GET `/api/v1/cidadaos/{id}` | PUT `/api/v1/cidadaos/{id}` | DELETE `/api/v1/cidadaos/{id}` |
| Bairros | POST `/api/v1/bairros` | GET `/api/v1/bairros` | GET `/api/v1/bairros/{id}` | PUT `/api/v1/bairros/{id}` | DELETE `/api/v1/bairros/{id}` |
| Ocorrências | POST `/api/v1/ocorrencias` | GET `/api/v1/ocorrencias` | GET `/api/v1/ocorrencias/{id}` | PUT `/api/v1/ocorrencias/{id}` | DELETE `/api/v1/ocorrencias/{id}` |
| Atualizações | POST `/api/v1/atualizacoes` | GET `/api/v1/atualizacoes` | GET `/api/v1/atualizacoes/{id}` | PUT `/api/v1/atualizacoes/{id}` | DELETE `/api/v1/atualizacoes/{id}` |

**HTTP:** `201 Created` (+ `Location`) em POST, `200 OK` em GET/PUT, `204 No Content` em DELETE, `400 Bad Request` para JSON/validação inválida, `404 Not Found` em ID ausente, `409 Conflict` em duplicidade ou vínculo que impeça exclusão e `500` genérico para erros inesperados (sem stack trace nem credenciais).

### Exemplos de uso (nesta ordem)

```http
POST http://localhost:8080/api/v1/cidadaos
Content-Type: application/json

{"nome":"Maria Exemplo","email":"maria@example.org","telefone":"11999990000","ativo":true}
```

```http
POST http://localhost:8080/api/v1/bairros
Content-Type: application/json

{"nome":"Mooca","cidade":"São Paulo","uf":"SP","cepReferencia":"03122000","ativo":true}
```

```http
POST http://localhost:8080/api/v1/ocorrencias
Content-Type: application/json

{"titulo":"Iluminação apagada","descricao":"Poste sem luz na praça (registro de demonstração)","categoria":"ILUMINACAO","status":"ABERTA","cidadaoId":1,"bairroId":1}
```

```http
POST http://localhost:8080/api/v1/atualizacoes
Content-Type: application/json

{"descricao":"Solicitação registrada no protótipo","autor":"Maria Exemplo","tipo":"OBSERVACAO","concluida":false,"ocorrenciaId":1}
```

Os IDs acima supõem uma base recém-criada. Em outras bases, consulte o ID retornado em cada POST. Também há testes via PowerShell em `scripts/TESTAR_API.ps1`.

**Categorias:** `ILUMINACAO`, `VIAS_PUBLICAS`, `LIMPEZA`, `ACESSIBILIDADE`, `SANEAMENTO`, `OUTROS`.  
**Status:** `ABERTA`, `EM_ANDAMENTO`, `CONCLUIDA`.  
**Tipos de atualização:** `OBSERVACAO`, `MUDANCA_STATUS`, `COMPLEMENTO`.

## 6. Testes e validação

```powershell
mvn -B clean verify
```

Testes unitários verificam criação, conflito de e-mail, listagem, relacionamento e recurso inexistente. Faça também testes de requisições reais no Swagger e confira os registros no SQL Server. Para facilitar:

```powershell
powershell -NoProfile -ExecutionPolicy Bypass -File .\scripts\TESTAR_API.ps1
```

Não trate o build estático como evidência suficiente de persistência: é preciso comprovar `POST`/`GET`, reinicialização da API e leitura do mesmo registro no banco.

## 7. Docker Hub e GitHub Actions (CD obrigatório)

**Imagem pretendida (somente após publicação):** `docker.io/joaocastro768/cidadao-ativo-api:latest` — confirme no Docker Hub. Não presuma que ela existe antes do workflow terminar com sucesso.

1. Crie repositório público no GitHub chamado `cidadao-ativo-api` e envie TODO o projeto, incluindo `.github/workflows/dockerhub-cd.yml`.
2. Crie repositório público no Docker Hub chamado `cidadao-ativo-api` (na conta correta).
3. No GitHub, em **Settings → Secrets and variables → Actions**, adicione:
   - `DOCKERHUB_USERNAME` = usuário real do Docker Hub
   - `DOCKERHUB_TOKEN` = token de acesso criado no Docker Hub (não a senha da conta)
4. Faça um novo push na branch **main**. O workflow executa Maven, testes, Docker build e publicação de `latest` e `sha-...`.
5. Abra **GitHub → Actions**, confira o job `build-test-publish` verde e acesse o Docker Hub para verificar as tags publicadas.

O **deploy automático para o Azure NÃO é exigido**: o deploy no Azure é obrigatório, mas pode ser manual. Os segredos do Azure/SQL Server devem ficar fora do repositório.

## 8. Azure + Microsoft SQL Server

A publicação real depende da conta Azure e das permissões de cobrança/recursos. Caminho recomendado:

1. Crie um Azure SQL Database (motor Microsoft SQL Server) `cidadao_ativo` em um servidor SQL lógico, ou use outra instância Microsoft SQL Server acessível pela API no Azure.
2. Configure a rede e firewall do banco para aceitar somente as origens necessárias da aplicação (ex.: saída do Container Apps). O SQL deve aceitar conexões TCP 1433 quando cabível; mantenha TLS.
3. Teste a conexão do SQL Server com uma ferramenta apropriada. **Não publique senha em prints ou GitHub.**
4. Crie um Azure Container Apps Environment e um Container App que utilize a imagem pública do Docker Hub publicada no passo 7.
5. Configure ingress **External** para porta alvo **8080** e variáveis de ambiente:

| Variável | Valor esperado |
|---|---|
| `DB_URL` | `jdbc:sqlserver://SEU-SERVIDOR.database.windows.net:1433;databaseName=cidadao_ativo;encrypt=true;trustServerCertificate=false;loginTimeout=30` |
| `DB_USER` | Login do SQL Server (sem colocar em código) |
| `DB_PASSWORD` | **Secret** da plataforma; referencie a variável via secretRef |
| `PORT` | `8080` |

6. Abra `https://SUA-URL-AZURE/`, verifique o Swagger, faça CRUD e confirme a persistência no Azure SQL Server. Se a URL não estiver acessível, o professor não consegue testar a implantação.

> **Custo:** Azure Container Apps e SQL Database podem cobrar pelo uso. Monitore o orçamento e exclua recursos quando a avaliação terminar. O uso público sem autenticação é somente para demonstração fictícia; não hospede dados reais.

## 9. Evidências para avaliação

- GitHub público com fonte completo + workflow após push;
- Docker Hub com imagem/tags publicadas;
- Histórico do GitHub Actions com execução `success` posterior a um push;
- URL Azure pública com Swagger acessível na raiz;
- Teste de CRUD no Azure + registro correspondente persistido no SQL Server;
- README com execução, conexão SQL, imagens e instruções acima.

**Entrega solicitada pelo professor:** **somente um `.TXT`** no Portal FIAP, contendo nome e RM, GitHub, API Azure e Docker Hub. O modelo está em `ENTREGA_PROFESSOR_PREENCHER.txt`; substitua os campos antes de enviar.

**Prazo no enunciado:** **16/11/2026, 23h55 (Brasília)**. **Nota específica:** máximo 8,0 pontos.
