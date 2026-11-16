package br.com.fiap.cidadaoativo.service;

import br.com.fiap.cidadaoativo.dto.*;
import br.com.fiap.cidadaoativo.domain.*;
import br.com.fiap.cidadaoativo.entity.*;
import br.com.fiap.cidadaoativo.exception.*;
import br.com.fiap.cidadaoativo.repository.BairroRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.List;

@Service
@Transactional
public class BairroService {
    private final BairroRepository repository;
    private final ModelMapper mapper;
    public BairroService(BairroRepository repository, ModelMapper mapper) { this.repository = repository; this.mapper = mapper; }

    public BairroResponse create(BairroRequest request) {

        Bairro entity = mapper.map(request, Bairro.class);
        entity.setId(null);
        entity.setAtivo(request.getAtivo() == null ? true : request.getAtivo());
        entity.setCriadoEm(Instant.now());
        return toResponse(repository.save(entity));
    }

    @Transactional(readOnly = true)
    public List<BairroResponse> list() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "id")).stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public BairroResponse find(Long id) { return toResponse(getOrThrow(id)); }

    public BairroResponse update(Long id, BairroRequest request) {
        Bairro entity = getOrThrow(id);

        mapper.map(request, entity);
        entity.setId(id);
        if (request.getAtivo() == null) entity.setAtivo(true);
        return toResponse(repository.save(entity));
    }

    public void delete(Long id) { repository.delete(getOrThrow(id)); }

    private Bairro getOrThrow(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Bairro não encontrado(a)."));
    }

    private BairroResponse toResponse(Bairro entity) {
        BairroResponse response = mapper.map(entity, BairroResponse.class);
        return response;
    }
}
