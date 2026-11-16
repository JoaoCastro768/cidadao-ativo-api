package br.com.fiap.cidadaoativo.service;

import br.com.fiap.cidadaoativo.dto.*;
import br.com.fiap.cidadaoativo.domain.*;
import br.com.fiap.cidadaoativo.entity.*;
import br.com.fiap.cidadaoativo.exception.*;
import br.com.fiap.cidadaoativo.repository.OcorrenciaRepository;
import br.com.fiap.cidadaoativo.repository.CidadaoRepository;
import br.com.fiap.cidadaoativo.repository.BairroRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.List;

@Service
@Transactional
public class OcorrenciaService {
    private final OcorrenciaRepository repository;
    private final ModelMapper mapper;
    private final CidadaoRepository cidadaoRepository;
    private final BairroRepository bairroRepository;
    public OcorrenciaService(OcorrenciaRepository repository, ModelMapper mapper, CidadaoRepository cidadaoRepository, BairroRepository bairroRepository) { this.repository = repository; this.mapper = mapper; this.cidadaoRepository = cidadaoRepository; this.bairroRepository = bairroRepository; }

    public OcorrenciaResponse create(OcorrenciaRequest request) {

        Ocorrencia entity = new Ocorrencia();
        applyRequest(request, entity);
        entity.setCidadao(cidadaoRepository.findById(request.getCidadaoId()).orElseThrow(() -> new NotFoundException("Cidadão não encontrado.")));
        entity.setBairro(bairroRepository.findById(request.getBairroId()).orElseThrow(() -> new NotFoundException("Bairro não encontrado.")));
        entity.setStatus(request.getStatus() == null ? StatusOcorrencia.ABERTA : request.getStatus());
        entity.setCriadoEm(Instant.now());
        entity.setAtualizadoEm(Instant.now());
        return toResponse(repository.save(entity));
    }

    @Transactional(readOnly = true)
    public List<OcorrenciaResponse> list() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "id")).stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public OcorrenciaResponse find(Long id) { return toResponse(getOrThrow(id)); }

    public OcorrenciaResponse update(Long id, OcorrenciaRequest request) {
        Ocorrencia entity = getOrThrow(id);

        applyRequest(request, entity);
        entity.setCidadao(cidadaoRepository.findById(request.getCidadaoId()).orElseThrow(() -> new NotFoundException("Cidadão não encontrado.")));
        entity.setBairro(bairroRepository.findById(request.getBairroId()).orElseThrow(() -> new NotFoundException("Bairro não encontrado.")));
        if (request.getStatus() == null) entity.setStatus(StatusOcorrencia.ABERTA);
        entity.setAtualizadoEm(Instant.now());
        return toResponse(repository.save(entity));
    }

    public void delete(Long id) { repository.delete(getOrThrow(id)); }

    private Ocorrencia getOrThrow(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Ocorrencia não encontrado(a)."));
    }

    private void applyRequest(OcorrenciaRequest request, Ocorrencia entity) {
        entity.setTitulo(request.getTitulo());
        entity.setDescricao(request.getDescricao());
        entity.setCategoria(request.getCategoria());
        entity.setStatus(request.getStatus() == null ? StatusOcorrencia.ABERTA : request.getStatus());
    }

    private OcorrenciaResponse toResponse(Ocorrencia entity) {
        OcorrenciaResponse response = new OcorrenciaResponse();
        response.setId(entity.getId());
        response.setTitulo(entity.getTitulo());
        response.setDescricao(entity.getDescricao());
        response.setCategoria(entity.getCategoria());
        response.setStatus(entity.getStatus());
        response.setCriadoEm(entity.getCriadoEm());
        response.setAtualizadoEm(entity.getAtualizadoEm());
        response.setCidadaoId(entity.getCidadao().getId());
        response.setBairroId(entity.getBairro().getId());
        return response;
    }
}
