package br.com.fiap.cidadaoativo.service;

import br.com.fiap.cidadaoativo.dto.CidadaoRequest;
import br.com.fiap.cidadaoativo.entity.Cidadao;
import br.com.fiap.cidadaoativo.exception.ConflictException;
import br.com.fiap.cidadaoativo.repository.CidadaoRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class CidadaoServiceTest {
    private final CidadaoRepository repo = mock(CidadaoRepository.class);
    private final CidadaoService service = new CidadaoService(repo, new ModelMapper());

    @Test void criacaoRetornaResponseEGravaNoRepositorio() {
        CidadaoRequest req = new CidadaoRequest();
        req.setNome("Maria Exemplo"); req.setEmail("maria@example.org");
        when(repo.save(any(Cidadao.class))).thenAnswer(inv -> {
            Cidadao cidadao = inv.getArgument(0); cidadao.setId(7L); return cidadao;
        });
        var result = service.create(req);
        assertEquals(7L, result.getId());
        assertEquals("Maria Exemplo", result.getNome());
        assertTrue(result.getAtivo());
        verify(repo).save(any(Cidadao.class));
    }

    @Test void emailDuplicadoGeraConflito() {
        CidadaoRequest req = new CidadaoRequest();
        req.setNome("Maria Exemplo"); req.setEmail("maria@example.org");
        when(repo.existsByEmailIgnoreCase("maria@example.org")).thenReturn(true);
        assertThrows(ConflictException.class, () -> service.create(req));
        verify(repo, never()).save(any());
    }

    @Test void listaVaziaRetornaListaVazia() {
        when(repo.findAll(any(Sort.class))).thenReturn(List.of());
        assertEquals(0, service.list().size());
    }
}
