package br.com.fiap.cidadaoativo.dto;

import jakarta.validation.constraints.*;
import br.com.fiap.cidadaoativo.domain.*;

public class AtualizacaoRequest {
    @NotBlank @Size(max = 1500)
    private String descricao;
    @NotBlank @Size(max = 120)
    private String autor;
    private TipoAtualizacao tipo;
    @Size(max = 500)
    private String anexoUrl;
    private Boolean concluida;
    @NotNull
    private Long ocorrenciaId;

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
    public Long getOcorrenciaId() { return ocorrenciaId; }
    public void setOcorrenciaId(Long ocorrenciaId) { this.ocorrenciaId = ocorrenciaId; }
}
