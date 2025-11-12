package com.cleberleao.estacionamento.repository;

import com.cleberleao.estacionamento.entity.Estacionar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstacionarRepository extends JpaRepository<Estacionar, Integer> {


    Optional<Estacionar> findByVeiculo_PlacaAndSaidaIsNull(String placa);


    Optional<Estacionar> findByVagaAndSaidaIsNull(Integer vaga);

    // vagas ocupadas
    List<Estacionar> findBySaidaIsNull();

    long countBySaidaIsNull();
}
