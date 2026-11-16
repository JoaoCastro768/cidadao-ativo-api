package br.com.fiap.cidadaoativo.service;

import br.com.fiap.cidadaoativo.dto.*;
import br.com.fiap.cidadaoativo.domain.*;
import br.com.fiap.cidadaoativo.entity.*;
import br.com.fiap.cidadaoativo.exception.*;
import br.com.fiap.cidadaoativo.repository.CidadaoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.List;

@Service
@Transactional
public class CidadaoService {
    private final CidadaoRepository repository;
    private final ModelMapper mapper;
    public CidadaoService(CidadaoRepository repository, ModelMapper mapper) { this.repository = repository; this.mapper = mapper; }

    public CidadaoResponse create(CidadaoRequest request) {
        if (repository.existsByEmailIgnoreCase(request.getEmail())) throw new ConflictException("Email já cadastrado.");
        Cidadao entity = mapper.map(request, Cidadao.class);
        entity.setId(null);
        entity.setAtivo(request.getAtivo() == null ? true : request.getAtivo());
        entity.setCriadoEm(Instant.now());
        return toResponse(repository.save(entity));
    }

    @Transactional(readOnly = true)
    public List<CidadaoResponse> list() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "id")).stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public CidadaoResponse find(Long id) { return toResponse(getOrThrow(id)); }

    public CidadaoResponse update(Long id, CidadaoRequest request) {
        Cidadao entity = getOrThrow(id);

        mapper.map(request, entity);
        entity.setId(id);
        if (request.getAtivo() == null) entity.setAtivo(true);
        return toResponse(repository.save(entity));
    }

    public void delete(Long id) { repository.delete(getOrThrow(id)); }

    private Cidadao getOrThrow(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Cidadao não encontrado(a)."));
    }

    private CidadaoResponse toResponse(Cidadao entity) {
        CidadaoResponse response = mapper.map(entity, CidadaoResponse.class);
        return response;
    }
}
