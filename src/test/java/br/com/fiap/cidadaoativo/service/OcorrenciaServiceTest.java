package br.com.fiap.cidadaoativo.service;

import br.com.fiap.cidadaoativo.domain.Categoria;
import br.com.fiap.cidadaoativo.domain.StatusOcorrencia;
import br.com.fiap.cidadaoativo.dto.OcorrenciaRequest;
import br.com.fiap.cidadaoativo.entity.Bairro;
import br.com.fiap.cidadaoativo.entity.Cidadao;
import br.com.fiap.cidadaoativo.entity.Ocorrencia;
import br.com.fiap.cidadaoativo.exception.NotFoundException;
import br.com.fiap.cidadaoativo.repository.BairroRepository;
import br.com.fiap.cidadaoativo.repository.CidadaoRepository;
import br.com.fiap.cidadaoativo.repository.OcorrenciaRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OcorrenciaServiceTest {
    private final OcorrenciaRepository repo = mock(OcorrenciaRepository.class);
    private final CidadaoRepository cidadaos = mock(CidadaoRepository.class);
    private final BairroRepository bairros = mock(BairroRepository.class);
    private final OcorrenciaService service = new OcorrenciaService(repo, new ModelMapper(), cidadaos, bairros);

    @Test void criaOcorrenciaComRelacionamentosEStatusPadrao() {
        Cidadao cidadao = new Cidadao(); cidadao.setId(2L);
        Bairro bairro = new Bairro(); bairro.setId(3L);
        when(cidadaos.findById(2L)).thenReturn(Optional.of(cidadao));
        when(bairros.findById(3L)).thenReturn(Optional.of(bairro));
        when(repo.save(any(Ocorrencia.class))).thenAnswer(inv -> {
            Ocorrencia o = inv.getArgument(0); o.setId(9L); return o;
        });
        OcorrenciaRequest req = new OcorrenciaRequest();
        req.setTitulo("Poste sem iluminação"); req.setDescricao("Problema observado na rua");
        req.setCategoria(Categoria.ILUMINACAO); req.setCidadaoId(2L); req.setBairroId(3L);
        var result = service.create(req);
        assertEquals(9L, result.getId());
        assertEquals(2L, result.getCidadaoId());
        assertEquals(3L, result.getBairroId());
        assertEquals(StatusOcorrencia.ABERTA, result.getStatus());
    }

    @Test void idInexistenteRetorna404PorExcecaoDominio() {
        assertThrows(NotFoundException.class, () -> service.find(999L));
    }
}
