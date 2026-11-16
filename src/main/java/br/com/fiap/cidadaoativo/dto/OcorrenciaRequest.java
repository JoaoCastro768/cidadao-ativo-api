package br.com.fiap.cidadaoativo.dto;

import jakarta.validation.constraints.*;
import br.com.fiap.cidadaoativo.domain.*;

public class OcorrenciaRequest {
    @NotBlank @Size(max = 160)
    private String titulo;
    @NotBlank @Size(max = 2500)
    private String descricao;
    @NotNull
    private Categoria categoria;
    private StatusOcorrencia status;
    @NotNull
    private Long cidadaoId;
    @NotNull
    private Long bairroId;

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
    public StatusOcorrencia getStatus() { return status; }
    public void setStatus(StatusOcorrencia status) { this.status = status; }
    public Long getCidadaoId() { return cidadaoId; }
    public void setCidadaoId(Long cidadaoId) { this.cidadaoId = cidadaoId; }
    public Long getBairroId() { return bairroId; }
    public void setBairroId(Long bairroId) { this.bairroId = bairroId; }
}
