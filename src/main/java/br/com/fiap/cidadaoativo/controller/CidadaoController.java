package br.com.fiap.cidadaoativo.controller;

import br.com.fiap.cidadaoativo.dto.*;
import br.com.fiap.cidadaoativo.service.CidadaoService;
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
@RequestMapping("/api/v1/cidadaos")
@Tag(name = "Cidadao", description = "CRUD dos recursos cidadaos")
public class CidadaoController {
    private final CidadaoService service;
    public CidadaoController(CidadaoService service) { this.service = service; }

    @PostMapping
    @Operation(summary = "Criar cidadao")
    public ResponseEntity<CidadaoResponse> create(@Valid @RequestBody CidadaoRequest request) {
        CidadaoResponse saved = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @GetMapping
    @Operation(summary = "Listar cidadaos")
    public List<CidadaoResponse> list() { return service.list(); }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar cidadao pelo ID")
    public CidadaoResponse find(@PathVariable Long id) { return service.find(id); }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cidadao")
    public CidadaoResponse update(@PathVariable Long id, @Valid @RequestBody CidadaoRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Excluir cidadao")
    public void delete(@PathVariable Long id) { service.delete(id); }
}
