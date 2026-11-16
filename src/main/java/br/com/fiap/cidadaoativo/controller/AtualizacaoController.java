package br.com.fiap.cidadaoativo.controller;

import br.com.fiap.cidadaoativo.dto.*;
import br.com.fiap.cidadaoativo.service.AtualizacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/atualizacoes")
@Tag(name = "Atualizacao", description = "CRUD dos recursos atualizacoes")
public class AtualizacaoController {
    private final AtualizacaoService service;
    public AtualizacaoController(AtualizacaoService service) { this.service = service; }

    @PostMapping
    @Operation(summary = "Criar atualizacao")
    public ResponseEntity<AtualizacaoResponse> create(@Valid @RequestBody AtualizacaoRequest request) {
        AtualizacaoResponse saved = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @GetMapping
    @Operation(summary = "Listar atualizacoes")
    public List<AtualizacaoResponse> list() { return service.list(); }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar atualizacao pelo ID")
    public AtualizacaoResponse find(@PathVariable Long id) { return service.find(id); }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar atualizacao")
    public AtualizacaoResponse update(@PathVariable Long id, @Valid @RequestBody AtualizacaoRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Excluir atualizacao")
    public void delete(@PathVariable Long id) { service.delete(id); }
}
