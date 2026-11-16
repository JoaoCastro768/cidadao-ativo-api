package br.com.fiap.cidadaoativo.controller;

import br.com.fiap.cidadaoativo.dto.*;
import br.com.fiap.cidadaoativo.service.BairroService;
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
@RequestMapping("/api/v1/bairros")
@Tag(name = "Bairro", description = "CRUD dos recursos bairros")
public class BairroController {
    private final BairroService service;
    public BairroController(BairroService service) { this.service = service; }

    @PostMapping
    @Operation(summary = "Criar bairro")
    public ResponseEntity<BairroResponse> create(@Valid @RequestBody BairroRequest request) {
        BairroResponse saved = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @GetMapping
    @Operation(summary = "Listar bairros")
    public List<BairroResponse> list() { return service.list(); }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar bairro pelo ID")
    public BairroResponse find(@PathVariable Long id) { return service.find(id); }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar bairro")
    public BairroResponse update(@PathVariable Long id, @Valid @RequestBody BairroRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Excluir bairro")
    public void delete(@PathVariable Long id) { service.delete(id); }
}
