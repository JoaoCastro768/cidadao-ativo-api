# Estado de validação do pacote

**Verificações concluídas neste ambiente:** integridade da estrutura Maven/XML e YAML, 4 Entities JPA com atributos e relacionamentos, 4 Controllers com 20 rotas CRUD, DTOs request/response, ModelMapper, Service e Repository, tratamento de erros, workflow `push` em `main`, Dockerfile e modelo de TXT.

**O que NÃO foi testado aqui:** `mvn clean verify`, Docker build, inicialização SQL Server, execução de API, Docker Hub, Actions ou Azure. Esta sessão dispõe de JDK mas não de Maven/Docker nem resolução de dependências externas. A validação definitiva deve ser feita no Windows e nas contas reais antes da entrega.

**Comandos de validação no Windows:**

```powershell
# Na raiz do projeto:
mvn -B clean verify
docker compose config
powershell -NoProfile -ExecutionPolicy Bypass -File .\scripts\INICIAR_WINDOWS.ps1
powershell -NoProfile -ExecutionPolicy Bypass -File .\scripts\TESTAR_API.ps1
```

Não substitua links reais por links presumidos em `ENTREGA_PROFESSOR_PREENCHER.txt`.
