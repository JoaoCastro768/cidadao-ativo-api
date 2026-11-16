# Checklist de publicação — GS 2026.2 (microservices)

- [ ] `mvn clean verify` passou em Windows ou GitHub Actions
- [ ] `docker compose up --build -d` iniciou SQL Server + API
- [ ] `/` redireciona para Swagger UI
- [ ] 4 entidades têm cada uma ≥5 atributos, ≥2 obrigatórios e relacionamentos válidos
- [ ] 20 operações CRUD respondem adequadamente
- [ ] Criar/consultar no SQL Server e reiniciar API sem perder dados
- [ ] GitHub público contém código, Dockerfile e `.github/workflows/dockerhub-cd.yml`
- [ ] Actions executou automaticamente após push na `main` com resultado verde
- [ ] Docker Hub contém a imagem e a tag `latest`
- [ ] Azure Container App acessível na internet com SQL Server funcional
- [ ] Banco não exposto indiscriminadamente e senhas fora do Git
- [ ] `ENTREGA_PROFESSOR_PREENCHER.txt` foi editado para ter os 3 links verificados
- [ ] Arquivo TXT foi enviado no Portal FIAP até 16/11/2026 23h55

O ZIP entregue aqui é **material de desenvolvimento**; o arquivo exigido no Portal é o TXT final. Não confundir!
