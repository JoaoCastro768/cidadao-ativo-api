package br.com.fiap.cidadaoativo.dto;

import jakarta.validation.constraints.*;
import br.com.fiap.cidadaoativo.domain.*;

public class CidadaoRequest {
    @NotBlank @Size(max = 120)
    private String nome;
    @NotBlank @Email @Size(max = 180)
    private String email;
    @Size(max = 30)
    private String telefone;
    private Boolean ativo;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }
}
