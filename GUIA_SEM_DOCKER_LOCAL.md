# GS FIAP 2026.2 — Microservice — Sem Docker instalado no Windows

**Aluno:** João Vitor Pedreira de Castro | **RM:** 554628 | **Turma:** 3SIR
**Entrega:** 16/11/2026, 23h55 | **Formato de entrega do professor:** TXT com os 3 links públicos reais.

## 0. O que é e o que NÃO é necessário

**Você NÃO precisa instalar Docker Desktop, SQL Server nem Maven no seu Windows.**
O GitHub Actions roda Maven e constrói a imagem Docker em um runner hospedado pelo GitHub.
O Docker Hub armazena a imagem pronta. Azure Container Apps executa a imagem e Azure SQL Database armazena os dados.

**Você precisa:** Git instalado no Windows, sua conta GitHub, sua conta Docker Hub, assinatura Azure ativa e navegador.
O deploy Azure e a criação de banco podem gerar custos: confirme os preços no portal antes de criar.

## 1. Docker Hub primeiro (antes do push)

1. Entre em https://hub.docker.com/ e crie um repositório **PUBLIC** chamado `cidadao-ativo-api`.
2. Confira seu **Docker ID**; não presuma que seja o mesmo nome do GitHub.
3. Abra configurações da conta -> Personal access tokens -> gere token com permissão de escrita no repositório.
4. Guarde o token em local privado; NÃO o cole no README, terminal, mensagens ou código.

## 2. Repositório GitHub, segredos e push

1. Abra https://github.com/new ; crie repositório PUBLIC `cidadao-ativo-api` na conta `JoaoCastro768`, SEM README inicial.
2. Entre no repositório > Settings > Secrets and variables > Actions > New repository secret.
3. Crie dois segredos com nomes **exatos**:
   - `DOCKERHUB_USERNAME` = seu Docker ID
   - `DOCKERHUB_TOKEN` = seu token de acesso Docker Hub
4. Extraia o ZIP desta entrega e abra o PowerShell na pasta `cidadao-ativo-api`, onde está o `pom.xml`.
5. Execute os comandos a seguir (NÃO execute o `docker info`):

```powershell
git --version
git init
git branch -M main
git add .
git status --short
git commit -m "GS 2026.2 - Cidadao Ativo API"
git remote add origin https://github.com/JoaoCastro768/cidadao-ativo-api.git
git push -u origin main
```

Se for seu primeiro commit, o Git pode solicitar `git config --global user.name` e `git config --global user.email`.
Se a pasta já tiver um remoto `origin`, use `git remote set-url origin https://github.com/JoaoCastro768/cidadao-ativo-api.git` em vez de `git remote add`.
**NÃO envie arquivos `.env`, senhas ou tokens!** A `.gitignore` do projeto ignora `.env`.

## 3. Esperar o GitHub Actions (sem Docker local)

1. No GitHub, acesse aba **Actions** -> `Compilar, testar e publicar no Docker Hub (sem Docker local)`.
2. Abra a execução ligada ao seu push. Veja o passo `Compilar e executar testes Java`, depois `Construir e publicar a imagem Docker na nuvem`.
3. Status verde `Success` significa que a compilação/testes e o push foram concluídos pelo workflow.
4. No Docker Hub verifique tags `latest` e `sha-...` e a data de push da imagem.
5. Caso dê erro, abra o passo vermelho, copie o texto do erro (sem credenciais) e corrija o código/workflow antes de prosseguir.

## 4. Banco SQL no Azure (na nuvem)

1. Abra https://portal.azure.com > `SQL databases` > `Create`.
2. Resource group: `rg-cidadao-ativo-gs`; nome do banco: `cidadao_ativo`.
3. Crie servidor lógico SQL com autenticação SQL. Guarde `server.database.windows.net`, usuário e senha; NÃO os publique.
4. Na aba Rede configure conectividade de entrada adequada ao Container App; prefira regras restritas / rede privada.
5. Confirme custos estimados e a criação. Se a oferta gratuita aparecer, confira seus limites/pausas.

## 5. API no Azure Container Apps

1. Portal Azure > `Container Apps` > `Create`.
2. Group: `rg-cidadao-ativo-gs`; imagem Docker pública `<SEU_DOCKER_ID>/cidadao-ativo-api:latest` (registry `docker.io`).
3. External ingress habilitado, target port `8080`, região próxima ao SQL.
4. Configure variáveis de ambiente no container:
   - `PORT` = `8080`
   - `DB_URL` = `jdbc:sqlserver://<SEU_SERVIDOR>.database.windows.net:1433;databaseName=cidadao_ativo;encrypt=true;trustServerCertificate=false;loginTimeout=30`
   - `DB_USER` = seu usuário SQL
   - `DB_PASSWORD` = referência a secret do Container App contendo a senha SQL
5. Se necessário, faça em duas etapas: crie o secret nas configurações do App e depois crie uma NOVA REVISION com a variável `DB_PASSWORD` baseada nesse secret.
6. Confirme a URL pública `https://...azurecontainerapps.io` e abra `https://...azurecontainerapps.io/` (Swagger).
7. Confira também `/actuator/health` e `/api/v1/bairros`.

## 6. Teste público sem Docker e evidências

No PowerShell, dentro do projeto:

```powershell
powershell -NoProfile -ExecutionPolicy Bypass -File .\scripts\TESTAR_API.ps1 -BaseUrl "https://SUA-URL-REAL-DO-AZURE"
```

A rotina cria dados fictícios de demonstração. Se executar com sucesso, copie o endereço do GitHub, o link do Docker Hub e URL Azure. **Mantenha a API acessível durante a correção do professor.**

## 7. Entrega FIAP (somente o TXT solicitado)

Edite `ENTREGA_PROFESSOR_PREENCHER.txt` e substitua os campos pendentes por links reais.
No Portal do Aluno -> Microservice and Web Engineering & IT Services -> Entrega de Trabalhos, envie o TXT; não envie o ZIP do código nesse campo, a menos que a instrução da atividade seja atualizada.

## Limitações da validação atual

O código e os arquivos de configuração foram inspecionados, mas **a imagem Docker e o deploy Azure NÃO foram executados nem publicados por este ambiente**.
Compilação Maven real e testes serão verificados pelo GitHub Actions após você publicar o repositório.
