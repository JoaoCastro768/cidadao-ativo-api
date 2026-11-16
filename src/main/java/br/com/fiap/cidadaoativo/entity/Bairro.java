package br.com.fiap.cidadaoativo.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "bairros")
public class Bairro {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String nome;
    @Column(nullable = false, length = 100)
    private String cidade;
    @Column(nullable = false, length = 2)
    private String uf;
    @Column(length = 8)
    private String cepReferencia;
    @Column(nullable = false)
    private Boolean ativo;
    @Column(nullable = false, updatable = false)
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
