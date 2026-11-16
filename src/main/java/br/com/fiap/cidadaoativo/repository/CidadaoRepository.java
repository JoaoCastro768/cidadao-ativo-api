package br.com.fiap.cidadaoativo.repository;

import br.com.fiap.cidadaoativo.entity.Cidadao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CidadaoRepository extends JpaRepository<Cidadao, Long> {
    boolean existsByEmailIgnoreCase(String email);
}
