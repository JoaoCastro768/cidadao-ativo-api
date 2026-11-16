package br.com.fiap.cidadaoativo.dto;

import jakarta.validation.constraints.*;
import br.com.fiap.cidadaoativo.domain.*;

public class BairroRequest {
    @NotBlank @Size(max = 100)
    private String nome;
    @NotBlank @Size(max = 100)
    private String cidade;
    @NotBlank @Size(max = 2)
    private String uf;
    @Size(max = 8)
    private String cepReferencia;
    private Boolean ativo;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }
    public String getUf() { return uf; }
    public void setUf(String uf) { this.uf = uf; }
    public String getCepReferencia() { return cepReferencia; }
    public void setCepReferencia(String cepReferencia) { this.cepReferencia = cepReferencia; }
    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }
}
