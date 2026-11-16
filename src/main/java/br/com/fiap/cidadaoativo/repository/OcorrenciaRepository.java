package br.com.fiap.cidadaoativo.repository;

import br.com.fiap.cidadaoativo.entity.Ocorrencia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Long> {

}
