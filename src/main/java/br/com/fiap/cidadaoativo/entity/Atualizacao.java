package br.com.fiap.cidadaoativo.entity;

import jakarta.persistence.*;
import br.com.fiap.cidadaoativo.domain.*;
import java.time.Instant;

@Entity
@Table(name = "atualizacoes")
public class Atualizacao {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 1500)
    private String descricao;
    @Column(nullable = false, length = 120)
    private String autor;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 40)
    private TipoAtualizacao tipo;
    @Column(length = 500)
    private String anexoUrl;
    @Column(nullable = false)
    private Boolean concluida;
    @Column(nullable = false, updatable = false)
    private Instant criadoEm;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ocorrencia_id", nullable = false)
    private Ocorrencia ocorrencia;

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
    public Ocorrencia getOcorrencia() { return ocorrencia; }
    public void setOcorrencia(Ocorrencia ocorrencia) { this.ocorrencia = ocorrencia; }
}
