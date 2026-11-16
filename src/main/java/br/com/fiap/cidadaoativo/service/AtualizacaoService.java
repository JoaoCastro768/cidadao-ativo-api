package br.com.fiap.cidadaoativo.service;

import br.com.fiap.cidadaoativo.dto.*;
import br.com.fiap.cidadaoativo.domain.*;
import br.com.fiap.cidadaoativo.entity.*;
import br.com.fiap.cidadaoativo.exception.*;
import br.com.fiap.cidadaoativo.repository.AtualizacaoRepository;
import br.com.fiap.cidadaoativo.repository.OcorrenciaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.List;

@Service
@Transactional
public class AtualizacaoService {
    private final AtualizacaoRepository repository;
    private final ModelMapper mapper;
    private final OcorrenciaRepository ocorrenciaRepository;
    public AtualizacaoService(AtualizacaoRepository repository, ModelMapper mapper, OcorrenciaRepository ocorrenciaRepository) { this.repository = repository; this.mapper = mapper; this.ocorrenciaRepository = ocorrenciaRepository; }

    public AtualizacaoResponse create(AtualizacaoRequest request) {

        Atualizacao entity = new Atualizacao();
        applyRequest(request, entity);
        entity.setOcorrencia(ocorrenciaRepository.findById(request.getOcorrenciaId()).orElseThrow(() -> new NotFoundException("Ocorrência não encontrada.")));
        entity.setConcluida(request.getConcluida() == null ? false : request.getConcluida());
        entity.setTipo(request.getTipo() == null ? TipoAtualizacao.OBSERVACAO : request.getTipo());
        entity.setCriadoEm(Instant.now());
        return toResponse(repository.save(entity));
    }

    @Transactional(readOnly = true)
    public List<AtualizacaoResponse> list() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "id")).stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public AtualizacaoResponse find(Long id) { return toResponse(getOrThrow(id)); }

    public AtualizacaoResponse update(Long id, AtualizacaoRequest request) {
        Atualizacao entity = getOrThrow(id);

        applyRequest(request, entity);
        entity.setOcorrencia(ocorrenciaRepository.findById(request.getOcorrenciaId()).orElseThrow(() -> new NotFoundException("Ocorrência não encontrada.")));
        if (request.getConcluida() == null) entity.setConcluida(false);
        if (request.getTipo() == null) entity.setTipo(TipoAtualizacao.OBSERVACAO);
        return toResponse(repository.save(entity));
    }

    public void delete(Long id) { repository.delete(getOrThrow(id)); }

    private Atualizacao getOrThrow(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Atualizacao não encontrado(a)."));
    }

    private void applyRequest(AtualizacaoRequest request, Atualizacao entity) {
        entity.setDescricao(request.getDescricao());
        entity.setAutor(request.getAutor());
        entity.setTipo(request.getTipo() == null ? TipoAtualizacao.OBSERVACAO : request.getTipo());
        entity.setAnexoUrl(request.getAnexoUrl());
        entity.setConcluida(request.getConcluida() == null ? false : request.getConcluida());
    }

    private AtualizacaoResponse toResponse(Atualizacao entity) {
        AtualizacaoResponse response = new AtualizacaoResponse();
        response.setId(entity.getId());
        response.setDescricao(entity.getDescricao());
        response.setAutor(entity.getAutor());
        response.setTipo(entity.getTipo());
        response.setAnexoUrl(entity.getAnexoUrl());
        response.setConcluida(entity.getConcluida());
        response.setCriadoEm(entity.getCriadoEm());
        response.setOcorrenciaId(entity.getOcorrencia().getId());
        return response;
    }
}
