package br.com.fiap.cidadaoativo.dto;

import java.time.Instant;
import br.com.fiap.cidadaoativo.domain.*;

public class AtualizacaoResponse {
    private Long id;
    private String descricao;
    private String autor;
    private TipoAtualizacao tipo;
    private String anexoUrl;
    private Boolean concluida;
    private Instant criadoEm;
    private Long ocorrenciaId;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }
    public TipoAtualizacao getTipo() { return tipo; }
    public void setTipo(TipoAtualizacao tipo) { this.tipo = tipo; }
    public String getAnexoUrl() { return anexoUrl; }
    public void setAnexoUrl(String anexoUrl) { this.anexoUrl = anexoUrl; }
    public Boolean getConcluida() { return concluida; }
    public void setConcluida(Boolean concluida) { this.concluida = concluida; }
    public Instant getCriadoEm() { return criadoEm; }
    public void setCriadoEm(Instant criadoEm) { this.criadoEm = criadoEm; }
    public Long getOcorrenciaId() { return ocorrenciaId; }
    public void setOcorrenciaId(Long ocorrenciaId) { this.ocorrenciaId = ocorrenciaId; }
}
