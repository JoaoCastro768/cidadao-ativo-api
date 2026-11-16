package br.com.fiap.cidadaoativo.controller;

import br.com.fiap.cidadaoativo.dto.*;
import br.com.fiap.cidadaoativo.service.OcorrenciaService;
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
@RequestMapping("/api/v1/ocorrencias")
@Tag(name = "Ocorrencia", description = "CRUD dos recursos ocorrencias")
public class OcorrenciaController {
    private final OcorrenciaService service;
    public OcorrenciaController(OcorrenciaService service) { this.service = service; }

    @PostMapping
    @Operation(summary = "Criar ocorrencia")
    public ResponseEntity<OcorrenciaResponse> create(@Valid @RequestBody OcorrenciaRequest request) {
        OcorrenciaResponse saved = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @GetMapping
    @Operation(summary = "Listar ocorrencias")
    public List<OcorrenciaResponse> list() { return service.list(); }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar ocorrencia pelo ID")
    public OcorrenciaResponse find(@PathVariable Long id) { return service.find(id); }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar ocorrencia")
    public OcorrenciaResponse update(@PathVariable Long id, @Valid @RequestBody OcorrenciaRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Excluir ocorrencia")
    public void delete(@PathVariable Long id) { service.delete(id); }
}
