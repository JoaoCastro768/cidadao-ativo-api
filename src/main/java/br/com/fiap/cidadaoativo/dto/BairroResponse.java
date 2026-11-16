package br.com.fiap.cidadaoativo.dto;

import java.time.Instant;
import br.com.fiap.cidadaoativo.domain.*;

public class BairroResponse {
    private Long id;
    private String nome;
    private String cidade;
    private String uf;
    private String cepReferencia;
    private Boolean ativo;
    private Instant criadoEm;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
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
    public Instant getCriadoEm() { return criadoEm; }
    public void setCriadoEm(Instant criadoEm) { this.criadoEm = criadoEm; }
}
