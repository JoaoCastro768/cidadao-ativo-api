-- Execute no Microsoft SQL Server conectado à base cidadao_ativo.
-- Confirma as quatro tabelas no plural e relacionamentos persistidos.
SELECT name FROM sys.tables WHERE name IN ('cidadaos','bairros','ocorrencias','atualizacoes');
SELECT TOP (10) id, nome, email, criado_em FROM dbo.cidadaos ORDER BY id DESC;
SELECT TOP (10) id, nome, cidade, uf FROM dbo.bairros ORDER BY id DESC;
SELECT TOP (10) id, titulo, categoria, status, cidadao_id, bairro_id FROM dbo.ocorrencias ORDER BY id DESC;
SELECT TOP (10) id, autor, descricao, ocorrencia_id FROM dbo.atualizacoes ORDER BY id DESC;
SELECT fk.name AS chave_estrangeira, OBJECT_NAME(fk.parent_object_id) AS tabela_origem,
       OBJECT_NAME(fk.referenced_object_id) AS tabela_destino
FROM sys.foreign_keys fk
WHERE OBJECT_NAME(fk.parent_object_id) IN ('ocorrencias', 'atualizacoes');
